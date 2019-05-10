package com.interview.webcrawler.retriever;

import com.interview.webcrawler.HtmlPageDocument;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
public
class UrlRetriever implements PageRetriever {

    @Override
    public List<String> retrieve(HtmlPageDocument htmlPageDocument) {
        return htmlPageDocument.getUrls();
    }
}
