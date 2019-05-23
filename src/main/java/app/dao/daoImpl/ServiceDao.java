package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.service.Service;
import app.utils.exeptions.Failure;
import app.utils.queries.ServiceQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class ServiceDao implements Dao<Service> {

    private EntityManager entityManager;

    public ServiceDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static void inTransaction(EntityManager manager, Runnable runnable) {
        try {

            manager.getTransaction().begin();
            runnable.run();
            manager.getTransaction().commit();

        } catch (RuntimeException e) {
            manager.getTransaction().rollback();
        }
    }

    @Override
    public List<Service> getAll() {
        return this.entityManager
                .createQuery(ServiceQueries.FROM_SERVICES_WHERE_EMPLOYEE, Service.class)
                .getResultList();
    }

    @Override
    public void save(Service service) {
        inTransaction(entityManager,
                () -> entityManager.persist(service));
    }

    // TODO
    @Override
    public void update(Service service, String[] params) throws Failure {

    }


    @Override
    public void delete(Service service) {
        inTransaction(entityManager,
                () -> entityManager.remove(service));
    }
}
