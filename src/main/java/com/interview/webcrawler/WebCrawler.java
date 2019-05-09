package com.interview.webcrawler;

import com.interview.webcrawler.retriever.WordRetriever;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/webcrawler")
class WebCrawler {

    private static final String MALFORMED_REQUEST_RESPONSE = "Invalid URL. Please make sure you include 'http://'";
    private final UrlLoader urlLoader;
    private final WordRetriever wordRetriever;

    public WebCrawler(UrlLoader urlLoader, WordRetriever wordRetriever) {
        this.urlLoader = urlLoader;
        this.wordRetriever = wordRetriever;
    }

    @RequestMapping(value = "/crawl", method = RequestMethod.GET)
    public ResponseEntity<String> crawl(@RequestParam("url") String url) {
        try {
            PageDocument pageDocument = new PageDocument(urlLoader.getPageDocument(url));
            return ResponseEntity.ok(wordRetriever.retrieveAll(pageDocument).asJava().toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MALFORMED_REQUEST_RESPONSE);
        }
    }
}
