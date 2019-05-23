package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.EmployeeDao;
import app.dao.view.ResultParser;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.person.Employee;
import app.utils.constants.EmployeeMessage;
import app.utils.constants.ExceptionMessage;
import app.utils.constants.Message;
import app.utils.exeptions.EmployeeException;
import app.utils.exeptions.Failure;
import app.utils.queries.EmployeeQueries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    private final Dao<Employee> employeeDao;
    private Scanner scanner = new Scanner(System.in);
    private ResultParser resultParser = new ResultParser();
    private EntityManager entityManager;
    private Employee employee;

    public EmployeeService(EntityManager entityManager) {
        this.entityManager = entityManager;
        employeeDao = new EmployeeDao(entityManager);
    }

    private static Employee createEmployee() {

        Scanner scanner = new Scanner(System.in);
        System.out.println(EmployeeMessage.FIRST_NAME);
        String firstName = scanner.nextLine();

        System.out.println(EmployeeMessage.LAST_NAME);
        String lastName = scanner.nextLine();

        System.out.println(EmployeeMessage.PHONE);
        String phone = scanner.nextLine();

        System.out.println(EmployeeMessage.EMAIL);
        String email = scanner.nextLine();

        System.out.println(Message.TOWN);
        String townName = scanner.nextLine();

        System.out.println(EmployeeMessage.EMPLOYEE_ADDRESS);
        String addressText = scanner.nextLine();

        if (firstName.isEmpty() || lastName.isEmpty() || addressText.isEmpty()
                || phone.isEmpty() || townName.isEmpty()) {
            throw new EmployeeException(ExceptionMessage.EMPTY_FIELD);

        }

        Town town = new Town();
        town.setTownName(townName);

        Address address = new Address();
        address.setAddressText(addressText);
        address.setTown(town);

        return new Employee(firstName, lastName, phone, email, town, address);
    }

    private static String[] update() throws Failure {

        Scanner scanner = new Scanner(System.in);

        System.out.println(EmployeeMessage.FIRST_NAME);
        String firstName = scanner.nextLine();

        System.out.println(EmployeeMessage.LAST_NAME);
        String lastName = scanner.nextLine();

        System.out.println(EmployeeMessage.PHONE);
        String phone = scanner.nextLine();

        System.out.println(EmployeeMessage.EMAIL);
        String email = scanner.nextLine();

        System.out.println(Message.TOWN);
        String townName = scanner.nextLine();

        System.out.println(EmployeeMessage.EMPLOYEE_ADDRESS);
        String addressText = scanner.nextLine();

        if (firstName.isEmpty() || lastName.isEmpty() || addressText.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || townName.isEmpty()) {
            throw new Failure(ExceptionMessage.EMPTY_FIELD);
        }

        Address address = new Address();
        address.setAddressText(addressText);

        Town town = new Town();
        town.setTownName(townName);

        address.setTown(town);

        return new String[]{firstName, lastName, phone, email, String.valueOf(address), String.valueOf(town)};
    }

    public void saveEmployee() {

        try {
            employee = createEmployee();

            employeeDao.save(employee);

            resultParser.printEmployee(employee);

        } catch (EmployeeException e) {
            System.err.println(ExceptionMessage.ERROR);
            System.err.println(ExceptionMessage.EMPTY_FIELD);
            System.err.println(Message.REENTER);
            System.out.println();
        }
    }

    public void updateEmployee() throws Failure {

        System.out.println(EmployeeMessage.EMPLOYEE_EMAIL);
        String email = scanner.nextLine();

        try {

            employee = entityManager
                    .createQuery(EmployeeQueries.EMPLOYEE_WHERE_EMAIL_LIKE + EmployeeMessage.MAIL_PARAM, Employee.class)
                    .setParameter(EmployeeMessage.MAIL, email)
                    .getSingleResult();

            String[] employeeParams = update();
            employeeDao.update(employee, employeeParams);


        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_EMPLOYEE);
        }


    }

    public void deleteEmployee() {

        System.out.println(EmployeeMessage.EMPLOYEE_EMAIL);
        String mail = scanner.nextLine();

        try {

            employee = entityManager
                    .createQuery( EmployeeQueries.EMPLOYEE_WHERE_EMAIL_LIKE+ EmployeeMessage.MAIL_PARAM , Employee.class)
                    .setParameter(EmployeeMessage.MAIL, mail)
                    .getSingleResult();

            employeeDao.delete(employee);

            System.out.println(EmployeeMessage.EMPLOYEE + employee.getFirstName() + Message.DELETED);

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_EMPLOYEE);
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

}
