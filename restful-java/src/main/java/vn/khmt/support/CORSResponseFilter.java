/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.khmt.support;

/**
 *
 * @author Luan
 */
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class CORSResponseFilter
implements ContainerResponseFilter {

        @Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		headers.add("Access-Control-Allow-Origin", "http://localhost:8383");	
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");	
                headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization");
	}

}
