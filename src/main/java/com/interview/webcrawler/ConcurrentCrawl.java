package com.interview.webcrawler;

import com.interview.webcrawler.retriever.UrlRetriever;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class ConcurrentCrawl {

    private final UrlLoader urlLoader;
    private final WordRetriever wordRetriever;
    private final UrlRetriever urlRetriever;

    private List<String> wordList = List.empty();
    private List<Future<List<String>>> futureWords = List.empty();

    public ConcurrentCrawl(UrlLoader urlLoader, WordRetriever wordRetriever, UrlRetriever urlRetriever) {
        this.urlLoader = urlLoader;
        this.wordRetriever = wordRetriever;
        this.urlRetriever = urlRetriever;
    }

    List<String> crawl(String rootUrl) throws IOException {
        saveWords(getWordsFromUrl(rootUrl));

        iteratePages(rootUrl);

        for (Future<List<String>> f : futureWords) {
            saveWords(f.getOrElse(List.empty()));
        }

        return wordList;
    }

    private void iteratePages(String rootUrl) throws IOException {
        getWordsForAllUrlsInPage(rootUrl);
    }

    private void saveWords(List<String> wordsFromUrl) {
        wordList = wordList.appendAll(wordsFromUrl);
    }

    private void getWordsForAllUrlsInPage(String rootUrl) throws IOException {
        for (String path : urlRetriever.retrieve(getDocument(rootUrl))) {
            final String fullUrl = rootUrl + "/" + path;
            futureWords = futureWords.append(Future.of(() -> getWordsFromUrl(fullUrl)));
            iteratePages(fullUrl);
        }
    }

    private List<String> getWordsFromUrl(String rootUrl) throws IOException {
        PageDocument rootDocument = new PageDocument(getHtmlPage(rootUrl));
        return wordRetriever.retrieve(rootDocument);
    }

    private PageDocument getDocument(String url) throws IOException {
        return new PageDocument(getHtmlPage(url));
    }

    //TODO: Add memoization
    private String getHtmlPage(String rootUrl) throws IOException {
        return urlLoader.getHtmlPage(rootUrl);
    }
}
