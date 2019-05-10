package com.interview.webcrawler;

import com.interview.webcrawler.retriever.UrlRetriever;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class ConcurrentCrawl extends Crawl {
    private final Logger logger = LoggerFactory.getLogger(ConcurrentCrawl.class);
    private final UrlRetriever urlRetriever;
    private final DocumentRetriever documentRetriever;

    private List<Future<List<String>>> futureWords = List.empty();

    public ConcurrentCrawl(DocumentRetriever documentRetriever, WordRetriever wordRetriever, UrlRetriever urlRetriever) {
        super(documentRetriever, wordRetriever);
        this.documentRetriever = documentRetriever;
        this.urlRetriever = urlRetriever;
    }

    @Override
    public List<String> crawl(String rootUrl) {
        saveWords(getWordsFromUrl(rootUrl));

        getWordsForAllUrlsInPage(rootUrl);

        for (Future<List<String>> f : futureWords) {
            saveWords(f.getOrElse(List.empty()));
        }

        return wordList;
    }

    private void getWordsForAllUrlsInPage(String rootUrl) {
        try {
            final PageDocument document = documentRetriever.getDocument(rootUrl);
            for (String path : urlRetriever.retrieve(document)) {
                final String fullUrl = rootUrl + "/" + path;
                futureWords = futureWords.append(Future.of(() -> getWordsFromUrl(fullUrl)));
                getWordsForAllUrlsInPage(fullUrl);
            }
        } catch (IOException e) {
            logger.debug("Failed to fetch page for: " + rootUrl);
        }
    }

    private void saveWords(List<String> wordsFromUrl) {
        wordList = wordList.appendAll(wordsFromUrl);
    }
}
