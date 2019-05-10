package com.interview.webcrawler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/webcrawler")
class WebCrawler {

    private final Crawl concurrentCrawl;
    private final Crawl singleCrawl;

    public WebCrawler(SingleCrawl singleCrawl, ConcurrentCrawl concurrentCrawl) {
        this.concurrentCrawl = concurrentCrawl;
        this.singleCrawl = singleCrawl;
    }

    @RequestMapping(value = "/single-crawl", method = RequestMethod.GET)
    public List<String> crawl(@RequestParam("url") String url) {
        return singleCrawl.crawl(url).asJava();
    }

    @RequestMapping(value = "/concurrent-crawl", method = RequestMethod.GET)
    public List<String> concurrentCrawl(@RequestParam("url") String url) {
        return concurrentCrawl.crawl(url).asJava();
    }
}
