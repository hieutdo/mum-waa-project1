package cs545.airline.configuration;

import cs545.airline.dao.AirlineDao;
import cs545.airline.dao.AirplaneDao;
import cs545.airline.dao.AirportDao;
import cs545.airline.dao.FlightDao;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;
import cs545.airline.service.FlightService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class ClassBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AirlineDao.class).to(AirlineDao.class);
        bind(AirplaneDao.class).to(AirplaneDao.class);
        bind(AirportDao.class).to(AirportDao.class);
        bind(FlightDao.class).to(FlightDao.class);
        bind(AirlineService.class).to(AirlineService.class);
        bind(AirplaneService.class).to(AirplaneService.class);
        bind(AirportService.class).to(AirportService.class);
        bind(FlightService.class).to(FlightService.class);
    }

}
