package jp.co.wiseman.speech2text;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea output_field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setTitle("議事録文字起こしプログラム by 齊藤");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton open_button = new JButton("ファイルを選択");
		open_button.setFont(new Font("メイリオ", Font.PLAIN, 10));
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.NORTH, open_button, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, open_button, -374, SpringLayout.EAST, contentPane);
		contentPane.setLayout(sl_contentPane);
		contentPane.add(open_button);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("メイリオ", Font.PLAIN, 10));
		sl_contentPane.putConstraint(SpringLayout.WEST, textPane, 1, SpringLayout.EAST, open_button);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textPane, 2, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textPane, -15, SpringLayout.EAST, contentPane);
		textPane.setText("ボタンをクリックして音声ファイルを選択してください。");
		contentPane.add(textPane);
		
		textField = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, 30, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField, -467, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, -15, SpringLayout.EAST, contentPane);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton run_button = new JButton("音声認識実行");
		run_button.setFont(new Font("メイリオ", Font.PLAIN, 10));
		sl_contentPane.putConstraint(SpringLayout.WEST, open_button, 47, SpringLayout.EAST, run_button);
		sl_contentPane.putConstraint(SpringLayout.NORTH, run_button, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, run_button, -14, SpringLayout.NORTH, textField);
		sl_contentPane.putConstraint(SpringLayout.EAST, run_button, 1137, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, run_button, 1010, SpringLayout.EAST, contentPane);
		contentPane.add(run_button);
		contentPane.add(run_button);
		
		output_field = new JTextArea();
		output_field.setFont(new Font("メイリオ", Font.PLAIN, 12));
		sl_contentPane.putConstraint(SpringLayout.NORTH, output_field, 60, SpringLayout.SOUTH, textField);
		sl_contentPane.putConstraint(SpringLayout.WEST, output_field, 0, SpringLayout.WEST, textField);
		contentPane.add(output_field);
		output_field.setLineWrap(true);
		output_field.setColumns(10);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setFont(new Font("メイリオ", Font.PLAIN, 10));
		textPane_1.setBackground(new Color(240, 248, 255));
		sl_contentPane.putConstraint(SpringLayout.NORTH, textPane_1, 33, SpringLayout.SOUTH, textField);
		sl_contentPane.putConstraint(SpringLayout.WEST, textPane_1, 0, SpringLayout.WEST, textField);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textPane_1, -6, SpringLayout.NORTH, output_field);
		sl_contentPane.putConstraint(SpringLayout.EAST, textPane_1, 64, SpringLayout.WEST, contentPane);
		textPane_1.setText("認識結果");
		contentPane.add(textPane_1);
		
		JTextPane txtpnPowerdByIbm = new JTextPane();
		sl_contentPane.putConstraint(SpringLayout.EAST, output_field, 0, SpringLayout.EAST, txtpnPowerdByIbm);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtpnPowerdByIbm, 495, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtpnPowerdByIbm, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, output_field, -6, SpringLayout.NORTH, txtpnPowerdByIbm);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtpnPowerdByIbm, 0, SpringLayout.EAST, contentPane);
		txtpnPowerdByIbm.setFont(new Font("MS UI Gothic", Font.PLAIN, 10));
		sl_contentPane.putConstraint(SpringLayout.WEST, txtpnPowerdByIbm, -266, SpringLayout.EAST, contentPane);
		txtpnPowerdByIbm.setText("Powerd by IBM Watson");
		contentPane.add(txtpnPowerdByIbm);
		
		//ファイルを選択させるボタン
		open_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filepath = dialogevents();
				if(filepath != "error") {
					textField.setText(filepath);
  				}
  				else {
  					JFrame msgframe = new JFrame();
  					JOptionPane.showMessageDialog(msgframe, "ファイルが正しく選択されませんでした。");
  				}
				
			}
		});
		
		//選択された音声ファイルをSpeech2Textクラスの関数に渡す関数
		run_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String StreamStr = "";
				if(textField.getText() != "") {
					String filepath = textField.getText();
					String dirpath = new File(filepath).getParent();
					output_field.setText("音声認識中です。しばらくお待ちください。");
					try {
						StreamStr = Speech2Text.main(filepath);
						text_output_func(StreamStr,dirpath);
					} catch (FileNotFoundException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
					output_field.setText(StreamStr);
				}
				else {
  					JFrame msgframe = new JFrame();
  					JOptionPane.showMessageDialog(msgframe, "ファイルが正しく選択されておりません。");
				}
			}
		});
	}
	
	public void text_output_func(String str_payload,String dirstr) {
		dirstr = dirstr.replace("\\", "\\\\");
		dirstr = dirstr + "\\\\";
		LocalDateTime d = LocalDateTime.now();
		DateTimeFormatter df_obj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String filename = df_obj.format(d) + ".txt";
		try{
			//出力先を作成する
            FileWriter fw = new FileWriter(dirstr + filename, false);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            pw.println(str_payload);
            pw.close();
			
		}catch(IOException e){
			System.out.println(e);
		}
	}

	//ファイル選択画面を表示させる関数
	public String dialogevents(){
		JFileChooser filechooser_obj = new JFileChooser();
		String FilePath = "";

		int selected = filechooser_obj.showOpenDialog(this);
		if (selected == JFileChooser.APPROVE_OPTION){
			File file = filechooser_obj.getSelectedFile();
			FilePath = file.getPath();
		}else if (selected == JFileChooser.CANCEL_OPTION){
			System.out.println("キャンセルされました。");
			FilePath = "error";
		}else if (selected == JFileChooser.ERROR_OPTION){
			System.out.println("エラーまたは取り消しがありました。");
			FilePath = "error";
		}
		return FilePath;
	}
}
