package com.heroes;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext req, ContainerResponseContext resp) throws IOException {
		
		MultivaluedMap<String, Object> headers = resp.getHeaders();

		headers.add("Access-Control-Allow-Origin", "*");
//		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");			
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		
	}

}