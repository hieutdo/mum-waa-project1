package cs545.airline.service;

import cs545.airline.dao.FlightDao;
import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Named
@ApplicationScoped
@Transactional
public class FlightService {

    @Inject
    private FlightDao flightDao;

    // These services should be evaluated to reconsider which methods should be public

    public void create(Flight flight) {
        flightDao.create(flight);
    }

    public void delete(Flight flight) {
        flightDao.delete(flight);
    }

    public Flight update(Flight flight) {
        return flightDao.update(flight);
    }

    public Flight find(Flight flight) {
        return flightDao.findOne(flight.getId());
    }

    public List<Flight> findByNumber(String flightnr) {
        return flightDao.findByFlightnr(flightnr);
    }

    public List<Flight> findByAirline(Airline airline) {
        return flightDao.findByAirline(airline.getId());
    }

    public List<Flight> findByOrigin(Airport airport) {
        return flightDao.findByOrigin(airport.getId());
    }

    public List<Flight> findByDestination(Airport airport) {
        return flightDao.findByDestination(airport.getId());
    }

    public List<Flight> findByArrival(Airplane airplane) {
        return flightDao.findByAirplane(airplane.getId());
    }

    public List<Flight> findByArrival(Date datetime) {
        return flightDao.findByArrival(datetime, datetime);
    }

    public List<Flight> findByArrivalBetween(Date datetimeFrom, Date datetimeTo) {
        return flightDao.findByArrivalBetween(datetimeFrom, datetimeFrom, datetimeTo, datetimeTo);
    }

    public List<Flight> findByDeparture(Date datetime) {
        return flightDao.findByDeparture(datetime, datetime);
    }

    public List<Flight> findByDepartureBetween(Date datetimeFrom, Date datetimeTo) {
        return flightDao.findByDepartureBetween(datetimeFrom, datetimeFrom, datetimeTo, datetimeTo);
    }

    public List<Flight> findAll() {
        return flightDao.findAll();
    }

}
