package com.interview.webcrawler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/webcrawler")
public class WebCrawler {

    private UrlLoader urlLoader;
    private WordRetriever wordRetriever;

    public WebCrawler(UrlLoader urlLoader, WordRetriever wordRetriever) {
        this.urlLoader = urlLoader;
        this.wordRetriever = wordRetriever;
    }

    @RequestMapping(value = "/crawl", method = RequestMethod.GET)
    public List<String> crawl(@RequestParam("url") String url) {
        try {
            PageDocument pageDocument = new PageDocument(urlLoader.getPageDocument(url));
            return wordRetriever.retrieveAllWords(pageDocument).asJava();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
