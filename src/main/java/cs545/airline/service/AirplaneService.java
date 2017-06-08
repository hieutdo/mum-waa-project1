package cs545.airline.service;

import cs545.airline.dao.AirplaneDao;
import cs545.airline.model.Airplane;
import cs545.airline.model.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
@Transactional
public class AirplaneService {

    // These services should be evaluated to reconsider which methods should be public

    @Inject
    private AirplaneDao airplaneDao;


    public void create(Airplane airplane) {
        airplaneDao.create(airplane);
    }

    public void delete(Airplane airplane) {
        airplaneDao.delete(airplane);
    }

    public Airplane update(Airplane airplane) {
        return airplaneDao.update(airplane);
    }

    public Airplane find(Airplane airplane) {
        return airplaneDao.findOne(airplane.getId());
    }

    public Airplane findBySrlnr(String serialno) {
        return airplaneDao.findOneBySerialnr(serialno);
    }

    public List<Airplane> findByFlight(Flight flight) {
        return airplaneDao.findByFlight(flight.getId());
    }

    public List<Airplane> findByModel(String model) {
        return airplaneDao.findByModel(model);
    }

    public List<Airplane> findAll() {
        return airplaneDao.findAll();
    }

}
