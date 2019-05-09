package com.interview.webcrawler;

import io.vavr.collection.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class PageDocument {

    private final String htmlPage;

    PageDocument(String htmlPage) {
        this.htmlPage = htmlPage;
    }

    String getText() {
        return Jsoup.parse(htmlPage).body().text();
    }

    List<String> getUrls() {
        final Elements a = Jsoup.parse(htmlPage).body().select("a");

        List<String> elements = List.empty();
        for (Element element : a) {
            String absHref = element.attr("href");
            elements = elements.append(absHref);
        }

        return elements;
    }
}
