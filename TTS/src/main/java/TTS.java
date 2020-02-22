import marytts.util.data.audio.MaryAudioUtils;
import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TTS {
    private static ArrayList<String> maleVoiceList;
    private static ArrayList<String> femaleVoiceList;
    private static HashMap<String, String> speakerVoiceMap = new HashMap<>();

    static final long RECORD_TIME = 10000;  // 1 minute
    private static final String NARRATOR = "dfki-pavoque-neutral-hsmm";
    private static final String GENERAL_MALE_VOICE = "cms-rms-hsmm";
    private static final String GENERAL_FEMALE_VOICE = "dfki-poppy-hsmm";
    private static File wavFile = new File("Audio.wav");
    private static AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private static TargetDataLine line;
    private static double[] samples;
    private static double[] record = new double[5000];
    private static AudioInputStream audio = null;

    public static void main(String[] args) {
    	// Male voice list
        maleVoiceList = new ArrayList<>();
		maleVoiceList.add("dfki-pavoque-neutral-hsmm");
		maleVoiceList.add("enst-dennys-hsmm");
		maleVoiceList.add("upmc-pierre-hsmm");
		maleVoiceList.add("dfki-spike-hsmm");

		// Female voice list
		femaleVoiceList = new ArrayList<>();
		femaleVoiceList.add("cmu-slt-hsmm");
		femaleVoiceList.add("istc-lucia-hsmm");
		femaleVoiceList.add("dfki-prudence-hsmm");

        textReader("output.json");
    }

    public static void textReader(String text) {
        MaryTTS tts = new MaryTTS();
        File file = new File(text);

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));

            for (Object object : jsonArray) {
                JSONObject person = (JSONObject) object;

                String speaker = (String) person.get("Speaker");
                String gender = (String) person.get("Gender");
                String quote = (String) person.get("Quote");

                System.out.println(speaker + ": " + gender);
                System.out.println(quote);
                System.out.println();

                assignSpeakerVoice();

                tts.setVoice(speakerVoiceMap.get(speaker));
                java.util.List<String> arrayList = Arrays.asList(quote);
                arrayList.forEach(word -> tts.speak(word, 2.0f, false, true));

                audio = tts.record(quote, 2.0f, false, true);
                // write to output
                samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);
                record = combine(record, samples);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read the file: " + file.toString());
        } catch (ParseException e) {
            System.out.println("Unable to parse the object: " + file.toString());
        }

        try {
            MaryAudioUtils.writeWavFile(record, "output.wav", audio.getFormat());
            System.out.println("Output written to " + "output.wav");
        } catch (IOException e) {
            System.err.println("Could not write to file: " + "output" + "\n" + e.getMessage());
            System.exit(1);
        }
    }

    private static void assignSpeakerVoice() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("maleSpeakersList.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String speaker = StringUtils.strip(line);
                if (!speakerVoiceMap.containsKey(speaker)) {
                    if (!maleVoiceList.isEmpty()) {
                        speakerVoiceMap.put(speaker, maleVoiceList.remove(maleVoiceList.size() - 1));
                    } else {
                        speakerVoiceMap.put(speaker, GENERAL_MALE_VOICE);
                    }
                }
            }

            reader = new BufferedReader(new FileReader("femaleSpeakersList.csv"));
            while ((line = reader.readLine()) != null) {
                String speaker = StringUtils.strip(line);
                if (!speakerVoiceMap.containsKey(speaker)) {
                    if (!femaleVoiceList.isEmpty()) {
                        speakerVoiceMap.put(speaker, femaleVoiceList.remove(femaleVoiceList.size() - 1));
                    } else {
                        speakerVoiceMap.put(speaker, GENERAL_FEMALE_VOICE);
                    }
                }
            }
            speakerVoiceMap.put("Narrator", NARRATOR);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[] combine(double[] a, double[] b) {
        int length = a.length + b.length;
        double[] result = new double[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static AudioFormat getAudioFormat() {
        float sampleRate = 32000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return format;
    }

    private static void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
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

    private static void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
}
