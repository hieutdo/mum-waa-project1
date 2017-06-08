package cs545.airline.service;

import cs545.airline.dao.AirlineDao;
import cs545.airline.model.Airline;
import cs545.airline.model.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
@Transactional
public class AirlineService {

    // These services should be evaluated to reconsider which methods should be
    // public

    @Inject
    private AirlineDao airlineDao;

    public void create(Airline airport) {
        airlineDao.create(airport);
    }

    public void delete(Airline airport) {
        airlineDao.delete(airport);
    }

    public Airline update(Airline airport) {
        return airlineDao.update(airport);
    }

    public Airline find(Airline airport) {
        return airlineDao.findOne(airport.getId());
    }

    public Airline findByName(String name) {
        return airlineDao.findOneByName(name);
    }

    public List<Airline> findByFlight(Flight flight) {
        return airlineDao.findByFlight(flight.getId());
    }

    public List<Airline> findAll() {
        return airlineDao.findAll();
    }
}
