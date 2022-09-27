package api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class apiServiceTest {
    @Autowired
    private WebApplicationContext wac;

    MockMvc mockmvc;

    @Before
    public void before() {
        mockmvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testDefinition() throws Exception {
        MvcResult mvcResult = mockmvc.perform(
                MockMvcRequestBuilders.get("/definition").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getStatus() + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void testStatus() throws Exception {
        MvcResult mvcResult = mockmvc.perform(
                MockMvcRequestBuilders.post("/status").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getStatus() + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void testPredict() throws Exception {
        String s = "{\"keywords\":[\"民族自治地方国民经济和社会发展主要指标\"]}";
        MvcResult mvcResult = mockmvc.perform(
                MockMvcRequestBuilders.post("/predict").contentType(MediaType.APPLICATION_JSON).content(s))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getStatus() + mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void testInit() throws Exception {
        String s = "{\n" +
                "    \"relate_server\": [\n" +
                "          {\n" +
                "            \"node_name\": \"neo4j\",\n" +
                "            \"server_list\": [\n" +
                "                {\n" +
                "                    \"server_name\": \"Neo4j\",\n" +
                "                    \"server_config\": {\n" +
                "                        \"ip\": \"bolt://139.224.104.33\",\n" +
                "                        \"port\": \"1337\",\n" +
                "                        \"username\": \"neo4j\",\n" +
                "                        \"password\": \"neo4j999\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        MvcResult mvcResult = mockmvc.perform(
                MockMvcRequestBuilders.post("/init_server_config").contentType(MediaType.APPLICATION_JSON).content(s))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getStatus() + mvcResult.getResponse().getContentAsString());

    }
}
