package com.interview.webcrawler;

import io.vavr.collection.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageDocument implements HtmlPageDocument {

    private final String htmlPage;

    PageDocument(String htmlPage) {
        this.htmlPage = htmlPage;
    }

    @Override
    public String getText() {
        return Jsoup.parse(htmlPage).body().text();
    }

    @Override
    public List<String> getUrls() {
        Elements a = Jsoup.parse(htmlPage).body().select("a");

        List<String> elements = List.empty();
        for (Element element : a) {
            String absHref = element.attr("href");
            elements = elements.append(absHref);
        }

        return elements;
    }
}
