package com.interview.webcrawler;

import org.jsoup.Jsoup;

class PageDocument {

    private final String htmlPage;

    PageDocument(String htmlPage) {
        this.htmlPage = htmlPage;
    }

    String getText() {
        return Jsoup.parse(htmlPage).body().text();
    }

    String getUrls() {
        return null;
    }
}
