package com.example.radiobook;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreQuote;

import java.util.ArrayList;
import java.util.List;

public class QuotePreprocessor {

    private CoreDocument document;
    private Annotation annotation;
    private int lambda = 3; // lambda an estimation of a phrase about 3 or more words
    private String defaultSpeaker = "narrator";
    private final String TOKENIZER_REGEX = " ";
    private ArrayList<Quote> quoteList = new ArrayList<>();

    public QuotePreprocessor(CoreDocument coreDocument) {
        this.document = coreDocument;
    }

    public QuotePreprocessor(Annotation annotation) {
        this.annotation = annotation;
    }

    // Classification of quotes
    //
    public Quote[] classify() {
        String prevSentence = "", currentSentence = "", prevSpeaker = "", currentSpeaker;
        int prevQuoteLastIdx = 0, prevQuoteLastSentenceIdx = 0, prevSentenceIdx = 0, currentSentenceIdx = 0, quoteStartingIdx, quoteLength = 0;
        boolean isTag;
        List<CoreQuote> quotes = document.quotes();
        for (CoreQuote quote : quotes) {
            currentSentence = quote.sentences().get(0).text();
            currentSentenceIdx = quote.sentences().get(0).coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class);
            quoteStartingIdx = getQuoteStartingIdx(currentSentence, quote.text());

            currentSpeaker = quote.hasSpeaker ? quote.speaker().get() : (quote.hasCanonicalSpeaker?quote.canonicalSpeaker().get():defaultSpeaker);
            quoteLength = quoteStartingIdx + quote.text().length();

            if ((currentSentenceIdx - prevSentenceIdx) == 1 && quoteStartingIdx == 0) {
                if (quote.sentences().size() > 1) {
                    int noOfQuoteSentences = quote.sentences().size();
                    quoteList.add(createQuote(currentSentenceIdx, currentSentenceIdx + noOfQuoteSentences - 1, quoteStartingIdx, quoteLength, currentSpeaker, false, true));
                } else {
                    quoteList.add(createQuote(currentSentenceIdx, currentSentenceIdx, quoteStartingIdx, quoteLength, currentSpeaker, false, true));
                }
            } else {
                quoteList.add(createQuote(prevSentenceIdx, currentSentenceIdx, prevQuoteLastIdx, quoteStartingIdx, defaultSpeaker, true, false));
                if (quote.sentences().size() > 1) {
                    int noOfQuoteSentences = quote.sentences().size();
                    quoteList.add(createQuote(currentSentenceIdx, currentSentenceIdx + noOfQuoteSentences - 1, quoteStartingIdx, quoteLength, currentSpeaker, false, true));
                } else {
                    quoteList.add(createQuote(currentSentenceIdx, currentSentenceIdx, quoteStartingIdx, quoteLength, currentSpeaker, false, true));
                }
            }

            prevQuoteLastSentenceIdx = currentSentenceIdx + quote.sentences().size() - 1;
            prevSentenceIdx = currentSentenceIdx;
            prevQuoteLastIdx = quoteStartingIdx + quote.text().length();
            //prevSpeaker = currentSpeaker;
        }
        int lastDocumentSentenceIdx = document.sentences().size() - 1;
        if (prevQuoteLastSentenceIdx < lastDocumentSentenceIdx) {
            quoteList.add(createQuote(currentSentenceIdx, lastDocumentSentenceIdx, prevQuoteLastIdx, 0, defaultSpeaker, true, false));
        }
        return null;
    }

    // Checks whether a quote is a tag and if so returns true otherwise false
    // Sentence :
    // tagStartingIdx:
    // tagEndingIdx:
    private boolean isQuoteTag(String sentence, int tagStartingIdx, int tagEndingIdx) {
        String tag = sentence.substring(tagStartingIdx, tagEndingIdx);
        String[] words = tag.split(TOKENIZER_REGEX);
        return words.length < lambda;
    }

    // Create a quote
    // startingSentenceIdx: index of the Quote starting sentence
    // endingSentenceIdx: index of the Quote ending sentence
    // startingQuoteIdx: starting index of the Quote in starting sentence
    // endingQuoteIdx: last index of the Quote in ending sentence
    // quote
    // speaker: quote’s speaker
    // tag: is a quote attribution tag
    private Quote createQuote(int startingSentenceIdx, int endingSentenceIdx, int startingQuoteIdx, int endingQuoteIdx, String speaker, boolean isTag, boolean isQuote) {
        //String startingSentence = getSentenceByIndex(startingSentenceIdx);
        String endingSentence = getSentenceByIndex(endingSentenceIdx);
        StringBuffer strBuffer = new StringBuffer();

        if (isQuote) {
            if (startingSentenceIdx == endingSentenceIdx) {
                strBuffer.append(endingSentence, startingQuoteIdx, endingQuoteIdx);
            } else {
                StringBuffer tempBuffer = new StringBuffer();
                for (int i = startingSentenceIdx; i <= endingSentenceIdx; i++) {
                    tempBuffer.append(document.sentences().get(i).text()).append(" ");
                }
                strBuffer.append(tempBuffer.substring(startingQuoteIdx));
            }
        } else {
            if (startingSentenceIdx == endingSentenceIdx) {
                strBuffer.append(endingSentence, startingQuoteIdx, endingQuoteIdx);
            } else {
                StringBuffer tempBuffer = new StringBuffer();
                for (int i = startingSentenceIdx; i < endingSentenceIdx; i++) {
                    tempBuffer.append(document.sentences().get(i).text()).append(" ");
                }
                strBuffer.append(tempBuffer.substring(startingQuoteIdx));

                if (endingQuoteIdx > 0)
                    strBuffer.append(endingSentence, 0, endingQuoteIdx);
            }
        }

        if (strBuffer.length() > 0)
            return new Quote(strBuffer.toString(), speaker, isTag);
        else
            return null;
    }

    // Returns the starting index of a quote in a sentence
    private int getQuoteStartingIdx(String sentence, String quote) {
        return Math.max(sentence.indexOf(quote), 0);
    }

    private String getSentenceByIndex(int index) {
        if (document != null)
            return document.sentences().get(index).text();
        else if (annotation != null)
            return new CoreDocument(annotation).sentences().get(index).text();
        else
            return null;
    }

    public void printQuotes() {
        for (Quote quote : quoteList) {
            if (quote != null) {
                System.out.println("Quote: " + quote.getQuote());
                System.out.println("Speaker: " + quote.getSpeaker());
                System.out.println("IsTag: " + quote.isQuoteAttributionTag());
                System.out.println();
            }
        }
    }
}
