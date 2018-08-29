package jp.co.wiseman.speech2text;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONObject;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;


public class Speech2Text {
	
	public static String parce_of_json(JSONObject input_json) {
		String return_str = "";
		
		for(int i = 0;i < input_json.getJSONArray("results").length();i++) {
			String First_str = input_json.getJSONArray("results").getJSONObject(i).getJSONArray("alternatives").toString();
			First_str = First_str.replace("[", "");
			First_str = First_str.replace("]", "");
			JSONObject jsonObject_second = new JSONObject(First_str);
			String Second_str = jsonObject_second.getString("transcript");
			return_str = return_str + Second_str + " ";
		}
		
		return return_str;
	}
	
	public static String main(String filepath) throws FileNotFoundException {
		
		IamOptions settings = new IamOptions.Builder()
			    .apiKey({apikey})
			    .build();
		
		SpeechToText service = new SpeechToText(settings);
		service.setEndPoint("https://gateway-syd.watsonplatform.net/speech-to-text/api");
		//service.setUsernameAndPassword("<username>", "<password>");
		
		//File audio = new File("src/test/resources/test.mp3");
		File audio = new File(filepath);
		RecognizeOptions options = new RecognizeOptions.Builder()
				.audio(audio)
				.contentType(RecognizeOptions.ContentType.AUDIO_MP3)
				.model("ja-JP_BroadbandModel")
				.build();
		SpeechRecognitionResults transcript = service.recognize(options).execute();
		//System.out.println(transcript);
		String caststr = transcript.toString();
		
		JSONObject jsonObject = new JSONObject(caststr);
		System.out.println(parce_of_json(jsonObject));
		
		return parce_of_json(jsonObject);
	}
}
