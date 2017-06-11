package edu.mum.cs545.jsf.service;

import cs545.airline.model.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@ApplicationScoped
public class FlightRestClient {
    private static final String API_BASE_URL = "http://localhost:8080/airlinesWebApp-0.0.1-SNAPSHOT/rs";
    private final Client restClient;

    public FlightRestClient() {
        restClient = ClientBuilder.newClient();
    }

    public List<Flight> getFlights(String airlineName, String originAirportName, String destinationAirportName) {
        WebTarget webTarget = restClient.target(API_BASE_URL)
                .path("/flights")
                .queryParam("airlineName", airlineName)
                .queryParam("originAirportName", originAirportName)
                .queryParam("destinationAirportName", destinationAirportName);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        return response.readEntity(new GenericType<List<Flight>>() {
        });
    }

}
