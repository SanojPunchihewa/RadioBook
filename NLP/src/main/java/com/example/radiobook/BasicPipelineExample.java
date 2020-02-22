package com.example.radiobook;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BasicPipelineExample {

    //public static String text = "Hal felt his heart sink. He was not ready to tell Roger. He should begin to prepare his mind for the bad, news that Kaggs would not return? ‘Of course,’ and he tried to speak lightly, ‘there’s always a chance that we won’t see him again.’ Roger stopped and looked at him. ‘Then what will happen to us?’ ‘Oh, we’ll make out. We’ll have to. Now then, let’s try to flay the skin up at this corner. Boy, isn’t it thick!’";
    //public static String text = "Hal and Roger are brothers. Hal felt his heart sink. He was not ready to tell Roger yet, but shouldn’t he begin to prepare his mind for the bad, news that Smith would not return? ‘Of course,’ and he said lightly, ‘there’s always a chance that we won’t see him again.’";
    //public static String text = "Hal woke with a start. He found himself sitting up in bed, his spine tingling. What had roused him? A cry of some sort. The play of light and shadow in the tent told him that the camp-fire outside was still burning. It was meant to keep off dangerous visitors. Wild animals were all about -yet the sound he had heard did not seem the voice of an animal. Still, he could be mistaken. This was his first night in the African wilds. Beside the camp-fire earlier in the evening he and his younger brother, Roger, had listened to the voices of the forest while their Father, John Hunt, told them what they were hearing. Hunt had said, ‘It’s like an orchestra,’. ‘Those high violins you hear are being played by the jackals. That crazy trombone - the hyena is playing it. The hippo is on the bass tuba. Doesn’t that wart-hog’s 'arnk-arnk-arnk' sound just like a snare drum? And listen - far away … you can just hear it, a lion on the 'cello'.’ ‘Who’s that with the saxophone?’ asked Roger. ‘The elephant. He’s good on the trumpet too.’ A sharp grinding roar made the boys jump. Whatever made it was very close to the camp. It sounded like a rough file being dragged over the edge of a tin roof.";
    //public static String text = "Roger was awake. Hal woke with saying, ‘What will happen to us?’ Hal looked worried. Hunt said ‘Calm down all’.";
    //public static String text = "‘You always look so cool,’ Daisy repeated innocently. She had told him that she loved him, and Tom Buchanan saw. He exclaimed. His mouth opened a little and he looked at Gatsby and then back at Daisy as if he had just recognized her as someone he knew a long time ago. ‘You resemble the advertisement of the man,’ she went on innocently ‘You know the advertisement of the man’";
    //public static String text = "‘I want ice cream,’ Ann cried aloud. Mary angrily said ‘Do your home work, then you can have it’.";
    //public static String text = "‘Ah’ said he, ‘I have this load to carry: to be sure it is silver, but it is so heavy that I can’t hold up my head, and you must know it hurts my shoulder sadly.’";
    public static String text;
    public static void main(String[] args) {
        
        // comment this if not used
        // reading from a file 
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("story_books/HANS_IN_LUCK_THE_BROTHERS_GRIMM_FAIRY_TALES.txt"));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            text = sb.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set up pipeline properties
        Properties props = new Properties();

        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");

        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        props.setProperty("singleQuotes", "true");
        props.setProperty("allowEmbeddedSame", "false");
        props.setProperty("quote.asciiQuotes", "true");
        props.setProperty("quote.extractUnclosedQuotes", "true");
        props.setProperty("unicodeQuotes", "true");

        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument document = new CoreDocument(text);
        // Annotate the document
        pipeline.annotate(document);

        //number of sentences
        System.out.println("Number of Sentences");
        System.out.println(document.sentences().size());
        System.out.println();

//        // coreference between entity mentions
//        CoreEntityMention originalEntityMention = document.sentences().get(0).entityMentions().get(0);
//        System.out.println("Example: original entity mention");
//        System.out.println(originalEntityMention);
//        System.out.println("Example: canonical entity mention");
//        System.out.println(originalEntityMention.canonicalEntityMention().get());
//        System.out.println();

        QuotePreprocessor quotePreprocessor = new QuotePreprocessor(document);
        quotePreprocessor.generateEmotionsMap();
        quotePreprocessor.classify();
        quotePreprocessor.polishIsTagSpeaker();
        quotePreprocessor.printQuotes();
        quotePreprocessor.printSpeakerGenders();

//        // get quotes in document
//        int i = 1;
//        List<CoreQuote> quotes = document.quotes();
//        for (CoreQuote quote : quotes) {
//            System.out.println("quote: " + (i++));
//            System.out.println(quote);
//            List<CoreSentence> sentences = quote.sentences();
//            System.out.println("Sentence Index: " + quote.sentences().get(0).coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class));
//            if (quote.hasSpeaker) {
//                System.out.println("original speaker of quote");
//                System.out.println(quote.speaker().get());
//            }
//            if (quote.hasCanonicalSpeaker) {
//                System.out.println("canonical speaker of quote");
//                System.out.println(quote.canonicalSpeaker().get());
//            }
//            System.out.println();
//        }
    }
}
