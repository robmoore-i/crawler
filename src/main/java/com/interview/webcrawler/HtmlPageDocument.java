package com.interview.webcrawler;

import io.vavr.collection.List;

public interface HtmlPageDocument {
    String getText();

    List<String> getUrls();
}
