package com.interview.webcrawler;

import com.interview.webcrawler.retriever.UrlRetriever;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConcurrentCrawl.class)
public class ConcurrentCrawlTest {

    @Autowired
    private
    ConcurrentCrawl concurrentCrawl;

    @MockBean
    private DocumentRetriever documentRetriever;

    @MockBean
    private WordRetriever wordRetriever;

    @MockBean
    private UrlRetriever urlRetriever;

    private final String rootUrl = "https://www.website.co.uk";
    private final String rootPageContent = "rootPageContent";
    private final String subPage1Link = "subPage1Link";
    private final String subPage1Content = "subPage1Content";
    private final String subPage2Link = "subPage2Link";
    private final String subPage2Content = "subPage2Content";

    private PageDocument mockDocument(String text, List<String> links) {
        PageDocument document = mock(PageDocument.class);
        when(document.getText()).thenReturn(text);
        when(document.getUrls()).thenReturn(links);
        return document;
    }

    private void mockPage(String pageContent, List<String> urls, String url) throws IOException {
        PageDocument rootDocument = mockDocument(pageContent, urls);
        when(documentRetriever.getDocument(url)).thenReturn(rootDocument);
        when(wordRetriever.retrieve(rootDocument)).thenReturn(List.of(pageContent));
        when(urlRetriever.retrieve(rootDocument)).thenReturn(urls);
    }

    @Before
    public void setUp() {
        Mockito.reset(documentRetriever, wordRetriever, urlRetriever);
    }

    @Test
    public void itCrawlsSinglePageInRootPage() throws Exception {
        mockPage(rootPageContent, List.of(subPage1Link), rootUrl);
        mockPage(subPage1Content, List.empty(), rootUrl + "/" + subPage1Link);

        assertEquals(List.of(rootPageContent, subPage1Content), concurrentCrawl.crawl(rootUrl));
    }

    @Test
    public void itCrawlsMultiplePagesInRootPage() throws Exception {
        mockPage(rootPageContent, List.of(subPage1Link, subPage2Link), rootUrl);
        mockPage(subPage1Content, List.empty(), rootUrl + "/" + subPage1Link);
        mockPage(subPage2Content, List.empty(), rootUrl + "/" + subPage2Link);

        assertEquals(List.of(rootPageContent, subPage1Content, subPage2Content), concurrentCrawl.crawl(rootUrl));
    }

    @Test
    public void itCrawlsAllPagesInsideAllSubPages_WhenRootPageHasAtLeastOneUrl_AndSubPagesHaveAtLeastOneUrl() throws Exception {
        mockPage(rootPageContent, List.of(subPage1Link), rootUrl);
        mockPage(subPage1Content, List.of(subPage2Link), rootUrl + "/" + subPage1Link);
        mockPage(subPage2Content, List.empty(), rootUrl + "/" + subPage1Link + "/" + subPage2Link);

        assertEquals(List.of(rootPageContent, subPage1Content, subPage2Content), concurrentCrawl.crawl(rootUrl));
    }
}