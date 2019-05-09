package com.interview.webcrawler;

import com.interview.webcrawler.retriever.UrlRetriever;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ConcurrentCrawl.class, WordRetriever.class, UrlRetriever.class})
public class ConcurrentCrawlTest {

    @Autowired
    private
    ConcurrentCrawl concurrentCrawl;

    @MockBean
    private UrlLoader urlLoader;

    @Test
    public void itCrawlsSinglePageInsideRootPage_WhenRootPageHasOneUrl() throws Exception {
        final String rootUrl = "https://www.website.co.uk";
        final String subPage1Url = "/subPage1.html";

        String rootPage = "<a href='" + subPage1Url + "'> subPage1_link </a>";
        String subPage1 = "<p> subPage1 </p>";

        when(urlLoader.getHtmlPage(rootUrl)).thenReturn(rootPage);
        when(urlLoader.getHtmlPage(subPage1Url)).thenReturn(subPage1);

        assertEquals(List.of("subPage1_link", "subPage1"), concurrentCrawl.crawl(rootUrl));
    }

    @Test
    public void itCrawlsAllPagesInsideAllSubPages_WhenRootPageHasAtLeastOneUrl_AndSubPagesHaveAtLeastOneUrl() throws Exception {
        final String rootUrl = "https://www.website.co.uk";
        final String subPage1Url = "/subPage1.html";
        final String subPage2Url = "/subPage2.html";

        String rootPage = "<a href='" + subPage1Url + "'> subPage1_link </a>";
        String subPage1 = "<a href='" + subPage2Url + "'> subPage2_link </a>";
        String subPage2 = "<p> subPage2 </p>";

        when(urlLoader.getHtmlPage(rootUrl)).thenReturn(rootPage);
        when(urlLoader.getHtmlPage(subPage1Url)).thenReturn(subPage1);
        when(urlLoader.getHtmlPage(subPage2Url)).thenReturn(subPage2);

        assertEquals(List.of("subPage1_link", "subPage2_link", "subPage2"), concurrentCrawl.crawl(rootUrl));
    }
}