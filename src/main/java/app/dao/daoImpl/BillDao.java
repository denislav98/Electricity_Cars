package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.bill.Bill;
import app.utils.exeptions.Failure;
import app.utils.queries.AdminQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class BillDao implements Dao<Bill> {

    private EntityManager entityManager;

    public BillDao(EntityManager entityManager) {
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
    public List<Bill> getAll() {
        return this.entityManager
                .createQuery(AdminQueries.FROM_ADMIN, Bill.class)
                .getResultList();
    }

    @Override
    public void save(Bill bill) {
        inTransaction(entityManager,
                () -> entityManager.persist(bill));
    }

    @Override
    public void update(Bill bill, String[] params) throws Failure {
    }


    @Override
    public void delete(Bill bill) {
        inTransaction(entityManager,
                () -> entityManager.remove(bill));
    }
}
