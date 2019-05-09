package com.interview.webcrawler;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
class WordRetriever extends PageRetriever {

    List<String> retrieveAllWords(PageDocument pageDocument) {
        String[] words = getContent(pageDocument);
        return hasContent(words) ? List.of(words) : List.empty();
    }

    @Override
    String[] getContent(PageDocument pageDocument) {
        return pageDocument.getText().split(" ");
    }
}
