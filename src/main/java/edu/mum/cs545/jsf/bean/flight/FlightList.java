package edu.mum.cs545.jsf.bean.flight;

import cs545.airline.model.Flight;
import edu.mum.cs545.jsf.service.RestClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FlightList implements Serializable {
    @Inject
    private RestClient restClient;

    private List<Flight> flights;
    private String airlineFilter;
    private String originAirportFilter;
    private String destinationAirportFilter;

    public FlightList() {
    }

    @PostConstruct
    public void init() {
        this.flights = restClient.getFlights(this.airlineFilter, this.originAirportFilter, this.airlineFilter);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public String getAirlineFilter() {
        return airlineFilter;
    }

    public void setAirlineFilter(String airlineFilter) {
        this.airlineFilter = airlineFilter;
    }

    public String getOriginAirportFilter() {
        return originAirportFilter;
    }

    public void setOriginAirportFilter(String originAirportFilter) {
        this.originAirportFilter = originAirportFilter;
    }

    public String getDestinationAirportFilter() {
        return destinationAirportFilter;
    }

    public void setDestinationAirportFilter(String destinationAirportFilter) {
        this.destinationAirportFilter = destinationAirportFilter;
    }

    public void filterListener() {
        this.flights = restClient.getFlights(this.airlineFilter, this.originAirportFilter, this.destinationAirportFilter);
    }

    public List<String> getAirlines() {
        return this.flights.stream()
                .map(flight -> flight.getAirline().getName())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getOriginAirports() {
        return this.flights.stream()
                .map(flight -> flight.getOrigin().getName())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getDestinationAirports() {
        return this.flights.stream()
                .map(flight -> flight.getDestination().getName())
                .distinct()
                .collect(Collectors.toList());
    }
}
