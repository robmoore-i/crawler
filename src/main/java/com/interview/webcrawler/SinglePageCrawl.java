package com.interview.webcrawler;

import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SinglePageCrawl implements Crawler {
    private final UrlLoader urlLoader;
    private final WordRetriever wordRetriever;

    public SinglePageCrawl(UrlLoader urlLoader, WordRetriever wordRetriever) {
        this.urlLoader = urlLoader;
        this.wordRetriever = wordRetriever;
    }

    @Override
    public List<String> crawl(String url) throws IOException {
        HtmlPageDocument htmlPageDocument = new PageDocument(urlLoader.getHtmlPage(url));
        return wordRetriever.retrieve(htmlPageDocument);
    }
}
