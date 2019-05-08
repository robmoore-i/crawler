package com.interview.webcrawler;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
class WordRetriever {

    public List<String> retrieveAllWords(PageDocument pageDocument) {
        String[] words = getWords(pageDocument);
        return hasWords(words) ? List.of(words) : List.empty();
    }

    private boolean hasWords(String[] words) {
        return words.length > 0 && !words[0].equals("");
    }

    private String[] getWords(PageDocument pageDocument) {
        return pageDocument.getText().split(" ");
    }
}
