package cs545.airline.dao;

import cs545.airline.model.Airline;
import edu.mum.gf.workaround.JpaUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Named
@ApplicationScoped
public class AirlineDao {

    //	@PersistenceContext(unitName = "cs545")
//	private static EntityManager entityManager;
//  Couldn't figure out another way to inject the persistence context
    private EntityManager entityManager = JpaUtil.getEntityManager();


    public void create(Airline airline) {
        entityManager.persist(airline);
    }

    public Airline update(Airline airline) {
        return entityManager.merge(airline);
    }

    public void delete(Airline airline) {
        entityManager.remove(airline);
    }

    public Airline findOne(long id) {
        return entityManager.find(Airline.class, id);
    }

    public Airline findOneByName(String name) {
        Query query = entityManager.createQuery("select a from Airline a where a.name=:name", Airline.class);
        query.setParameter("name", name);

        return (Airline) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Airline> findByFlight(long flightId) {
        Query query = entityManager
                .createQuery("select distinct a from Airline a join a.flights f where f.id=:flightId", Airline.class);
        query.setParameter("flightId", flightId);

        return query.getResultList();
    }

    public List<Airline> findAll() {
        return entityManager.createQuery("select a from Airline a", Airline.class).getResultList();
    }

}
