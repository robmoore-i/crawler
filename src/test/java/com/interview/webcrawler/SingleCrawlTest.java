package com.interview.webcrawler;

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
@ContextConfiguration(classes = SingleCrawl.class)
public class SingleCrawlTest {
    @Autowired
    private
    SingleCrawl singleCrawl;

    @MockBean
    private DocumentRetriever documentRetriever;

    @MockBean
    private WordRetriever wordRetriever;

    private final String rootUrl = "https://www.website.co.uk";
    private final String rootPageContent = "rootPageContent";

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
    }

    @Before
    public void setUp() {
        Mockito.reset(documentRetriever, wordRetriever);
    }

    @Test
    public void itCrawlsSinglePageInRootPage() throws Exception {
        mockPage(rootPageContent, List.empty(), rootUrl);

        assertEquals(List.of(rootPageContent), singleCrawl.crawl(rootUrl));
    }

    @Test
    public void itSkipsPage_WhenPageFetchFails() throws IOException {
        mockPage(rootPageContent, List.empty(), rootUrl);
        when(documentRetriever.getDocument(rootUrl)).thenThrow(new IOException());

        assertEquals(List.empty(), singleCrawl.crawl(rootUrl));
    }
}