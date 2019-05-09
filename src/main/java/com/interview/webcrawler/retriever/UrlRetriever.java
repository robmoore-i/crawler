package com.interview.webcrawler.retriever;

import com.interview.webcrawler.PageDocument;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
public
class UrlRetriever implements PageRetriever {

    @Override
    public List<String> retrieve(PageDocument pageDocument) {
        return pageDocument.getUrls();
    }
}
