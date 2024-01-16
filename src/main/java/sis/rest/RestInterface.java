/* Copyright 2018-2021 Universidad Politécnica de Madrid (UPM).
 *
 * Authors:
 *    Mario San Emeterio de la Parte
 *    Vicente Hernández Díaz
 *    José-Fernan Martínez Ortega
 *    Néstor Lucas Martínez
 *
 * This software is distributed under a dual-license scheme:
 *
 * - For academic uses: Licensed under GNU Affero General Public License as
 *                      published by the Free Software Foundation, either
 *                      version 3 of the License, or (at your option) any
 *                      later version.
 *
 * - For any other use: Licensed under the Apache License, Version 2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * You can get a copy of the license terms in licenses/LICENSE.
 *
 */

package sis.rest;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.Request;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sis.config.Constants;
import sis.translator.Mapping;


@Path("/analyse/")
public class RestInterface {

	private static final Logger log = Logger.getLogger(RestInterface.class);
	public static final URI docsUri=URI.create(WebApp.BASE_URI+"sis/");
	
	private final Response invalidJsonldException = Response.status(405).entity("405: \"Invalid input: not a JSONLD\". For more information, please refer to the API documentation: "+ docsUri).build();
	private final Response invalidJsonException =  Response.status(405).entity("415: \"Invalid input: not a JSON\". For more information, please refer to the API documentation: "+ docsUri).build();
	private final Response invalidParsedJsonException = Response.status(405).entity("405: \"Invalid input: The jsonld can not be parsed\". For more information, please refer to the API documentation: "+ docsUri).build();
	private final Response contextException = Response.status(405).entity("Context cannot be found either locally or online").build();
	
	private String getRemoteAddress(Request request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
		       ipAddress = request.getRemoteAddr();  
		   } 
		   return ipAddress;
	}	
	
	@GET  
	@Path("/generateMapping/")		
    @Produces({ "application/json" })    
    @Operation(summary = "Get mapping between equivalent models", description = "", tags={ "observations" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Operation"),
        
        @ApiResponse(responseCode = "405", description = "Invalid input: not JSON compliant"),
        
        @ApiResponse(responseCode = "415", description = "Invalid input: not a JSONLD"),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error") })
    
	public Response generateMap(
			@Parameter(in = ParameterIn.QUERY, description = "Input JSON for the mapping" , required = true)
				@QueryParam(Constants.SOURCE_JSON) String sourceJSON,
			@Parameter(in = ParameterIn.QUERY, description = "Destination JSON for the mapping" , required = false)
				@QueryParam(Constants.TARGET_JSON) String targetJSON,

			@Context UriInfo uriInfo, @Context Request request) throws Exception {
			
		log.debug("Mapping request Receibed from IP: "+getRemoteAddress(request) + ", Request ID: "+ request.getSession().getIdInternal());
		
		if(sourceJSON.isEmpty() || targetJSON.isEmpty() || sourceJSON.length()<4 || targetJSON.length()<4) {
			log.debug("JSON is empty");
			return Response.status(405).entity("JSON cannot be empty").build();
		}
		
		String[] response = Mapping.traceMapping(sourceJSON, targetJSON);
		
		return Response.status(200).entity(response[0]).build();

				
	}
	
	public static String[] generateMap(String sourceJSON, String targetJSON) {
		if(sourceJSON.isEmpty() || targetJSON.isEmpty() || sourceJSON.length()<4 || targetJSON.length()<4) {
			log.debug("JSON is empty");
			String[] response = {"JSON is empty", ""};
			return response;
		}
		
		String[] response = Mapping.traceMapping(sourceJSON, targetJSON);
		return response;
	}
	
	
}
