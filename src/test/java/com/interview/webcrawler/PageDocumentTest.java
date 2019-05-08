package com.interview.webcrawler;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageDocumentTest {

    @Test
    public void itGetsTextFromPage_WhenPageHasText() {
        String htmlPageWithText = "<p> All your base are belong to us </p>";
        PageDocument pageDocument = new PageDocument(htmlPageWithText);

        assertEquals(pageDocument.getText(), "All your base are belong to us");
    }

    @Test
    public void itGetNoTextBackFromPage_WhenPageHasNoText() {
        String emptyHtmlPage = "<p> </p>";
        PageDocument pageDocument = new PageDocument(emptyHtmlPage);

        assertEquals(pageDocument.getText(), "");
    }
}