package com.example.radiobook;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class GenderTest {
    public static void main(String[] args){
        Properties props = new Properties();
        props.setProperty("gender.firstnames", "/home/cyborg/Documents/WorkSpace/RadioBook/NLP/data/gender_filtered.txt");
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,gender");
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,gender,depparse,coref,kbp,quote");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation("Kamal goes to school and he is smart");

        pipeline.annotate(document);

        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                System.out.print(token.value());
                System.out.print(", Gender: ");
                System.out.println(token.get(CoreAnnotations.GenderAnnotation.class));
            }
        }
    }
}
