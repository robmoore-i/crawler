package com.interview.webcrawler;

abstract class PageRetriever {
    boolean hasContent(String[] content) {
        return content.length > 0 && !content[0].equals("");
    }

    abstract String[] getContent(PageDocument pageDocument);
}
