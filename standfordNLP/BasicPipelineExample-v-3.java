package com.ebook.drama.standfordNLP;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;


public class BasicPipelineExample {


  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new FileReader("./story.txt"));
    String line = br.readLine();
    while (line != null){
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
    }
    String text = sb.toString();
    // set up pipeline properties
    Properties props = new Properties();
    // set the list of annotators to run
    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
    props.setProperty("coref.algorithm", "neural");
    props.setProperty("singleQuotes", "true");
    props.setProperty("allowEmbeddedSame", "false");
    //props.setProperty("quote.asciiQuotes", "true");
    //props.setProperty("quote.extractUnclosedQuotes", "true");
    //props.setProperty("unicodeQuotes", "true");
    // build pipeline
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    // create a document object
    CoreDocument document = new CoreDocument(text);
    // annnotate the document
    pipeline.annotate(document);
    // examples

    // // 10th token of the document
    // CoreLabel token = document.tokens().get(0);
    // System.out.println("Example: token");
    // System.out.println(token);
    // System.out.println();


    //number of senteces
    System.out.println("Number of Senteces");
    System.out.println(document.sentences().size());
    System.out.println();




    // text of the first sentence
    String sentenceText = document.sentences().get(9).text();
    System.out.println("Example: sentence");
    System.out.println(sentenceText);
    System.out.println();

    // second sentence
    CoreSentence sentence = document.sentences().get(10);

    // // list of the part-of-speech tags for the second sentence
    // List<String> posTags = sentence.posTags();
    // System.out.println("Example: pos tags");
    // System.out.println(posTags);
    // System.out.println();

    // // list of the ner tags for the second sentence
    // List<String> nerTags = sentence.nerTags();
    // System.out.println("Example: ner tags");
    // System.out.println(nerTags);
    // System.out.println();

    // constituency parse for the second sentence
    Tree constituencyParse = sentence.constituencyParse();
    System.out.println("Example: constituency parse");
    System.out.println(constituencyParse);
    System.out.println();

    // dependency parse for the second sentence
    SemanticGraph dependencyParse = sentence.dependencyParse();
    System.out.println("Example: dependency parse");
    System.out.println(dependencyParse);
    System.out.println();

    // // kbp relations found in fifth sentence
    // List<RelationTriple> relations =
    //     document.sentences().get(4).relations();
    // System.out.println("Example: relation");
    // System.out.println(relations.get(0));
    // System.out.println();

    // entity mentions in the second sentence
    List<CoreEntityMention> entityMentions = sentence.entityMentions();
    System.out.println("Example: entity mentions");
    System.out.println(entityMentions);
    System.out.println();

    // coreference between entity mentions
    CoreEntityMention originalEntityMention = document.sentences().get(0).entityMentions().get(0);
    System.out.println("Example: original entity mention");
    System.out.println(originalEntityMention);
    System.out.println("Example: canonical entity mention");
    System.out.println(originalEntityMention.canonicalEntityMention().get());
    System.out.println();

    // // get document wide coref info
    // Map<Integer, CorefChain> corefChains = document.corefChains();
    // System.out.println("Example: coref chains for document");
    // System.out.println(corefChains);
    // System.out.println();

    // get quotes in document
    int i =1;
    List<CoreQuote> quotes = document.quotes();
    for(CoreQuote quote:quotes){
    	System.out.println("quote: "+(i++));
    	System.out.println(quote);
    	if(quote.hasSpeaker){
    		System.out.println("original speaker of quote");
		    System.out.println(quote.speaker().get());
    	}
    	if(quote.hasCanonicalSpeaker){
    		System.out.println("canonical speaker of quote");
		    System.out.println(quote.canonicalSpeaker().get());
    	}
    	CoreSentence quoteSentence = quote.sentences().get(0);
    	System.out.println("sentence index of quote: "+quoteSentence.coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class));
    	System.out.println();
    }
    // CoreQuote quote = quotes.get(0);
    // System.out.println("Example: quote 1");
    // System.out.println(quote);
    // System.out.println();

    // // original speaker of quote
    // // note that quote.speaker() returns an Optional
    // System.out.println("Example: original speaker of quote");
    // System.out.println(quote.speaker().get());
    // System.out.println();

    // // canonical speaker of quote
    // System.out.println("Example: canonical speaker of quote");
    // System.out.println(quote.canonicalSpeaker().get());
    // System.out.println();

     // sentence index of quote
    // CoreSentence quoteSentence = quote.sentences().get(0);
    // System.out.println("sentence index of quote: "+quoteSentence.coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class));

   
    // CoreQuote quote2 = quotes.get(1);
    // System.out.println("Example: quote 2");
    // System.out.println(quote2);
    // System.out.println();

    // // original speaker of quote
    // // note that quote.speaker() returns an Optional
    // System.out.println("Example: original speaker of quote");
    // System.out.println(quote2.speaker().get());
    // System.out.println();

    // // canonical speaker of quote
    // System.out.println("Example: canonical speaker of quote");
    // System.out.println(quote2.canonicalSpeaker().get());
    // System.out.println();

    //  // sentence index of quote
    // CoreSentence quoteSentence2 = quote2.sentences().get(0);
    // System.out.println("sentence index of quote: "+quoteSentence2.coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class));




    // CoreQuote quote3 = quotes.get(2);
    // System.out.println("Example: quote 3");
    // System.out.println(quote3);
    // System.out.println();

    // // original speaker of quote
    // // note that quote.speaker() returns an Optional
    // System.out.println("Example: original speaker of quote");
    // System.out.println(quote3.speaker().get());
    // System.out.println();

    // // canonical speaker of quote
    // System.out.println("Example: canonical speaker of quote");
    // System.out.println(quote3.canonicalSpeaker().get());
    // System.out.println();

    //  // sentence index of quote
    // CoreSentence quoteSentence3 = quote3.sentences().get(0);
    // System.out.println("sentence index of quote: "+quoteSentence3.coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class));
  }

}