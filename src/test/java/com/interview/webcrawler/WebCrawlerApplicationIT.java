package com.interview.webcrawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebCrawlerApplicationIT {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void itCrawlsASinglePage_WhenPageIsValid() throws Exception {
		String validUrl = "http://asdf.com";

		mockMvc.perform(get("/webcrawler/crawl?url=" + validUrl))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json("['About','asdf','What','is','asdf','?','asdf','Forums']"));
	}

}
