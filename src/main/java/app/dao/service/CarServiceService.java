package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.ServiceDao;
import app.dao.view.ResultParser;
import app.entities.car.Car;
import app.entities.person.Employee;
import app.entities.service.Service;
import app.entities.service.ServiceEnum;
import app.entities.service.ServiceType;
import app.utils.constants.EmployeeMessage;
import app.utils.constants.ExceptionMessage;
import app.utils.constants.Message;
import app.utils.exeptions.CarServiceException;
import app.utils.exeptions.EnumException;
import app.utils.exeptions.Failure;
import app.utils.queries.CarQueries;
import app.utils.queries.EmployeeQueries;
import app.utils.queries.ServiceQueries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CarServiceService {

    private final Dao<Service> serviceDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;
    private Car car;
    private Employee employee;
    private ResultParser resultParser = new ResultParser();

    public CarServiceService(EntityManager entityManager) {
        this.entityManager = entityManager;
        serviceDao = new ServiceDao(entityManager);
    }

    public void getAllServicesMadeByEmployee() {

        System.out.println(EmployeeMessage.ENTER_EMPLOYEE_ID);
        int employeeId = Integer.parseInt(scanner.nextLine());

        List<Service> services = new ArrayList<>();

        this.entityManager.getTransaction().begin();

        try {
            services = entityManager.
                    createQuery(ServiceQueries.FROM_SERVICES_WHERE_EMPLOYEE + EmployeeMessage.ID_PARAM, Service.class)
                    .setParameter(Message.ID, employeeId)
                    .getResultList();
        } catch (NoResultException e) {
            System.out.println(
                    ExceptionMessage.NO_SUCH_EMPLOYEE + " or " +
                            ExceptionMessage.NO_SUCH_CAR);
        }

        this.entityManager.getTransaction().commit();

        resultParser.printAllServices(services);
    }

    public void addCarService() throws Failure {

        try {

            Service service = createService();

            serviceDao.save(service);

            resultParser.printService(service);

        } catch (IllegalArgumentException e) {
            System.out.println("Wrong service type entered");
        } catch (CarServiceException c) {
            System.out.println(ExceptionMessage.EMPTY_FIELD);
        }
    }

    private Service createService() throws CarServiceException {
        System.out.println(Message.SERVICE_TYPE);

        for (ServiceType service : ServiceEnum.serviceTypesEnumSet) {
            System.out.println(service);
        }

        String serviceType = scanner.nextLine();

        if (!ServiceEnum.serviceTypesEnumSet.contains(ServiceType.valueOf(serviceType))) {
            throw new EnumException("Wrong service type entered !");
        }

        Date dateOfService = new Date();

        System.out.println(Message.REGISTRATION_NUMBER);
        String registrationNumber = scanner.nextLine();

        System.out.println(Message.EMPLOYEE_FIRST_NAME);
        String firstName = scanner.nextLine();

        System.out.println(Message.EMPLOYEE_LAST_NAME);
        String lastName = scanner.nextLine();

        if (firstName.isEmpty() || lastName.isEmpty() || registrationNumber.isEmpty()) {
            throw new CarServiceException(" " + ExceptionMessage.EMPTY_FIELD);
        }

        try {
            car = this.entityManager
                    .createQuery(CarQueries.CAR_WHERE_REGISTRATION_NUMBER + Message.REGISTRATION_NUMBER_PARAM, Car.class)
                    .setParameter(Message.REG_NUM, registrationNumber)
                    .getSingleResult();

            employee = entityManager
                    .createQuery(EmployeeQueries.EMPLOYEE_WHERE_FIRST_NAME + Message.FIRST_NAME_PARAM
                            + EmployeeQueries.AND_LAST_NAME + Message.LAST_NAME_PARAM, Employee.class)
                    .setParameter(Message.FIRST_NAME_P, firstName)
                    .setParameter(Message.LAST_NAME_P, lastName)
                    .getSingleResult();

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_EMPLOYEE + " or " + ExceptionMessage.NO_SUCH_CAR);
            System.out.println(ExceptionMessage.TRY_AGAIN);
            System.out.println();
            createService();
        }

        return new Service(serviceType, dateOfService, car, employee);
    }
}
