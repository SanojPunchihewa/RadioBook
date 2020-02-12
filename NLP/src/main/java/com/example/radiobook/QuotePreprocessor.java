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
    private int lambda = 3; // lambda an estimation of phrase about 3 or more words
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
        int prevQuoteLastIdx = 0, prevSentenceIdx = 0, currentSentenceIdx, quoteIdx, length;
        boolean isTag;
        List<CoreQuote> quotes = document.quotes();
        for (CoreQuote quote : quotes) {
            currentSentence = quote.sentences().get(0).text();
            currentSentenceIdx = quote.sentences().get(0).coreMap().get(CoreAnnotations.SentenceIndexAnnotation.class);
            length = (prevSentence.isEmpty()) ? 0 : prevSentence.length();
            quoteIdx = getQuoteStartingIdx(currentSentence, quote.text());
            if (prevSentenceIdx < currentSentenceIdx) {
                isTag = isQuoteTag(prevSentence, prevQuoteLastIdx, length);
                currentSpeaker = isTag ? defaultSpeaker : prevSpeaker;
                // TODO check here
                createQuote(prevSentenceIdx, prevSentenceIdx, prevQuoteLastIdx, length, currentSpeaker, isTag);
                prevQuoteLastIdx = 0;
                length = prevSentence.length();
                // TODO check here
                createQuote(prevSentenceIdx + 1, currentSentenceIdx - 1, prevQuoteLastIdx, length, defaultSpeaker, false);
                prevSentenceIdx = currentSentenceIdx;
            }
            isTag = isQuoteTag(currentSentence, prevQuoteLastIdx, quoteIdx);
            currentSpeaker = quote.speaker().get();
            currentSpeaker = isTag ? currentSpeaker : defaultSpeaker;
            // TODO check here
            quoteList.add(createQuote(currentSentenceIdx, currentSentenceIdx, prevQuoteLastIdx, quoteIdx, currentSpeaker, isTag));
            prevQuoteLastIdx = quote.text().length() - 1;
            // TODO check here
            createQuote(currentSentenceIdx, currentSentenceIdx, quoteIdx, prevQuoteLastIdx, currentSpeaker, isTag);
            prevSpeaker = currentSpeaker;
            prevSentence = currentSentence;
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
    private Quote createQuote(int startingSentenceIdx, int endingSentenceIdx, int startingQuoteIdx, int endingQuoteIdx, String speaker, boolean isTag) {
        String startingSentence = getSentenceByIndex(startingSentenceIdx);
        String endingSentence = getSentenceByIndex(endingSentenceIdx);
        StringBuffer strBuffer = new StringBuffer();
        if (startingSentenceIdx == endingSentenceIdx) {
            strBuffer.append(startingSentence, startingQuoteIdx, endingQuoteIdx);
        } else {
            strBuffer.append(startingSentence.substring(startingQuoteIdx));
            for (int i = startingSentenceIdx + 1; i < endingQuoteIdx; i++) {
                strBuffer.append(document.sentences().get(i).text());
            }
            strBuffer.append(endingSentence, 0, endingQuoteIdx);
        }
        if (strBuffer.length() > 0)
            return new Quote(strBuffer.toString(), speaker, isTag);
        else
            return null;
    }

    // Returns the starting index of a quote in a sentence
    private int getQuoteStartingIdx(String sentence, String quote) {
        return sentence.indexOf(quote);
    }

    private String getSentenceByIndex(int index) {
        if (document != null)
            return document.sentences().get(index).text();
        else if (annotation != null)
            return new CoreDocument(annotation).sentences().get(index).text();
        else
            return null;
    }
}
