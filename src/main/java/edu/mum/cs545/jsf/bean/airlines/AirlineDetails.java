package edu.mum.cs545.jsf.bean.airlines;

import cs545.airline.model.Airline;
import cs545.airline.model.Flight;
import edu.mum.cs545.jsf.service.RestClient;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AirlineDetails implements Serializable {
    @Inject
    private RestClient restClient;

    private long airlineId;
    private Airline airline;
    private List<Flight> flights;

    public AirlineDetails() {
    }

    @PostConstruct
    public void init() {
        airline = new Airline();
    }

    public long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(long airlineId) {
        this.airlineId = airlineId;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void loadAirline() {
        airline = restClient.getAirlineById(airlineId);
        flights = restClient.getFlights(airline.getName(), null, null);
        for (Flight flight : flights) {
            airline.addFlight(flight);
        }
    }

    public void save() {
        try {
            restClient.updateAirline(airline);
        } catch (WebApplicationException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not update airline. Please try again later.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            e.printStackTrace();
        }
    }
}
