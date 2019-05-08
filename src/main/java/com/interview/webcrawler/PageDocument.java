package com.interview.webcrawler;

import org.jsoup.Jsoup;

class PageDocument {

    private final String htmlPage;

    public PageDocument(String htmlPage) {
        this.htmlPage = htmlPage;
    }

    public String getText() {
        return Jsoup.parse(htmlPage).body().text();
    }
}
