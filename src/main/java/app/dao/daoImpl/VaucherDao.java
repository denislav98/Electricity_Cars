package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.payment.Vaucher;
import app.utils.exeptions.Failure;
import app.utils.queries.VaucherQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class VaucherDao implements Dao<Vaucher> {

    private EntityManager entityManager;

    public VaucherDao(EntityManager entityManager) {
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
    public List<Vaucher> getAll() {
        return this.entityManager
                .createQuery(VaucherQueries.FROM_VAUCHER, Vaucher.class)
                .getResultList();
    }

    @Override
    public void save(Vaucher vaucher) {
        inTransaction(entityManager,
                () -> entityManager.persist(vaucher));
    }

    @Override
    public void update(Vaucher vaucher, String[] params) throws Failure {

        String vaucherNumber = params[0];
        double vaucherAmount = Double.valueOf(params[1]);

        vaucher.setVaucherNumber(vaucherNumber);
        vaucher.setAmount(vaucherAmount);

        inTransaction(entityManager,
                () -> entityManager.merge(vaucher)
        );
    }



    @Override
    public void delete(Vaucher vaucher) {
        inTransaction(entityManager,
                () -> entityManager.remove(vaucher));
    }
}
