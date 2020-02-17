package com.example.radiobook;

public class Quote {
    private String quote;
    private String speaker;
    private boolean isQuoteAttributionTag;

    public Quote(String quote, String speaker, boolean isQuoteAttributionTag) {
        this.quote = quote;
        this.speaker = speaker;
        this.isQuoteAttributionTag = isQuoteAttributionTag;
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
}
