package com.example.demo.DAO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//---------------------------

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
//import org.json.JSONException;
//import org.json.JSONException;
//import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Entity.TheEmployee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.util.Base64Utils;
import javax.ws.rs.core.MediaType;
import java.lang.Object;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import  java.lang.Object;

@Service
public class DAOImp implements DAO{
	
	
	private static final JsonNode JsonNode = null;

	private static String basicAuth(String username, String password) {
	    return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}
	
	HttpClient client = HttpClient.newHttpClient();
	
	@Override
	public JsonNode  GetAll(String appUser, String appPass, String uri) throws InterruptedException, ExecutionException, IOException {
		
		
	    HttpRequest request = HttpRequest.newBuilder().GET()
		          .uri(URI.create(uri))
		          .header("Authorization", basicAuth(appUser, appPass))
		           .build();
	    
	    String s= client.sendAsync(request,BodyHandlers.ofString()) 
	          .thenApply(HttpResponse::body).get();
	    
	    
	    ObjectMapper jacksonObjMapper = new ObjectMapper();
	    return jacksonObjMapper.readTree(s);
	    
	}
	
	@Override
	public JsonNode Get(String empId,String appUser, String appPass, String uri) throws InterruptedException, ExecutionException, IOException {
		
	    HttpRequest request = HttpRequest.newBuilder().GET()
	          .uri(URI.create(uri+"/"+empId))
	          .header("Authorization", basicAuth(appUser,appPass))
	          .build();
	    
	    String s= client.sendAsync(request,BodyHandlers.ofString()) 
		          .thenApply(HttpResponse::body).get();
		    ObjectMapper jacksonObjMapper = new ObjectMapper();
		    return jacksonObjMapper.readTree(s);
	}

	@Override
	public ResponseEntity<ObjectNode> post(TheEmployee theEmployee,String appUser, String appPass, String uri) throws IOException, InterruptedException, ExecutionException {
		
		ObjectMapper jacksonObjMapper = new ObjectMapper();
		String rawjson= jacksonObjMapper.writeValueAsString(theEmployee);
		
		Date date = new Date(); 
		SimpleDateFormat    formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strDate = formatter.format(date);
		JsonNode json= jacksonObjMapper.readValue(rawjson,JsonNode.class);
		ObjectNode node = (ObjectNode) json;
		
	    HttpRequest request = HttpRequest.newBuilder()  
			            .uri(URI.create(uri))
			            .header("Authorization", basicAuth(appUser ,appPass))     
			            .setHeader("Content-Type", "application/json")
			            .POST(BodyPublishers.ofString(rawjson))
			            .build();
	    
	    
	    String s= client.sendAsync(request,BodyHandlers.ofString()).thenApply(HttpResponse::body).get();
//	    System.out.println(response.body().toString());
	    JsonNode new_json= jacksonObjMapper.readValue(s.toString(),JsonNode.class);   
//	    
	    String number="" ;
	    String sys_idtest="";
    	   for (JsonNode jsonNode : new_json) {
                 number = jsonNode.get("number").asText();      
                 sys_idtest=jsonNode.get("sys_id").asText();
             }
    	   
    	   node.put("number", number);
    	   node.put("sys_id", sys_idtest);
    	   node.with("Last Insertion").put(strDate,"Done");
	
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(node);
	}

	@Override
	public ResponseEntity<ObjectNode> put(TheEmployee theEmployee, String sys_id,String appUser, String appPass, String uri) throws IOException, InterruptedException, ExecutionException {
		
		//convert java object to json using writeValueAsString method
		ObjectMapper jacksonObjMapper = new ObjectMapper();
		String rawjson= jacksonObjMapper.writeValueAsString(theEmployee);
//		System.out.println(rawjson);
		
		   Date date = new Date(); 
		   SimpleDateFormat    formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		   String strDate = formatter.format(date);
		JsonNode json= jacksonObjMapper.readValue(rawjson.toString(),JsonNode.class);
		ObjectNode node = (ObjectNode) json;
		
	    	HttpRequest request = HttpRequest.newBuilder()
					            .uri(URI.create(uri+"/"+sys_id))
					            .header("Authorization", basicAuth(appUser, appPass))
					            .setHeader("Content-Type", "application/json")
					            .PUT(HttpRequest.BodyPublishers.ofString(rawjson))	            
					            .build();
//
	    HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    
	    String s= client.sendAsync(request,BodyHandlers.ofString()) .thenApply(HttpResponse::body).get();
//	    System.out.println(s);
	    JsonNode new_json= jacksonObjMapper.readValue(s.toString(),JsonNode.class);   
	    
	    String number="" ;
	    String sys_id1="";  
    	   for (JsonNode jsonNode : new_json) {
                 number = jsonNode.get("number").asText();      
                 sys_id1=jsonNode.get("sys_id").asText();
             }
    	   
    	   node.put("number", number);
    	   node.put("sys_id", sys_id1);
    	   node.with("Last Updation").put(strDate,"Done");
	    
	 
	    if(response.statusCode()==404) {
	    	System.out.println("Please enter correct ID");	
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    }
//	    System.out.println("Status Code: "+response.statusCode());
	    
	    return ResponseEntity.status(HttpStatus.ACCEPTED).body(node);
	}

	@Override
	public ResponseEntity<String> Delete(String sys_id,String appUser, String appPass, String uri) throws IOException, InterruptedException {
		
    	HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri+"/"+sys_id))
            .header("Authorization", basicAuth(appUser, appPass))
            .DELETE()
            .build();

	    HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    if(response.statusCode()==404) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    
//	    System.out.println("Status Code: "+response.statusCode());
	    
	    return ResponseEntity.of(Optional.of("Deleted sys_id = "+sys_id));
	}

	

}
