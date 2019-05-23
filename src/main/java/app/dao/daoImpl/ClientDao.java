package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.person.Client;
import app.utils.exeptions.Failure;
import app.utils.queries.ClientQuieries;

import javax.persistence.EntityManager;
import java.util.List;

public class ClientDao implements Dao<Client> {

    private EntityManager entityManager;

    public ClientDao(EntityManager entityManager) {
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
    public List<Client> getAll() {
        return this.entityManager
                .createQuery(ClientQuieries.FROM_CLIENT, Client.class)
                .getResultList();
    }

    @Override
    public void save(Client client) {
        inTransaction(entityManager,
                () -> entityManager.persist(client));
    }

    @Override
    public void update(Client client, String[] params) throws Failure {
        client.setUsername(params[0]);

        client.setPassword(params[1]);

        client.setFirstName(params[2]);

        client.setLastName(params[3]);

        client.setPhone(params[4]);

        client.setEmail(params[5]);

        String newTownName = params[6];

        String newAddress = params[7];

        client.setDrivingLicenseNumber(params[8]);

        Town town = new Town();
        town.setTownName(newTownName);

        Address address = new Address();
        address.setAddressText(newAddress);
        address.setTown(town);


        inTransaction(entityManager ,
                () -> entityManager.persist(town));

        inTransaction(entityManager ,
                () -> entityManager.persist(address));

        client.setAddress(address);

        inTransaction(entityManager,
                () -> entityManager.merge(client)
        );
    }

    @Override
    public void delete(Client client) {
        inTransaction(entityManager,
                () -> entityManager.remove(client));
    }
}
