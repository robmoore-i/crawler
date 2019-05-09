package com.interview.webcrawler;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
class WordRetriever implements PageRetriever {

    @Override
    public List<String> retrieveAll(PageDocument pageDocument) {
        String[] words = getContent(pageDocument);
        return hasContent(words) ? List.of(words) : List.empty();
    }

    String[] getContent(PageDocument pageDocument) {
        return pageDocument.getText().split(" ");
    }
}
