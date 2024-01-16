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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.jaxrs.config.BeanConfig;

public class WebApp 
{
    public static final String BASE_URI = "https://0.0.0.0:9105/";
    public static AtomicInteger sequenceNumber = new AtomicInteger(0);
    private static final Logger log = Logger.getLogger(WebApp.class);
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */	
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in afc.NRDBManager.DAM_RestServer package
//        final ResourceConfig rc = new ResourceConfig().packages("afarcloud.nrdb.services.rest");
        String resources = "sis.rest";
        
       	BeanConfig beanConfig = new BeanConfig();

    	beanConfig.setVersion("1.0.1");

    	beanConfig.setSchemes(new String[] { "http" });

    	beanConfig.setBasePath("");

    	beanConfig.setResourcePackage(resources);

    	beanConfig.setScan(true);

    	final ResourceConfig resourceConfig = new ResourceConfig();

    	resourceConfig.packages(resources);

    	resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);

    	resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

    	resourceConfig.register(JacksonFeature.class);

    	resourceConfig.register(JacksonJsonProvider.class);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
    	SSLContextConfigurator sslConfig = new SSLContextConfigurator();
    	sslConfig.setKeyStoreFile("src/SSL/margarita2024.jks");
    	sslConfig.setKeyPass("m4rg4rit4.etsist.upm.es");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig, true, new SSLEngineConfigurator(sslConfig).setClientMode(false).setNeedClientAuth(false),false);
    }

    
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
    	 final HttpServer server = startServer();
         server.start();
         ClassLoader loader = WebApp.class.getClassLoader();

         CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "swagger-ui/");
         CLStaticHttpHandler schemasHandler = new CLStaticHttpHandler(loader, "schemas/");  
         
         
         docsHandler.setFileCacheEnabled(false);
         schemasHandler.setFileCacheEnabled(false);
         
         ServerConfiguration cfg = server.getServerConfiguration();
         
         cfg.addHttpHandler(docsHandler, "/sis/");
         cfg.addHttpHandler(schemasHandler, "/schemas/");
         
         
         String log4jConfPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"properties"+File.separator+"log4j.properties";
         
         PropertyConfigurator.configure(log4jConfPath);
         
//         try {
// 			Schema.loadSchemas(BASE_URI+"schemas/");
// 			} catch (ProcessingException e) {
//				log.error("Schemas cannot be loaded: "+e);
//			}  
         
         log.debug(String.format("Jersey app started with WADL available at "
                 + "%sapplication.wadl\nbase dir:: %s\n Hit enter to stop it...", BASE_URI, System.getProperty("user.dir")));
         System.in.read();
         server.shutdownNow();
    }
}
