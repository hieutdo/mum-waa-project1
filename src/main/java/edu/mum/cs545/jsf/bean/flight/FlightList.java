package edu.mum.cs545.jsf.bean.flight;

import cs545.airline.model.Flight;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class FlightList implements Serializable {
    private final Client jaxrsClient;
    private final String baseUrl = "http://localhost:8080/airlinesWebApp-0.0.1-SNAPSHOT/rs";
    private List<Flight> flights;
    private List<Flight> filterFlights;

    public FlightList() {
        jaxrsClient = ClientBuilder.newClient();
    }

    @PostConstruct
    public void init() {
        WebTarget webTarget = jaxrsClient.target(baseUrl + "/flights");

        // build the request (e.g. a GET request)
        Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON).buildGet();

        // invoke the request
        Response response = invocation.invoke();

        this.flights = response.readEntity(new GenericType<List<Flight>>() {
        });
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<String> getAirlines() {
        return this.flights.stream()
                .map(flight -> flight.getAirline().getName())
                .distinct()
                .collect(Collectors.toList());
    }
}
