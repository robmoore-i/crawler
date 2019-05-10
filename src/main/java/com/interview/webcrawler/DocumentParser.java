package com.interview.webcrawler;

import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentParser {
    private final UrlLoader urlLoader;
    private final WordRetriever wordRetriever;

    public DocumentParser(UrlLoader urlLoader, WordRetriever wordRetriever) {
        this.urlLoader = urlLoader;
        this.wordRetriever = wordRetriever;
    }

    List<String> getWordsFromUrl(String rootUrl) throws IOException {
        HtmlPageDocument rootDocument = new PageDocument(urlLoader.getHtmlPage(rootUrl));
        return wordRetriever.retrieve(rootDocument);
    }
}
