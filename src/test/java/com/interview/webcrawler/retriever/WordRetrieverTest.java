package com.interview.webcrawler.retriever;

import com.interview.webcrawler.PageDocument;
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
@ContextConfiguration(classes = WordRetriever.class)
public class WordRetrieverTest {

    @Autowired
    private
    WordRetriever wordRetriever;

    @Test
    public void itRetrievesAllWordsFromAPage_WhenAPageHasWords() {
        List<String> expected = List.of("All", "your", "base", "are", "belong", "to", "us");

        PageDocument pageDocument = mock(PageDocument.class);
        when(pageDocument.getText()).thenReturn("All your base are belong to us");

        assertEquals(wordRetriever.retrieve(pageDocument), expected);
    }

    @Test
    public void itRetrievesNoWordsFromPage_WhenPageHasNoWords() {
        PageDocument pageDocument = mock(PageDocument.class);
        when(pageDocument.getText()).thenReturn("");

        assertEquals(wordRetriever.retrieve(pageDocument), List.empty());
    }
}