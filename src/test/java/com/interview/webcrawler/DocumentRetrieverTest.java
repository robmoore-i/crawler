package com.interview.webcrawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DocumentRetriever.class)
public class DocumentRetrieverTest {

    @Autowired
    DocumentRetriever documentRetriever;

    @MockBean
    UrlLoader urlLoader;

    @Test
    public void itGetsDocument() throws IOException {
        when(urlLoader.getHtmlPage(anyString())).thenReturn("<p> webpage </p>");

        assertTrue(documentRetriever.getDocument("url") != null);
    }

    @Test (expected = IOException.class)
    public void itThrowsException_WhenUnableToFetchDocument() throws IOException {
        when(urlLoader.getHtmlPage(anyString())).thenThrow(new IOException());
        documentRetriever.getDocument("url");
    }
}