package com.interview.webcrawler;

import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WebCrawler.class)
public class WebCrawlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SinglePageCrawl singlePageCrawl;

    @MockBean
    private ConcurrentCrawl concurrentCrawl;

    @Test
    public void itReturnsAListOfWords() throws Exception {
        final String validUrl = "https://www.website.co.uk";

        when(singlePageCrawl.crawl(validUrl)).thenReturn(List.of("All", "your", "base", "are", "belong", "to", "us"));

        mockMvc.perform(get("/webcrawler/crawl?url=" + validUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("['All','your','base','are','belong','to','us']"));
    }

    @Test
    public void itFailsGracefully_WhenFailingToGetPage() throws Exception {
        final String validUrl = "https://www.website.co.uk";

        when(singlePageCrawl.crawl(validUrl)).thenThrow(new IOException());

        mockMvc.perform(get("/webcrawler/crawl?url=" + validUrl))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Invalid URL. Please make sure you include 'http://'"));
    }

    @Test
    public void itReturnsAllWordsForConcurrentCrawl() throws Exception {
        final String rootUrl = "https://www.website.co.uk";

        when(concurrentCrawl.crawl(rootUrl)).thenReturn(List.of("subPage1_link", "subPage2_link", "subPage2"));

        mockMvc.perform(get("/webcrawler/concurrent-crawl?url=" + rootUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("['subPage1_link', 'subPage2_link', 'subPage2']"));
    }
}