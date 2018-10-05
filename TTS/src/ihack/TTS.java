package ihack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import groovy.json.internal.ArrayUtils;
import marytts.util.data.audio.MaryAudioUtils;

public class TTS 
{
	
	private static ArrayList<String> maleVoiceList;
	private static ArrayList<String> femaleVoiceList;
	private static HashMap<String,String> characterList = new HashMap<>();
	
	static final long RECORD_TIME = 10000;  // 1 minute
	 
	private static File wavFile = new File("Audio.wav");
    private static AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private static TargetDataLine line;
    private static double[] samples;
    private static double[] record = new double[5000];
    private static AudioInputStream audio = null;
 
    private static AudioFormat getAudioFormat() 
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,channels, signed, bigEndian);
        return format;
    }
 
    private static void start() 
    {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if(!AudioSystem.isLineSupported(info)) 
            {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            System.out.println("Start capturing...");
 
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Start recording...");
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
 
    private static void finish() 
    {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
	
	public static void main(String[] args) 
	{
		maleVoiceList = new ArrayList<>();
		femaleVoiceList = new ArrayList<>();
		
		maleVoiceList.add("cmu-rms-hsmm");
		maleVoiceList.add("dfki-obadiah-hsmm");
		maleVoiceList.add("cmu-bdl-hsmm");
		maleVoiceList.add("dfki-spike-hsmm");
		femaleVoiceList.add("cmu-slt-hsmm");
		femaleVoiceList.add("dfki-poppy-hsmm");
		
		textReader("output.json");
	}
	
	public static void textReader(String text)
	{
		TextToSpeech tts = new TextToSpeech();
		File file = new File(text);
		
		try 
		{
			JSONParser parser = new JSONParser();
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));
			
			for (Object object : jsonArray)
			{
			    JSONObject person = (JSONObject) object;

			    String character = (String) person.get("Character");			 
			    String gender = (String) person.get("Gender");
			    String quote = (String) person.get("Quote");
			    System.out.println(character+": "+gender);			 
			    System.out.println(quote);
			    System.out.println();
			    
			    if(gender.equals("Male"))
			    {
			    	if(!characterList.containsKey(character))
			    	{
			    		if(!maleVoiceList.isEmpty())
				    	{
				    		characterList.put(character, maleVoiceList.remove(maleVoiceList.size()-1));
				    	}
			    		else
			    		{
			    			characterList.put(character,"cms-rms-hsmm");
			    		}
			    	}			    				    	
			    }
			    else
			    {
			    	if(!characterList.containsKey(character))
			    	{
			    		if(!maleVoiceList.isEmpty())
				    	{
				    		characterList.put(character, femaleVoiceList.remove(femaleVoiceList.size()-1));
				    	}
			    		else
			    		{
			    			characterList.put(character,"dfki-poppy-hsmm");
			    		}
			    	}		
			    }			 		 
			    
//			    Thread stopper = new Thread(new Runnable() {			           
//					public void run() 
//		            {
//		                try 
//		                {
//		                    Thread.sleep(RECORD_TIME);
//		                } catch (InterruptedException ex) {
//		                    ex.printStackTrace();
//		                }			     
//						TTS.finish();
//		            }
//		        });
//				
//				Thread starter = new Thread(new Runnable() {
//		            public void run() 
//		            {
//		                TTS.start();	 			
//		            }
//		        });				
//			
//			    stopper.start();
//			    starter.start();		
			    
			    tts.setVoice(characterList.get(character));
				java.util.List<String> arrayList = Arrays.asList(quote);
				arrayList.forEach(word -> tts.speak(word, 2.0f, false, true));	
				
				audio = tts.record(quote, 2.0f, false, true);
		        // write to output
		        samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);		      
		        record = combine(record, samples);
				
			  }			
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File Not Found: "+ file.toString());
		}
		catch (IOException e)
		{
			System.out.println("Unable to read the file: "+ file.toString());
		}
		catch(ParseException e)
		{
			System.out.println("Unable to parse the object: "+ file.toString());
		}
		
		 try {
	            MaryAudioUtils.writeWavFile(record, "output.wav", audio.getFormat());
	            System.out.println("Output written to " + "output.wav");
	        } catch (IOException e) {
	            System.err.println("Could not write to file: " + "output" + "\n" + e.getMessage());
	            System.exit(1);
	        }
	}
	public static double[] combine(double[] a, double[] b)
	{
        int length = a.length + b.length;
        double[] result = new double[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
