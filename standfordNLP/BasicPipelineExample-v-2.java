package com.ebook.drama.standfordNLP;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;


public class BasicPipelineExample {

//public static String text = "Hal felt his heart sink. He was not ready to tell Roger. He should begin to prepare his mind for the bad, news that Kaggs would not return? ‘Of course,’ and he tried to speak lightly, ‘there’s always a chance that we won’t see him again.’ Roger stopped and looked at him. ‘Then what will happen to us?’ ‘Oh, we’ll make out. We’ll have to. Now then, let’s try to flay the skin up at this corner. Boy, isn’t it thick!’";

//public static String text = "Hal and Roger are brothers. Hal felt his heart sink. He was not ready to tell Roger yet, but shouldn’t he begin to prepare his mind for the bad, news that Smith would not return? ‘Of course,’ and he said lightly, ‘there’s always a chance that we won’t see him again.’"; //Roger stopped and looked at him. ‘Then what will happen to us?’ ‘Oh, we’ll make out. We’ll have to. Now then, let’s try to flay the skin up at this corner. Boy, isn’t it thick!’";


	//public static String text = "‘And all we ask,’ Roger put in, ‘is for it to last a couple of weeks until Kaggs gets here.’";
	//public static String text = "‘That surely must be the plainest face in the whole Pacific Ocean,’ Hal said. He touched the hard sandpapery skin. ‘It’s not going to be easy to cut that.But we have good knives.We’ll slit him down the belly and then cut just behind the head and in front of the tail fin.’ The skin was as tough as emery cloth.";
	//public static String text = "‘That surely must be the plainest face in the whole Pacific Ocean,’ Hal said. He touched the hard sandpapery skin. ‘It’s not going to be easy to cut that. But we have good knives. We’ll slit him down the belly and then cut just behind the head and in front of the tail fin.’The skin was as tough as emery cloth.";
	//public static String text = "\"Hello,\" Joe said, \"how are you doing?\"";
	//public static String text = "He expected his brother to laugh at his idea but Hal shouted, ‘Why not? I think you’ve got something there.’";
	//public static String text = "\'That's fair enough,\' agreed Colonel Raymond.";
	//public static String text = "In the summer Joe Smith decided to go on vacation.  He said, ‘I'm going to Hawaii.  That July, vacationer Joe went to Hawaii.";
  // public static String text = "Joe Smith was born in California. " +
  //     "In 2017, he went to Paris, France in the summer. " +
  //     "His flight left at 3:00pm on July 10th, 2017. " +
  //     "After eating some escargot for the first time, Joe said, \"That was delicious!\" " +
  //     "He sent a postcard to his sister Jane Smith. " +
  //     "After hearing about Joe's trip, Jane decided she might go to France one day.";

  public static void main(String[] args) {
    // set up pipeline properties
    Properties props = new Properties();
    // set the list of annotators to run
    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
    props.setProperty("coref.algorithm", "neural");
    props.setProperty("singleQuotes", "true");
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

    // 10th token of the document
    CoreLabel token = document.tokens().get(6);
    System.out.println("Example: token");
    System.out.println(token);
    System.out.println();


    //number of senteces
    System.out.println("Number of Senteces");
    System.out.println(document.sentences().size());
    System.out.println();




    // text of the first sentence
    String sentenceText = document.sentences().get(3).text();
    System.out.println("Example: sentence");
    System.out.println(sentenceText);
    System.out.println();

    // second sentence
    CoreSentence sentence = document.sentences().get(1);

    // list of the part-of-speech tags for the second sentence
    List<String> posTags = sentence.posTags();
    System.out.println("Example: pos tags");
    System.out.println(posTags);
    System.out.println();

    // list of the ner tags for the second sentence
    List<String> nerTags = sentence.nerTags();
    System.out.println("Example: ner tags");
    System.out.println(nerTags);
    System.out.println();

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
    CoreEntityMention originalEntityMention = document.sentences().get(1).entityMentions().get(1);
    System.out.println("Example: original entity mention");
    System.out.println(originalEntityMention);
    System.out.println("Example: canonical entity mention");
    System.out.println(originalEntityMention.canonicalEntityMention().get());
    System.out.println();

    // get document wide coref info
    Map<Integer, CorefChain> corefChains = document.corefChains();
    System.out.println("Example: coref chains for document");
    System.out.println(corefChains);
    System.out.println();

    // get quotes in document
    List<CoreQuote> quotes = document.quotes();
    CoreQuote quote = quotes.get(0);
    System.out.println("Example: quote 1");
    System.out.println(quote);
    System.out.println();

    // original speaker of quote
    // note that quote.speaker() returns an Optional
    System.out.println("Example: original speaker of quote");
    System.out.println(quote.speaker().get());
    System.out.println();

    // canonical speaker of quote
    System.out.println("Example: canonical speaker of quote");
    System.out.println(quote.canonicalSpeaker().get());
    System.out.println();

     // sentence index of quote
    CoreSentence quoteSentence = quote.sentences().get(0);
    System.out.println("sentence index of quote: "+quoteSentence.coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class));

   
    CoreQuote quote2 = quotes.get(1);
    System.out.println("Example: quote 2");
    System.out.println(quote2);
    System.out.println();

    // original speaker of quote
    // note that quote.speaker() returns an Optional
    System.out.println("Example: original speaker of quote");
    System.out.println(quote2.speaker().get());
    System.out.println();

    // canonical speaker of quote
    System.out.println("Example: canonical speaker of quote");
    System.out.println(quote2.canonicalSpeaker().get());
    System.out.println();

     // sentence index of quote
    CoreSentence quoteSentence2 = quote2.sentences().get(0);
    System.out.println("sentence index of quote: "+quoteSentence2.coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class));




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