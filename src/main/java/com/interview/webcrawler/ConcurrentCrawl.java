package com.interview.webcrawler;

import com.interview.webcrawler.retriever.UrlRetriever;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class ConcurrentCrawl {

    private final DocumentRetriever documentRetriever;
    private final WordRetriever wordRetriever;
    private final UrlRetriever urlRetriever;

    private List<String> wordList = List.empty();
    private List<Future<List<String>>> futureWords = List.empty();

    public ConcurrentCrawl(DocumentRetriever documentRetriever, WordRetriever wordRetriever, UrlRetriever urlRetriever) {
        this.documentRetriever = documentRetriever;
        this.wordRetriever = wordRetriever;
        this.urlRetriever = urlRetriever;
    }

    List<String> crawl(String rootUrl) throws IOException {
        saveWords(getWordsFromUrl(rootUrl));

        getWordsForAllUrlsInPage(rootUrl);

        for (Future<List<String>> f : futureWords) {
            saveWords(f.getOrElse(List.empty()));
        }

        return wordList;
    }

    private void getWordsForAllUrlsInPage(String rootUrl) throws IOException {
        final PageDocument document = documentRetriever.getDocument(rootUrl);
        for (String path : urlRetriever.retrieve(document)) {
            final String fullUrl = rootUrl + "/" + path;
            futureWords = futureWords.append(Future.of(() -> getWordsFromUrl(fullUrl)));
            getWordsForAllUrlsInPage(fullUrl);
        }
    }

    private void saveWords(List<String> wordsFromUrl) {
        wordList = wordList.appendAll(wordsFromUrl);
    }

    private List<String> getWordsFromUrl(String rootUrl) throws IOException {
        PageDocument rootDocument = documentRetriever.getDocument(rootUrl);
        return wordRetriever.retrieve(rootDocument);
    }
}
