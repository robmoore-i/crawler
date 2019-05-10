package com.interview.webcrawler;

import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class SingleCrawl {
    private final Logger logger = LoggerFactory.getLogger(SingleCrawl.class);
    private final DocumentRetriever documentRetriever;
    private final WordRetriever wordRetriever;

    private List<String> wordList = List.empty();

    public SingleCrawl(DocumentRetriever documentRetriever, WordRetriever wordRetriever) {
        this.documentRetriever = documentRetriever;
        this.wordRetriever = wordRetriever;
    }

    List<String> crawl(String rootUrl) {
        saveWords(getWordsFromUrl(rootUrl));
        return wordList;
    }

    private void saveWords(List<String> wordsFromUrl) {
        wordList = wordList.appendAll(wordsFromUrl);
    }

    private List<String> getWordsFromUrl(String rootUrl) {
        try {
            PageDocument rootDocument = documentRetriever.getDocument(rootUrl);
            return wordRetriever.retrieve(rootDocument);
        } catch (IOException e) {
            logger.debug("Failed to fetch page for: " + rootUrl);
            return List.empty();
        }
    }
}
