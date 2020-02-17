import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassifierInputPreprocessor {

    public static void main(String[] args) {

        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader("../nlp_output.json"));
            Quotes[] quotes = gson.fromJson(jsonReader, Quotes[].class);

            Set<String> speakerSet = new HashSet<String>();
            for (Quotes quote : quotes) {
                speakerSet.add(quote.getSpeaker());
            }
            StringBuilder speakersList = new StringBuilder();
            for (String speaker : speakerSet) {
                speakersList.append(speaker).append("\n");
                System.out.println(speaker);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("../speaker_list.txt"));
            writer.write(speakersList.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
