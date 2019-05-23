package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.CarDao;
import app.dao.view.ResultParser;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.car.Car;
import app.utils.constants.CarMessages;
import app.utils.constants.ExceptionMessage;
import app.utils.constants.Message;
import app.utils.exeptions.CarException;
import app.utils.exeptions.Failure;
import app.utils.queries.CarQueries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CarService {

    private final Dao<Car> carDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;
    private Car car;
    private List<Car> cars;

    public CarService(EntityManager entityManager) {
        this.entityManager = entityManager;
        carDao = new CarDao(entityManager);
    }

    private static String[] updateCar(Scanner scanner) throws Failure {

        System.out.println(Message.REGISTRATION_NUMBER);
        String registrationNumber = scanner.nextLine();

        System.out.println(Message.CAR_MODEL);
        String carModel = scanner.nextLine();

        System.out.println(Message.IS_AVAILABLE);
        boolean isAvailable = Boolean.parseBoolean(scanner.nextLine());

        System.out.println(Message.TOWN);
        String townName = scanner.nextLine();

        System.out.println(Message.CAR_ADDRESS);
        String addressText = scanner.nextLine();

        if (registrationNumber.isEmpty() || carModel.isEmpty() || addressText.isEmpty()) {
            throw new CarException(ExceptionMessage.EMPTY_FIELD);
        }

        Address address = new Address();
        address.setAddressText(addressText);

        Town town = new Town();
        town.setTownName(townName);


        return new String[]{registrationNumber, carModel, String.valueOf(isAvailable), String.valueOf(address), String.valueOf(town)};
    }

    public void addCar() throws Failure {

        try {
            car = createCar();

            carDao.save(car);

            ResultParser resultParser = new ResultParser();
            resultParser.printCarInformation(car);

        } catch (CarException e) {
            System.err.println(ExceptionMessage.ERROR);
            System.err.println(ExceptionMessage.EMPTY_FIELD);
            System.err.println(Message.REENTER);
            System.out.println();

        }
    }

    public void updateCar() throws Failure {

        car = new Car();

        System.out.println(Message.REGISTRATION_NUMBER_FOR_UPDATE);
        String registrationNumber = scanner.nextLine();

        try {

            car = entityManager
                    .createQuery(CarQueries.CAR_WHERE_REGISTRATION_NUMBER + ":regNumber", Car.class)
                    .setParameter("regNumber", registrationNumber)
                    .getSingleResult();

            String[] carParams = updateCar(this.scanner);
            carDao.update(car, carParams);

            System.out.println(Message.CAR + registrationNumber + Message.UPDATED);

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_CAR);
        }
    }

    public void deleteCar() {

        System.out.println(Message.REGISTRATION_NUMBER_FOR_DELETE);
        String registrationNumber = scanner.nextLine();

        try {

            car = entityManager
                    .createQuery(CarQueries.CAR_WHERE_REGISTRATION_NUMBER + Message.PARAM, Car.class)
                    .setParameter(0, registrationNumber)
                    .getSingleResult();

            carDao.delete(car);

            System.out.println(Message.CAR_WITH_REGISTRATION + car.getRegistrationNumber() + Message.DELETED);

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_CAR);
        }
    }

    public List<Car> getCarByTown() {
        System.out.println(CarMessages.TOWN_TO_VIEW_CARS);
        String townName = scanner.nextLine();
        try {
            cars = entityManager
                    .createQuery(Message.FROM_CAR, Car.class)
                    .getResultList()
                    .stream()
                    .filter(c -> c.getTown().getTownName().equals(townName))
                    .collect(Collectors.toList());
            if(cars.isEmpty()){
                System.out.println(CarMessages.NO_CARS);
                return null;
            }
        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_TOWN);
        }

        return cars;
    }

    public List<Car> getAllCars() {

        return carDao.getAll();
    }

    private Car createCar()  {

        System.out.println(Message.REGISTRATION_NUMBER);
        String registrationNumber = scanner.nextLine();

        System.out.println(Message.CAR_MODEL);
        String carModel = scanner.nextLine();

        System.out.println(Message.TOWN);
        String townName = scanner.nextLine();

        System.out.println(Message.CAR_ADDRESS);
        String addressText = scanner.nextLine();

        if (registrationNumber.isEmpty() || carModel.isEmpty()
                || addressText.isEmpty() || townName.isEmpty()) {
            throw new CarException(ExceptionMessage.EMPTY_FIELD);
        }

        Town town = new Town();

        town.setTownName(townName);

        Address address = new Address();

        address.setAddressText(addressText);
        address.setTown(town);

        return new Car(registrationNumber, carModel, true, town, address);
    }
}
