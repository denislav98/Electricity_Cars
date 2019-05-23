package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.persondetails.Admin;
import app.utils.exeptions.Failure;
import app.utils.queries.AdminQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class AdminDao implements Dao<Admin> {
    private EntityManager entityManager;

    public AdminDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private void inTransaction(EntityManager manager, Runnable runnable) {
        try {

            manager.getTransaction().begin();
            runnable.run();
            manager.getTransaction().commit();

        } catch (RuntimeException e) {
            manager.getTransaction().rollback();
        }
    }

    @Override
    public List<Admin> getAll() {
        return this.entityManager
                .createQuery(AdminQueries.FROM_ADMIN, Admin.class)
                .getResultList();
    }

    @Override
    public void save(Admin admin) {
        inTransaction(entityManager,
                () -> entityManager.persist(admin));
    }

    @Override
    public void update(Admin admin, String[] params) throws Failure {
    }


    @Override
    public void delete(Admin admin) {
        inTransaction(entityManager,
                () -> entityManager.remove(admin));
    }
}
