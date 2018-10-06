package com.ebook.drama.standfordNLP;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import java.util.*;


public class BasicPipelineExample {
    private static String text = "Hal woke with a start. He found himself sitting up in bed, his spine tingling. What had roused him? A cry of some sort. The play of light and shadow in the tent told him that the camp-fire outside was still burning. It was meant to keep off dangerous visitors. Wild animals were all about -yet the sound he had heard did not seem the voice of an animal. Still, he could be mistaken. This was his first night in the African wilds. Beside the camp-fire earlier in the evening he and his younger brother, Roger, had listened to the voices of the forest while their Father, John Hunt, told them what they were hearing. Hunt had said, ‘It’s like an orchestra,’. ‘Those high violins you hear are being played by the jackals. That crazy trombone - the hyena is playing it. The hippo is on the bass tuba. Doesn’t that wart-hog’s 'arnk-arnk-arnk' sound just like a snare drum? And listen - far away … you can just hear it, a lion on the 'cello'.’ ‘Who’s that with the saxophone?’ asked Roger. ‘The elephant. He’s good on the trumpet too.’ A sharp grinding roar made the boys jump. Whatever made it was very close to the camp. It sounded like a rough file being dragged over the edge of a tin roof.";
    private Properties props = new Properties();
    private CoreSentence sentence;
    private CoreDocument document;

    public BasicPipelineExample(){
        
    }
    
    public void createDocument(String text){
        document = new CoreDocument(text);
    }

    public void setupPipeline(){
        //Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        props.setProperty("singleQuotes", "true");
        props.setProperty("allowEmbeddedSame", "false");
    }

    public void buildPipeline(){
         // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    }

    public void noOfSentences(){
        System.out.println("Number of Senteces");
        System.out.println(document.sentences().size());
        System.out.println();
    }

    public void textOfTheFirstSentence(){
        // text of the first sentence
        String sentenceText = document.sentences().get(9).text();
        System.out.println("Example: sentence");
        System.out.println(sentenceText);
        System.out.println();
    }

    public void secondSentence(){
        sentence = document.sentences().get(10);
    }

    public void constituencyParseForSecondSent(){
        Tree constituencyParse = sentence.constituencyParse();
        System.out.println("Example: constituency parse");
        System.out.println(constituencyParse);
        System.out.println();
    }
    
    public void dependencyParseForSencondSent(){
        SemanticGraph dependencyParse = sentence.dependencyParse();
        System.out.println("Example: dependency parse");
        System.out.println(dependencyParse);
        System.out.println();
    }

    public void entityMentionsSecondSent(){
        List<CoreEntityMention> entityMentions = sentence.entityMentions();
        System.out.println("Example: entity mentions");
        System.out.println(entityMentions);
        System.out.println();
    }

    public void coreferenceBetweenEntityMentions(){
        CoreEntityMention originalEntityMention = document.sentences().get(0).entityMentions().get(0);
        System.out.println("Example: original entity mention");
        System.out.println(originalEntityMention);
        System.out.println("Example: canonical entity mention");
        System.out.println(originalEntityMention.canonicalEntityMention().get());
        System.out.println();
    }

    public void getQuotesInTheDocument(){
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
    }    

  public static void main(String[] args) {
    BasicPipelineExample bpe = new BasicPipelineExample();
    bpe.setupPipeline();
    bpe.buildPipeline();
    bpe.createDocument(text);

    bpe.noOfSentences();
    bpe.textOfTheFirstSentence();
    bpe.secondSentence();
    bpe.constituencyParseForSecondSent();
    bpe.dependencyParseForSencondSent();
    bpe.entityMentionsSecondSent();
    bpe.coreferenceBetweenEntityMentions();
    bpe.getQuotesInTheDocument();
  }
 

}