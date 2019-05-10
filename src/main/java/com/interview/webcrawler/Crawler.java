package com.interview.webcrawler;

import io.vavr.collection.List;

import java.io.IOException;

interface Crawler {
    List<String> crawl(String url) throws IOException;
}
