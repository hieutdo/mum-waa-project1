package cs545.airline.configuration;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rs")
public class RestConfig extends ResourceConfig {

    public RestConfig() {
        register(new ClassBinder());
        packages(true, "edu.mum.cs545.ws, com.fasterxml.jackson.jaxrs.json");

    }

}
