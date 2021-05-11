package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.RestController.RestAPIController;
import com.example.demo.Service.ServiceImp;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@WebMvcTest(value=RestAPIController.class)
@RunWith(SpringRunner.class)
public class RestAPIControllerTest {
	
	private static final Object PRB0000032 = null;

	@Autowired
	private MockMvc mvc;
	
	@Mock
	private ServiceImp service;
	
	@InjectMocks
	private RestAPIController controller;
		
	@Autowired
    	WebApplicationContext wac;
	
	
	
	@Before
	public void before() {
//	    MockitoAnnotations.initMocks(this);
	    this.mvc = MockMvcBuilders
	    		.webAppContextSetup(this.wac)
	    		.dispatchOptions(true)
	    		.build();
	}
	
	@Test
	public void testGetAll() throws Exception {
		 mvc.perform( MockMvcRequestBuilders.get("/api/problem")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.result[*].sys_id").isNotEmpty());
		 
	}
	
	@Test
	public void testGetId() throws Exception {
		mvc.perform( MockMvcRequestBuilders
			      .get("/api/problem/{id}",PRB0000032)
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.result[*].number").value(PRB0000032));
	}
//	
	@Test
	public void TestPost() throws Exception 
	{
		Map<String, String> mapObj=new HashMap<String, String>();
		mapObj.put("hello", "hi");
		ObjectMapper jacksonObjMapper = new ObjectMapper();
		JsonNode json= jacksonObjMapper.readTree((JsonParser) mapObj);
	  mvc.perform( MockMvcRequestBuilders
	      .post("/api/problem")
	      .content(json.toString())
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.result[*].sys_id").exists());
	}
	
	@Test
	public void testdelete() throws Exception 
	{
	  mvc.perform( MockMvcRequestBuilders.delete("/api/problems/{sys_id}", 1) )
	        .andExpect(status().isAccepted());
	}

}
