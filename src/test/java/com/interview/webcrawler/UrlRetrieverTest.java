package com.interview.webcrawler;

import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UrlRetriever.class)
public class UrlRetrieverTest {

    @Autowired
    private UrlRetriever urlRetriever;

    @Test
    public void itRetrievesAllUrlsFromPage_WhenPageHasURls() {
        List<String> expected = List.of("https://www.url1.com", "https://www.url2.com");

        PageDocument pageDocument = mock(PageDocument.class);
        when(pageDocument.getUrls()).thenReturn(expected);

        assertEquals(expected, urlRetriever.retrieveAll(pageDocument));
    }
}