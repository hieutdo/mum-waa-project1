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

    public void create(Airline airline) {
        airlineDao.create(airline);
    }

    public void delete(Airline airline) {
        airlineDao.delete(airline);
    }

    public Airline update(Airline airline) {
        return airlineDao.update(airline);
    }

    public Airline findById(long id) {
        return airlineDao.findOne(id);
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
