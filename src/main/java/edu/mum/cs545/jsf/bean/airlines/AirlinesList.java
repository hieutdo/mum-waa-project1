package edu.mum.cs545.jsf.bean.airlines;

import cs545.airline.model.Airline;
import edu.mum.cs545.jsf.service.RestClient;

import javax.annotation.PostConstruct;
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

    public AirlinesList() {

    }

    @PostConstruct
    public void init() {
        airlines = restClient.getAirlines();
    }

    public List<Airline> getAirlines() {
        return airlines;
    }
}
