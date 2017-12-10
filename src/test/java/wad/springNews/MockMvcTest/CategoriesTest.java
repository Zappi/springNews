package wad.springNews.MockMvcTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriesTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/news/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void responseTypeHTML() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    public void responseContainsText() throws Exception {
        MvcResult res = mockMvc.perform(get("/"))
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("Categories"));
    }



}


