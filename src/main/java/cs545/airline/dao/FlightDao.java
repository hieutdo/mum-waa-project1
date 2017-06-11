package cs545.airline.dao;

import cs545.airline.model.Flight;
import edu.mum.gf.workaround.JpaUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ApplicationScoped
public class FlightDao {

    //	@PersistenceContext(unitName = "cs545")
//	private static EntityManager entityManager;
//  Couldn't figure out another way to inject the persistence context
    private EntityManager entityManager = JpaUtil.getEntityManager();

    public void create(Flight flight) {
        entityManager.getTransaction().begin();
        entityManager.persist(flight);
        entityManager.getTransaction().commit();
    }

    public Flight update(Flight flight) {
        return entityManager.merge(flight);
    }

    public void delete(Flight flight) {
        Flight toremove = entityManager.find(Flight.class, flight.getId());
        if (toremove != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(toremove);
            entityManager.getTransaction().commit();
        }
    }

    public Flight findOne(long id) {
        return entityManager.find(Flight.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByFlightnr(String flightnr) {
        Query query = entityManager.createQuery("select f from Flight f where f.flightnr=:flightnr", Flight.class);
        query.setParameter("flightnr", flightnr);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByOrigin(long airportId) {
        Query query = entityManager.createQuery("select f from Flight f join f.origin o where o.id=:airportId",
                Flight.class);
        query.setParameter("airportId", airportId);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByDestination(long airportId) {
        Query query = entityManager.createQuery("select f from Flight f join f.destination d where d.id=:airportId",
                Flight.class);
        query.setParameter("airportId", airportId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByAirplane(long airplaneId) {
        Query query = entityManager.createQuery("select f from Flight f join f.airplane a where a.id=:airplaneId",
                Flight.class);
        query.setParameter("airplaneId", airplaneId);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByAirline(long airlineId) {
        Query query = entityManager.createQuery("select f from Flight f join f.airline a where a.id=:airlineId",
                Flight.class);
        query.setParameter("airlineId", airlineId);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByDeparture(Date date, Date time) {
        Query query = entityManager.createQuery(
                "select distinct f from Flight f where f.departureDate=:departureDate and f.departureTime=:departureTime",
                Flight.class);
        query.setParameter("departureDate", date, TemporalType.DATE);
        query.setParameter("departureTime", time, TemporalType.TIME);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByDepartureBetween(Date dateFrom, Date dateTo, Date timeFrom, Date timeTo) {
        Query query = entityManager.createQuery(
                "select distinct f from Flight f where f.arrivalDate between :DateFrom and :DateTo and f.arrivalTime between :TimeFrom and :TimeTo",
                Flight.class);
        query.setParameter("DateFrom", dateFrom, TemporalType.DATE);
        query.setParameter("TimeFrom", dateFrom, TemporalType.TIME);
        query.setParameter("DateTo", dateTo, TemporalType.DATE);
        query.setParameter("TimeTo", dateTo, TemporalType.TIME);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByArrival(Date date, Date time) {
        Query query = entityManager.createQuery(
                "select distinct f from Flight f where f.arrivalDate=:arrivalDate and f.arrivalTime=:arrivalTime",
                Flight.class);
        query.setParameter("arrivalDate", date, TemporalType.DATE);
        query.setParameter("arrivalTime", time, TemporalType.TIME);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> findByArrivalBetween(Date dateFrom, Date dateTo, Date timeFrom, Date timeTo) {
        Query query = entityManager.createQuery(
                "select distinct f from Flight f where f.arrivalDate between :arrivalDateFrom and :arrivalDateTo and f.arrivalTime between :arrivalTimeFrom and :arrivalTimeTo",
                Flight.class);
        query.setParameter("arrivalDateFrom", dateFrom, TemporalType.DATE);
        query.setParameter("arrivalTimeFrom", dateFrom, TemporalType.TIME);
        query.setParameter("arrivalDateTo", dateTo, TemporalType.DATE);
        query.setParameter("arrivalTimeTo", dateTo, TemporalType.TIME);

        return query.getResultList();
    }

    public List<Flight> findAll() {
        return entityManager.createQuery("select f from Flight f", Flight.class).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flight> search(String airlineName, String originAirportName, String destinationAirportName) {
        boolean hasAirlineName = airlineName != null && !airlineName.isEmpty();
        boolean hasOriginAirportName = originAirportName != null && !originAirportName.isEmpty();
        boolean hasDestinationAirportName = destinationAirportName != null && !destinationAirportName.isEmpty();
        List<String> joinClauses = new ArrayList<>();
        List<String> whereClauses = new ArrayList<>();
        String sql = "select f from Flight f ";

        if (hasAirlineName) {
            joinClauses.add(" join f.airline a ");
            whereClauses.add(" a.name=:airlineName ");
        }

        if (hasOriginAirportName) {
            joinClauses.add(" join f.origin o ");
            whereClauses.add(" o.name=:originAirportName ");
        }

        if (hasDestinationAirportName) {
            joinClauses.add(" join f.destination d ");
            whereClauses.add(" d.name=:destinationAirportName ");
        }

        if (joinClauses.size() > 0) {
            sql += String.join(" ", joinClauses);
        }

        if (whereClauses.size() > 0) {
            sql += " where " + String.join(" and ", whereClauses);
        }

        Query query = entityManager.createQuery(sql, Flight.class);

        if (hasAirlineName) {
            query.setParameter("airlineName", airlineName);
        }

        if (hasOriginAirportName) {
            query.setParameter("originAirportName", originAirportName);
        }

        if (hasDestinationAirportName) {
            query.setParameter("destinationAirportName", destinationAirportName);
        }

        return query.getResultList();
    }
}
