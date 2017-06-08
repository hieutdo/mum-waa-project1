package edu.mum.gf.workaround;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

    // This resource has to be released at the end of the application otherwise
    // it will cause issues with classloader

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("cs545");
    private static EntityManager entityManager = null;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    public static void releaseResources() {
        if (emf != null) {
            emf.close();
        }
    }
}
