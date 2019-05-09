package com.interview.webcrawler;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
class UrlRetriever implements PageRetriever {

    @Override
    public List<String> retrieveAll(PageDocument pageDocument) {
        return pageDocument.getUrls();
    }
}
