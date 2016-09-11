package nashorn.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import nashorn.model.ApiResponse;
import nashorn.model.ScriptRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-spring-servlet.xml")
@WebAppConfiguration
public class NashornControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext applicationContext;

	private ObjectMapper mapper;

	private ScriptRequest scriprRequest;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
		mapper = new ObjectMapper();
		scriprRequest = new ScriptRequest("for (step = 0; step < 10; step++) {print(step)}");
	}

	@Test
	public void testAddSctipt() throws Exception {
		mockMvc.perform(post("/script").content(mapper.writeValueAsString(scriprRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.status", is("ok"))).andReturn();
	}

	@Test
	public void testGetResultScriptById() throws Exception {
		MvcResult mvcResultAddScript = mockMvc
				.perform(post("/script").content(mapper.writeValueAsString(scriprRequest))
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.status", is("ok"))).andReturn();
		ApiResponse resultResponse = mapper.readValue(mvcResultAddScript.getResponse().getContentAsString(),
				ApiResponse.class);
		Thread.sleep(1000);
		mockMvc.perform(get("/script/" + resultResponse.getMessage()).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status", is("ok"))).andExpect(jsonPath("$.message").isNotEmpty()).andReturn();
	}

	@Test
	public void testGetListScripts() throws Exception {
		mockMvc.perform(post("/script").content(mapper.writeValueAsString(scriprRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.status", is("ok"))).andReturn();

		Thread.sleep(1000);

		mockMvc.perform(get("/script").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status", is("ok"))).andExpect(jsonPath("$.result").isNotEmpty()).andReturn();
	}
	
	@Test
	public void testRemoveScriptById() throws Exception {
		MvcResult mvcResultAddScript = mockMvc
				.perform(post("/script").content(mapper.writeValueAsString(scriprRequest))
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.status", is("ok"))).andReturn();

		ApiResponse resultResponse = mapper.readValue(mvcResultAddScript.getResponse().getContentAsString(),
				ApiResponse.class);

		Thread.sleep(1000);

		mockMvc.perform(delete("/script/" + resultResponse.getMessage())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status", is("ok"))).andReturn();
		
		Thread.sleep(1000);
		
		mockMvc.perform(get("/script/" + resultResponse.getMessage())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		        .andExpect(jsonPath("$.status", is("error"))).andReturn();
		
	}

}
