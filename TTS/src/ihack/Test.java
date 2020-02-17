package ihack;

import java.util.Arrays;
import marytts.signalproc.effects.JetPilotEffect;
import marytts.signalproc.effects.LpcWhisperiserEffect;
import marytts.signalproc.effects.RobotiserEffect;
import marytts.signalproc.effects.StadiumEffect;
import marytts.signalproc.effects.VocalTractLinearScalerEffect;
import marytts.signalproc.effects.VolumeEffect;

public class Test 
{
	public static void main(String[] args) 
	{
		TextToSpeech tts = new TextToSpeech();
		
		//Print all the available audio effects
		tts.getAudioEffects().stream().forEach(audioEffect -> {
			System.out.println("-----Name-----");
			System.out.println(audioEffect.getName());
			System.out.println("-----Examples-----");
			System.out.println(audioEffect.getExampleParameters());
			System.out.println("-----Help Text------");
			System.out.println(audioEffect.getHelpText() + "\n\n");
			
		});
		
		//Print all the available voices
		tts.getAvailableVoices().stream().forEach(voice -> System.out.println("Voice: " + voice));
		
		// Setting the Current Voice
		tts.setVoice("dfki-spike-hsmm");
		
		
		//VocalTractLinearScalerEffect
		VocalTractLinearScalerEffect vocalTractLSE = new VocalTractLinearScalerEffect(); //russian drunk effect
		vocalTractLSE.setParams("amount:70");
		
		//JetPilotEffect
		JetPilotEffect jetPilotEffect = new JetPilotEffect(); //epic fun!!!
		jetPilotEffect.setParams("amount:100");
		
		//RobotiserEffect
		RobotiserEffect robotiserEffect = new RobotiserEffect();
		robotiserEffect.setParams("amount:50");
		
		//StadiumEffect
		StadiumEffect stadiumEffect = new StadiumEffect();
		stadiumEffect.setParams("amount:150");
		
		//LpcWhisperiserEffect
		LpcWhisperiserEffect lpcWhisperiserEffect = new LpcWhisperiserEffect(); //creepy
		lpcWhisperiserEffect.setParams("amount:70");
		
		//VolumeEffect
		VolumeEffect volumeEffect = new VolumeEffect(); //be careful with this i almost got heart attack
		volumeEffect.setParams("amount:0");
		
		//Apply the effects
		//tts.getMarytts().setAudioEffects(stadiumEffect.getFullEffectAsString());// + "+" + stadiumEffect.getFullEffectAsString());
		
		java.util.List<String> arrayList = Arrays.asList("‘perhaps we’ll just have to let him live on his own fat for a while until he decides to eat’");
		
		//Loop infinitely
		for (int i = 0; i < 150.000; i++)
			arrayList.forEach(word -> tts.speak(word, 2.0f, false, true));
	}
	
	public void tutorial_1_2_3_FromYoutube() 
	{
		TextToSpeech tts = new TextToSpeech();
		
		//Print all the available voices
		tts.getAvailableVoices().stream().forEach(voice -> System.out.println("Voice: " + voice));
		
		// Setting the Current Voice
		tts.setVoice("cmu-rms-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("Today we will learn how to add different languages and voices on Mary T T S!", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("dfki-poppy-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("Who is Mary Bob?", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("cmu-rms-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("No one my darling...", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("dfki-poppy-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("I don't trust you", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("cmu-rms-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("Oh yeah baby!", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("dfki-poppy-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("aaaaaa", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("cmu-rms-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("yeah!", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("dfki-poppy-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("oh oh yeah!", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("dfki-poppy-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("I am Elize , a wonderful girl!", 2.0f, false, true);
		
		// Setting the Voice
		tts.setVoice("cmu-rms-hsmm");
		
		// TTS say something that we actually are typing into the first variable
		tts.speak("Shut up Elize . Let's continue our tutorials!", 2.0f, false, true);
	}
}
