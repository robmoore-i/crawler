package com.interview.webcrawler;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class UrlLoader {

    String getHtmlPage(String pageUrl) throws IOException {
        return Jsoup.connect(pageUrl).get().html();
    }
}
