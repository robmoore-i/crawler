package com.interview.webcrawler;

import com.interview.webcrawler.retriever.UrlRetriever;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class ConcurrentCrawl implements Crawler {
    private final UrlLoader urlLoader;
    private final UrlRetriever urlRetriever;

    private List<String> wordList = List.empty();
    private List<Future<List<String>>> futureWords = List.empty();
    private DocumentParser documentParser;

    public ConcurrentCrawl(UrlLoader urlLoader, UrlRetriever urlRetriever, DocumentParser documentParser) {
        this.urlLoader = urlLoader;
        this.urlRetriever = urlRetriever;
        this.documentParser = documentParser;
    }

    @Override
    public List<String> crawl(String rootUrl) throws IOException {
        saveWords(documentParser.getWordsFromUrl(rootUrl));

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
            futureWords = futureWords.append(Future.of(() -> documentParser.getWordsFromUrl(fullUrl)));
            iteratePages(fullUrl);
        }
    }

    private HtmlPageDocument getDocument(String url) {
        try {
            return new PageDocument(getHtmlPage(url));
        } catch (IOException e) {
            return new EmptyHtmlDocument();
        }
    }

    //TODO: Add memoization
    private String getHtmlPage(String rootUrl) throws IOException {
        return urlLoader.getHtmlPage(rootUrl);
    }
}
