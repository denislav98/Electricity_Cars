package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.payment.CreditCard;
import app.utils.exeptions.Failure;
import app.utils.queries.TripQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class CreditCardDao implements Dao<CreditCard> {

    private EntityManager entityManager;

    public CreditCardDao(EntityManager entityManager) {
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
    public void save(CreditCard creditCard) {

        inTransaction(entityManager,
                () -> entityManager.persist(creditCard));

    }

    @Override
    public void update(CreditCard creditCard, String[] params) throws Failure {

    }

    @Override
    public void delete(CreditCard creditCard) {

    }

    @Override
    public List<CreditCard> getAll() {
        return this.entityManager
                .createQuery(TripQueries.FROM_TRIP, CreditCard.class)
                .getResultList();
    }

}
