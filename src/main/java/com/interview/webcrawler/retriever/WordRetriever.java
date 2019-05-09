package com.interview.webcrawler.retriever;

import com.interview.webcrawler.PageDocument;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
public class WordRetriever implements PageRetriever {

    @Override
    public List<String> retrieve(PageDocument pageDocument) {
        String[] words = getContent(pageDocument);
        return hasContent(words) ? List.of(words) : List.empty();
    }

    private String[] getContent(PageDocument pageDocument) {
        return pageDocument.getText().split(" ");
    }
}
