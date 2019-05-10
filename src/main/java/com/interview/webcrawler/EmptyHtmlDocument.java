package com.interview.webcrawler;

import io.vavr.collection.List;

public class EmptyHtmlDocument implements HtmlPageDocument {
    @Override
    public String getText() {
        return "";
    }

    @Override
    public List<String> getUrls() {
        return List.empty();
    }
}
