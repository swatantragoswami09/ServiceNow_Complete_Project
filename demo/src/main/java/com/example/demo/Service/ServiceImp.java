package com.example.demo.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.DAOImp;
import com.example.demo.Entity.TheEmployee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


@org.springframework.stereotype.Service
public class ServiceImp implements Service {

	@Autowired
	DAOImp dao;
	
	
	@Override
	public JsonNode   GetAll(String appUser, String appPass, String uri) throws InterruptedException, ExecutionException,JsonMappingException, JsonProcessingException ,IOException{
		return dao.GetAll(appUser,appPass,uri);
		
	}
	
	@Override
	public JsonNode Get(String id,String appUser, String appPass, String uri) throws InterruptedException, ExecutionException, JsonMappingException, JsonProcessingException, IOException  {
		return dao.Get(id,appUser,appPass,uri);
	}

	@Override
	public ResponseEntity<ObjectNode>  post(TheEmployee theEmployee,String appUser, String appPass, String uri) throws IOException, InterruptedException ,  ExecutionException{
		return dao.post(theEmployee,appUser,appPass,uri);
	}

	@Override
	public ResponseEntity<ObjectNode> Put(TheEmployee theEmployee, String sys_id,String appUser, String appPass, String uri) throws IOException, InterruptedException,ExecutionException {
//		System.out.println(theEmployee.getShort_description());
		return dao.put(theEmployee, sys_id,appUser,appPass,uri);
	}

	@Override
	public ResponseEntity<String>  Delete(String sys_id,String appUser, String appPass,String uri) throws IOException, InterruptedException {
		return dao.Delete(sys_id,appUser,appPass,uri);
	}

	

}
