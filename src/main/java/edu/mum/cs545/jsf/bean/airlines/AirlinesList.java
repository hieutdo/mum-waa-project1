package edu.mum.cs545.jsf.bean.airlines;

import cs545.airline.model.Airline;
import edu.mum.cs545.jsf.service.RestClient;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AirlinesList implements Serializable {
    @Inject
    private RestClient restClient;

    private List<Airline> airlines;
    private List<Airline> selectedAirlines;

    public AirlinesList() {

    }

    @PostConstruct
    public void init() {
        reloadData();
    }

    private void reloadData() {
        airlines = restClient.getAirlines();
    }

    public List<Airline> getAirlines() {
        return airlines;
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    public List<Airline> getSelectedAirlines() {
        return selectedAirlines;
    }

    public void setSelectedAirlines(List<Airline> selectedAirlines) {
        this.selectedAirlines = selectedAirlines;
    }

    public void deleteAirline(Airline airline) {
        FacesMessage message = new FacesMessage();
        if (restClient.deleteAirline(airline)) {
            message.setSummary("Success");
            message.setDetail("Airline " + airline.getName() + " has been deleted successfully.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSummary("Error");
            message.setDetail("Failed to delete the airline " + airline.getName() + ". Please try again later.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        reloadData();
    }
}
