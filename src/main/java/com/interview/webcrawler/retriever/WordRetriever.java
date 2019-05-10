package com.interview.webcrawler.retriever;

import com.interview.webcrawler.HtmlPageDocument;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
public class WordRetriever implements PageRetriever {

    @Override
    public List<String> retrieve(HtmlPageDocument htmlPageDocument) {
        String[] words = getContent(htmlPageDocument);
        return hasContent(words) ? List.of(words) : List.empty();
    }

    private String[] getContent(HtmlPageDocument htmlPageDocument) {
        return htmlPageDocument.getText().split(" ");
    }
}
