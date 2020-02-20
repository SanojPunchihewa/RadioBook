package com.example.radiobook;

public class Quote {
    private String quote;
    private String speaker;
    private boolean isQuoteAttributionTag;
    private String QATEmotion;

    public Quote(String quote, String speaker, boolean isQuoteAttributionTag, String QATEmotion) {
        this.quote = quote;
        this.speaker = speaker;
        this.isQuoteAttributionTag = isQuoteAttributionTag;
        this.QATEmotion = QATEmotion;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public boolean isQuoteAttributionTag() {
        return isQuoteAttributionTag;
    }

    public void setQuoteAttributionTag(boolean quoteAttributionTag) {
        isQuoteAttributionTag = quoteAttributionTag;
    }

    public String getQATEmotion() {
        return QATEmotion;
    }

    public void setQATEmotion(String QATEmotion) {
        this.QATEmotion = QATEmotion;
    }
}
