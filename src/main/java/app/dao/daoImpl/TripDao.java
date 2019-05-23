package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.trip.Trip;
import app.utils.exeptions.Failure;
import app.utils.queries.TripQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class TripDao implements Dao<Trip> {

    private EntityManager entityManager;

    public TripDao(EntityManager entityManager) {
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
    public void save(Trip trip) {

        inTransaction(entityManager,
                () -> entityManager.persist(trip));

    }

    @Override
    public void update(Trip trip, String[] params) throws Failure {

        Town town = new Town();
        town.setTownName(params[0]);

        Address address = new Address();
        address.setAddressText(params[1]);
        address.setTown(town);


        inTransaction(entityManager ,
                () -> entityManager.persist(town));

        inTransaction(entityManager ,
                () -> entityManager.persist(address));

        trip.setToAddress(address);

        inTransaction(entityManager,
                () -> entityManager.merge(trip)
        );
    }


    @Override
    public void delete(Trip trip) {

    }

    @Override
    public List<Trip> getAll() {
        return this.entityManager
                .createQuery(TripQueries.FROM_TRIP, Trip.class)
                .getResultList();
    }
}
