package com.interview.webcrawler;

import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
class SingleCrawl extends Crawl {

    SingleCrawl(DocumentRetriever documentRetriever, WordRetriever wordRetriever) {
        super(documentRetriever, wordRetriever);
    }

    @Override
    public List<String> crawl(String rootUrl) {
        saveWords(getWordsFromUrl(rootUrl));
        return wordList;
    }

    private void saveWords(List<String> wordsFromUrl) {
        wordList = wordList.appendAll(wordsFromUrl);
    }
}
