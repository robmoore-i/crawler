package com.interview.webcrawler;

import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WebCrawler.class)
public class WebCrawlerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WordRetriever wordRetriever;

    @MockBean
    UrlLoader urlLoader;

    @Test
    public void itReturnsAListOfWords() throws Exception {
        final String validUrl = "https://www.website.co.uk";

        String htmlPage = "<p> All your base are belong to us </p>";
        when(urlLoader.getPageDocument(validUrl)).thenReturn(htmlPage);

        List<String> wordList = List.of("All", "your", "base", "are", "belong", "to", "us");
        when(wordRetriever.retrieveAllWords(any(PageDocument.class))).thenReturn(wordList);

        mockMvc.perform(get("/webcrawler/crawl?url=" + validUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("['All','your','base','are','belong','to','us']"));
    }
}