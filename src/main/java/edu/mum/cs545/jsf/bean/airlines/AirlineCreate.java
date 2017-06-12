package edu.mum.cs545.jsf.bean.airlines;

import cs545.airline.model.Airline;
import edu.mum.cs545.jsf.service.RestClient;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import java.io.Serializable;

@Named
@ViewScoped
public class AirlineCreate implements Serializable {
    @Inject
    private RestClient restClient;

    private Airline airline;

    public AirlineCreate() {
    }

    @PostConstruct
    public void init() {
        airline = new Airline();
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String save() {
        try {
            restClient.createOrUpdateAirline(airline);
            return "airlinesList?faces-redirect=true";
        } catch (WebApplicationException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not create new airline. Please try again later.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            e.printStackTrace();
            return null;
        }
    }
}
