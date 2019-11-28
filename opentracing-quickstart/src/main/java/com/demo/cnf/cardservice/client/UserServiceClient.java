package com.demo.cnf.cardservice.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@RegisterRestClient
public interface UserServiceClient {


   @GET
   @Path("/user")
   String user();
    
}