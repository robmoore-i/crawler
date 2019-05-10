package com.interview.webcrawler;

import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SinglePageCrawl {

    private UrlLoader urlLoader;
    private WordRetriever wordRetriever;

    public SinglePageCrawl(UrlLoader urlLoader, WordRetriever wordRetriever) {
        this.urlLoader = urlLoader;
        this.wordRetriever = wordRetriever;
    }

    List<String> crawl(String url) throws IOException {
        PageDocument pageDocument = new PageDocument(urlLoader.getHtmlPage(url));
        return wordRetriever.retrieve(pageDocument);
    }
}
