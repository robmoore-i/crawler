package com.interview.webcrawler;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
class UrlRetriever extends PageRetriever {

    List<String> retrieveAllUrls(PageDocument pageDocument) {
        String[] urls = getContent(pageDocument);
        return hasContent(urls) ? List.of(urls) : List.empty();
    }

    @Override
    String[] getContent(PageDocument pageDocument) {
        return pageDocument.getUrls().split(" ");
    }
}
