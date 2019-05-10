package com.interview.webcrawler.retriever;

import com.interview.webcrawler.HtmlPageDocument;
import io.vavr.collection.List;

interface PageRetriever {
    default boolean hasContent(String[] content) {
        return content.length > 0 && !content[0].equals("");
    }

    List<String> retrieve(HtmlPageDocument htmlPageDocument);
}
