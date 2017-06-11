package edu.mum.cs545.jsf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs545.airline.model.Airline;
import cs545.airline.model.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@ApplicationScoped
public class RestClient {
    private static final String API_BASE_URL = "http://localhost:8080/airlinesWebApp-0.0.1-SNAPSHOT/rs";
    private final Client restClient;

    public RestClient() {
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

    public List<Airline> getAirlines() {
        WebTarget webTarget = restClient.target(API_BASE_URL).path("/airlines");
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        List<Airline> airlines = response.readEntity(new GenericType<List<Airline>>() {
        });
        for (Airline airline : airlines) {
            List<Flight> flights = getFlights(airline.getName(), null, null);
            for (Flight flight : flights) {
                airline.addFlight(flight);
            }
        }
        return airlines;
    }

    public boolean deleteAirline(Airline airline) {
        WebTarget webTarget = restClient.target(API_BASE_URL).path("/airlines/" + airline.getId());
        Response response = webTarget.request(MediaType.APPLICATION_JSON).delete();
        return response.getStatus() == 200;
    }

    public Airline getAirlineById(long id) {
        WebTarget webTarget = restClient.target(API_BASE_URL).path("/airlines/" + id);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() != 200) {
            throw new NotFoundException("Could not find airline with ID " + id);
        }
        return response.readEntity(Airline.class);
    }

    public void updateAirline(Airline airline) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(airline);
            WebTarget webTarget = restClient.target(API_BASE_URL).path("/airlines");
            Response response = webTarget
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(json));
            if (response.getStatus() != 200) {
                throw new WebApplicationException("Could not update airline with ID " + airline.getId());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new WebApplicationException("Could not convert object to json", e);
        }
    }
}
