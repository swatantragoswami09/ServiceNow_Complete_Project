package com.example.demo.DAO;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.example.demo.Entity.TheEmployee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface DAO {
	public JsonNode  GetAll(String appUser, String appPass, String uri) throws InterruptedException, ExecutionException,JsonMappingException, JsonProcessingException,IOException;
	public   JsonNode Get(String id,String appUser, String appPass, String uri)throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException,IOException ;
	public ResponseEntity<ObjectNode>  post(TheEmployee theEmployee,String appUser, String appPass, String uri)throws IOException, InterruptedException,  ExecutionException;
	public ResponseEntity<ObjectNode> put(TheEmployee theEmployee, String sys_id,String appUser, String appPass, String uri)throws IOException, InterruptedException,ExecutionException ;
	public ResponseEntity<String>  Delete(String sys_id,String appUser, String appPassString ,String uri)throws IOException, InterruptedException;

}
