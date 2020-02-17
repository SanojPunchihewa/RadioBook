package com.example.radiobook;


import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {

        String[] englishArgs = new String[]{"-file", "sample-english.txt", "-outputFormat", "text", "-props", "english.properties"};
        StanfordCoreNLP.main(englishArgs);

    }
}
