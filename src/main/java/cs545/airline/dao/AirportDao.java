package cs545.airline.dao;

import cs545.airline.model.Airport;
import edu.mum.gf.workaround.JpaUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Named
@ApplicationScoped
public class AirportDao {

    //	@PersistenceContext(unitName = "cs545")
//	private static EntityManager entityManager;
//  Couldn't figure out another way to inject the persistence context
    private EntityManager entityManager = JpaUtil.getEntityManager();

    public void create(Airport airport) {
        entityManager.persist(airport);
    }

    public Airport update(Airport airport) {

        return entityManager.merge(airport);
    }

    public void delete(Airport airport) {
        entityManager.remove(airport);
    }

    public Airport findOne(long id) {
        return entityManager.find(Airport.class, id);
    }

    public Airport findOneByCode(String airportcode) {
        Query query = entityManager.createQuery("select a from Airport a where a.airportcode=:airportcode",
                Airport.class);
        query.setParameter("airportcode", airportcode);

        return (Airport) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Airport> findByCity(String city) {
        Query query = entityManager.createQuery("select a from Airport a where a.city=:city", Airport.class);
        query.setParameter("city", city);

        return query.getResultList();

    }

    @SuppressWarnings("unchecked")
    public List<Airport> findByName(String name) {
        Query query = entityManager.createQuery("select a from Airport a where a.name like :name", Airport.class);
        query.setParameter("name", name);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Airport> findByCountry(String country) {
        Query query = entityManager.createQuery("select a from Airport a where a.country=:country", Airport.class);
        query.setParameter("country", country);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Airport> findByArrival(long flightId) {
        Query query = entityManager.createQuery(
                "select distinct a from Airport a join a.arrivals ar where ar.id=:flightId", Airport.class);
        query.setParameter("flightId", flightId);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Airport> findByDeparture(long flightId) {
        Query query = entityManager.createQuery(
                "select distinct a from Airport a join a.departures d where d.id=:flightId", Airport.class);
        query.setParameter("flightId", flightId);

        return query.getResultList();
    }

    public List<Airport> findAll() {
        return entityManager.createQuery("select a from Airport a", Airport.class).getResultList();
    }

}
