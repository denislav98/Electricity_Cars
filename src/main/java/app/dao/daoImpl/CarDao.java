package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.car.Car;
import app.utils.exeptions.Failure;
import app.utils.queries.CarQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class CarDao implements Dao<Car> {

    private EntityManager entityManager;

    public CarDao(EntityManager entityManager) {
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
    public List<Car> getAll() {
        return this.entityManager
                .createQuery(CarQueries.FROM_CAR, Car.class)
                .getResultList();
    }

    @Override
    public void save(Car car) {
        inTransaction(entityManager,
                () -> entityManager.persist(car));
    }

    @Override
    public void update(Car car, String[] params) throws Failure {

        String registrationNumber = params[0];
        String carModel = params[1];
        boolean isAvailable = Boolean.parseBoolean(params[2]);
        String newAddress = params[3];
        String townName = params[4];

        car.setRegistrationNumber(registrationNumber);
        car.setCarModel(carModel);
        car.setAvailable(isAvailable);

        Town town = new Town();
        town.setTownName(townName);

        Address address = new Address();
        address.setAddressText(newAddress);
        address.setTown(town);


        inTransaction(entityManager,
                () -> entityManager.persist(town));

        inTransaction(entityManager,
                () -> entityManager.persist(address));

        car.setAddress(address);

        inTransaction(entityManager,
                () -> entityManager.merge(car)
        );

    }


    @Override
    public void delete(Car car) {
        inTransaction(entityManager,
                () -> entityManager.remove(car));
    }
}
