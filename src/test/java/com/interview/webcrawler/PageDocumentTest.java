package com.interview.webcrawler;

import io.vavr.collection.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageDocumentTest {

    @Test
    public void itGetsTextFromPage_WhenPageHasText() {
        String htmlPageWithText = "<p> All your base are belong to us </p>";
        HtmlPageDocument pageDocument = new PageDocument(htmlPageWithText);

        assertEquals(pageDocument.getText(), "All your base are belong to us");
    }

    @Test
    public void itGetsNoTextBackFromPage_WhenPageHasNoText() {
        String emptyHtmlPage = "<p> </p>";
        HtmlPageDocument pageDocument = new PageDocument(emptyHtmlPage);

        assertEquals(pageDocument.getText(), "");
    }

    @Test
    public void itGetsUrlsFromPage_WhenPageHasUrls() {
        String htmlPageWithUrls = "<div>" +
                "<a href='/url1.html'> valid url1 </a> " +
                "<a href='/url2.html'> valid url2 </a>" +
                "</div>";
        HtmlPageDocument pageDocument = new PageDocument(htmlPageWithUrls);

        assertEquals(List.of("/url1.html", "/url2.html"), pageDocument.getUrls());
    }
}