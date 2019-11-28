package quarkus.oauth2.controller;

import java.security.Principal;
import java.util.HashMap;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class HelloController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * 
	 * Application.properties	
	 * quarkus.oauth2.client-id=SampleClientId
		quarkus.oauth2.client-secret=secret
		quarkus.oauth2.introspection-url= http://localhost:8081/auth/oauth/introspect
*/
	
	@ConfigProperty(name = "quarkus.oauth2.client-id")
	private String clientId;
	
	@ConfigProperty(name = "quarkus.oauth2.client-secret")
	private String clientSecret;
	
	@ConfigProperty(name = "quarkus.oauth2.introspection-url")
	private String introspectionURL;
	
	
	@GET
	@Path("/props")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> getProps(){
		HashMap<String, String> props = new HashMap<String, String>();
		props.put("quarkus.oauth2.client-id", clientId);
		props.put("quarkus.oauth2.client-secret", clientSecret);
		props.put("quarkus.oauth2.introspection-url", introspectionURL);
		return props;
	}
	
	@GET
	@Path("/secure")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloRolesAllowed(@Context SecurityContext ctx) {
		Principal caller = ctx.getUserPrincipal();
		String name = caller == null ? "anonymous" : caller.getName();
		return "example";
	}
	
	//gu install native-image
	//./gradlew clean buildNative --docker-build=true
	//java -Dquarkus.log.level=DEBUG -jar ./build/quarkus-oauth2-0.0.1-SNAPSHOT-runner.jar 
	//./build/quarkus-oauth2-0.0.1-SNAPSHOT-runner -Dquarkus.log.level=DEBUG

	
	//     java -Dquarkus.log.level=DEBUG -Dquarkus.oauth2.introspection-url=http://abc:8081/auth/oauth/introspect -jar ./build/quarkus-oauth2-0.0.1-SNAPSHOT-runner.jar 
	/*
	  sudo ./build/quarkus-oauth2-0.0.1-SNAPSHOT-runner -Dquarkus.oauth2.client-id=SampleClientId-native -Dquarkus.oauth2.client-secret=secret-native  -Dquarkus.oauth2.introspection-url=http://abc:8081/auth/oauth/introspect-native -Dquarkus.log.level=DEBUG
	 */

	
	//2019-11-27 11:56:20,874 DEBUG [org.wil.security] (executor-thread-1) Opening connection to token introspection endpoint [http://localhost:8081/auth/oauth/introspect]
	/*
	 	{
		    "quarkus.oauth2.introspection-url": "http://abc:8081/auth/oauth/introspect-native",
		    "quarkus.oauth2.client-secret": "secret-native",
		    "quarkus.oauth2.client-id": "SampleClientId-native"
		}
	 */
	
}
