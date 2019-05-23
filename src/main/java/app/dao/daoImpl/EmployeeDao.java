package app.dao.daoImpl;

import app.dao.contract.Dao;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.person.Employee;
import app.utils.exeptions.Failure;
import app.utils.queries.EmployeeQueries;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeDao implements Dao<Employee> {

    private EntityManager entityManager;

    public EmployeeDao(EntityManager entityManager) {
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
    public List<Employee> getAll() {

        return this.entityManager
                .createQuery(EmployeeQueries.FROM_EMPLOYEE, Employee.class)
                .getResultList();
    }

    @Override
    public void save(Employee employee) {
        inTransaction(entityManager,
                () -> entityManager.persist(employee));
    }

    @Override
    public void update(Employee employee, String[] params) throws Failure {

        employee.setFirstName(params[0]);

        employee.setLastName(params[1]);

        employee.setPhone(params[2]);

        employee.setEmail(params[3]);

        String newAddress = params[4];

        String newTownName = params[5];

        Town town = new Town();
        town.setTownName(newTownName);

        Address address = new Address();
        address.setAddressText(newAddress);
        address.setTown(town);


        inTransaction(entityManager ,
                () -> entityManager.persist(town));

        inTransaction(entityManager ,
                () -> entityManager.persist(address));

        employee.setAddress(address);

        inTransaction(entityManager,
                () -> entityManager.merge(employee)
        );
    }

    @Override
    public void delete(Employee employee) {
        inTransaction(entityManager,
                () -> entityManager.remove(employee));
    }
}

