package com.interview.webcrawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HttpIntegrationTest {

    @Autowired
    private
    MockMvc mockMvc;

    @Test
    public void itCrawlsASinglePage_WhenPageIsValid() throws Exception {
        String validUrl = "http://asdf.com";

        mockMvc.perform(get("/webcrawler/crawl?url=" + validUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("['About','asdf','What','is','asdf','?','asdf','Forums']"));
    }

    @Test
    public void itCrawlsMultiplePagesConcurrently_WhenPagesAreValid() throws Exception {
        String validUrl = "http://asdf.com";

        mockMvc.perform(get("/webcrawler/concurrent-crawl?url=" + validUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[About, asdf, What, is, asdf, ?, asdf, Forums, At, asdf, web, hosting, if, we're, not, up, there's, something, wrong., asdf, web, hosting, was, formed, to, promote, the, creative, expansion, of, the, Web, through, simplicity, and, experimentation., Send, questions, and, comments, to, semicolon.jkl@gmail.com, Hey,, why, can't, I, send, email, to, asdf@asdf.com?, the, ubiquitous, home, link, asdf, is, what, you, type, in, the, subject, line, when, you, have, no, subject., asdf, likes, jkl;, asdf, are, the, first, four, letters, you, learn, in, typing., asdf, is, free., asdf, is, what, happens, to, fads., asdf, is, nothing., asdf, just, is., asdf, is, green?, asdf, is, worth, 8, points, in, scrabble., asdf, is, the, title, of, an, unwritten, zine., aoeu, is, asdf's, cousin, asdf, is, a, four, letter, word., asdf, should, be, capitalized,, but, isn't., asdf, is, a, significant, difference., asdf, could, be, this, asdf, sounds, like, this?, About, Asdf, the, ubiquitous, home, link]"));
    }
}
