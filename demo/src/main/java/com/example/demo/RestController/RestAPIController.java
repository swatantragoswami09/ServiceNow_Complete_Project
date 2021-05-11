package com.example.demo.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
//import org.json.JSONException;
//import org.json.JSONException;
//import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Entity.TheEmployee;
import com.example.demo.Service.Service;
import com.example.demo.Service.ServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.util.Base64Utils;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@Slf4j
public class RestAPIController {
	
	
	@Value("${app.name}")
	private String appUser;
	
	@Value("${app.pass}")
	private String appPass;
	
	@Value("${app.URl}")
	private String uri;
	
	
	@Autowired
	ServiceImp service;

	private static Logger logger = LoggerFactory.getLogger(RestAPIController.class);
	
	@GetMapping("/problem")
	public JsonNode GetAll () throws InterruptedException, ExecutionException ,JsonMappingException, JsonProcessingException,IOException{
		logger.info("Inside getall method in RestController");
		return service.GetAll(appUser,appPass,uri);	
	}
	
	
	@GetMapping("/problem/{id}")
	public JsonNode  Getld(@PathVariable String id) throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException, IOException {
		logger.info("Inside getId method in RestController");
		return service.Get(id,appUser,appPass,uri);
	}

	@PostMapping(value="/problem", produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
	public @ResponseBody ResponseEntity<ObjectNode>  Create(@RequestBody TheEmployee theEmployee) throws IOException, InterruptedException,  ExecutionException{
		logger.info("Inside Create method in RestController");
//		if(theEmployee ==null) {
//			throw new yourException;
//		}
		return service.post(theEmployee,appUser,appPass, uri);
			
	}
//	
	@PutMapping(value="/problem/{Sys_id}", produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
	public  ResponseEntity<ObjectNode> Update(@RequestBody TheEmployee  theEmployee,@PathVariable String Sys_id) throws IOException, InterruptedException,ExecutionException {
//		System.out.println(theEmployee.getShort_description());		
		logger.info("Inside Update method in RestController");
		return service.Put(theEmployee, Sys_id,appUser,appPass,uri);
	}
	
	@DeleteMapping(value="/problems/{sys_id}",produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
	public  ResponseEntity<String>  Delete(@PathVariable String sys_id) throws IOException, InterruptedException {
		logger.info("Inside Delete method in RestController");
		return service.Delete(sys_id,appUser,appPass,uri);
	}
		

}
