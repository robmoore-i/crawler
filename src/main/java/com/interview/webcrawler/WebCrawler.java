package com.interview.webcrawler;

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

    public static final String MALFORMED_REQUEST_RESPONSE = "Invalid URL. Please make sure you include 'http://'";
    private ConcurrentCrawl concurrentCrawl;
    private SinglePageCrawl singlePageCrawl;

    public WebCrawler(SinglePageCrawl singlePageCrawl, ConcurrentCrawl concurrentCrawl) {
        this.singlePageCrawl = singlePageCrawl;
        this.concurrentCrawl = concurrentCrawl;
    }

    @RequestMapping(value = "/crawl", method = RequestMethod.GET)
    public ResponseEntity<String> crawl(@RequestParam("url") String url) {
        return crawlerResponse(singlePageCrawl, url);
    }

    @RequestMapping(value = "/concurrent-crawl", method = RequestMethod.GET)
    public ResponseEntity<String> concurrentCrawl(@RequestParam("url") String url) {
        return crawlerResponse(concurrentCrawl, url);
    }

    private ResponseEntity<String> crawlerResponse(Crawler crawler, String url) {
        try {
            return ResponseEntity.ok(crawler.crawl(url).asJava().toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MALFORMED_REQUEST_RESPONSE);
        }
    }

}
