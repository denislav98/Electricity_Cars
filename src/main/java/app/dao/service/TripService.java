package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.TripDao;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.car.Car;
import app.entities.person.Client;
import app.entities.trip.Trip;
import app.utils.constants.*;
import app.utils.queries.CarQueries;
import app.utils.queries.ClientQuieries;
import app.utils.queries.TripQueries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TripService {

    private final Dao<Trip> tripDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;
    private double tax;
    private long minutesDrived;
    private List<Trip> clientTrips;

    public TripService(EntityManager entityManager) {
        this.entityManager = entityManager;
        tripDao = new TripDao(entityManager);
    }

    public void startTrip() {

        System.out.println(ClientMessages.DRIVING_LICENSE);
        String drivingLicenseNumber = scanner.nextLine();

        System.out.println(CarMessages.ENTER_REG_NUM);
        String carRegistrationNumber = scanner.nextLine();

        try {

            Client client = entityManager
                    .createQuery(ClientQuieries.CLIENT_WITH_DRIVING_LICENSE + ":number", Client.class)
                    .setParameter("number", drivingLicenseNumber)
                    .getSingleResult();

            Car car = entityManager
                    .createQuery(CarQueries.CAR_WHERE_REGISTRATION_NUMBER + CarQueries.REG_NUM_PARAM + CarQueries.AND_IS_AVAILABLE, Car.class)
                    .setParameter(CarQueries.REG_NUM_ACTUAL, carRegistrationNumber)
                    .getSingleResult();


            Trip trip = tripParams(client, car);
            trip.getCar().setAvailable(false);
            tripDao.save(trip);

            System.out.println(TripMessagess.TRIP_ENDED);

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.UNAVAILABLE_CAR + " or "
                    + ExceptionMessage.INVALID_DRIVING_LICENSE_NUMBER);
        }
    }

    private Trip tripParams(Client client, Car car) {

        Address address = car.getAddress();
        Town town = car.getTown();
        Date startTime = new Date();

        return new Trip(address, startTime, car, client, town);
    }

    public void endTrip() {

        try {
            System.out.println(TripMessagess.DRIVING_LICENSE_TO_END);
            String drivingLicenseNumber = scanner.nextLine();

            Client client = entityManager
                    .createQuery(ClientQuieries.CLIENT_WITH_DRIVING_LICENSE + ClientQuieries.LICENSE_NUMBER_PARAM, Client.class)
                    .setParameter(ClientQuieries.LICENSE_NUMBER_ACTUAL, drivingLicenseNumber)
                    .getSingleResult();

            int clientId = client.getId();
            Trip trip = entityManager
                    .createQuery(TripQueries.FROM_TRIP_WITH_END_TIME + TripQueries.ID_PARAM, Trip.class)
                    .setParameter(TripQueries.ID_ACTUAL, clientId)
                    .getSingleResult();

            trip.setEnd_time(new Date());

            tax = calculateTaxAmount(trip.getStart_time(), trip.getEnd_time());

            trip.setChargeAmount(tax);

            trip.getCar().setAvailable(true);

            String[] tripParams = getTripParams();

            tripDao.update(trip, tripParams);

        } catch (NullPointerException e) {
            System.out.println(ExceptionMessage.TRIP_NOT_STARTED);
        }
    }

    public void showTaxAmount() {
        System.out.println("You have drived : " + minutesDrived + " minutes");
        System.out.println("Your bill is : " + tax + "lv");
    }

    public List<Trip> getAllTripsForClient() {

        System.out.println(Message.ENTER_YOUR_DRIVING_LICENSE);
        String drivingLicenseNumber = scanner.nextLine();

        try {

            Client client = entityManager
                    .createQuery(ClientQuieries.CLIENT_WITH_DRIVING_LICENSE + ":number", Client.class)
                    .setParameter("number", drivingLicenseNumber)
                    .getSingleResult();

            int clientId = client.getId();

            clientTrips = entityManager
                    .createQuery(TripQueries.FROM_TRIP_WITH_CLIENT_ID + ":id ", Trip.class)
                    .setParameter("id", clientId)
                    .getResultList();

            if (clientTrips.isEmpty()) {
                return null;
            }

            return clientTrips;

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.UNAVAILABLE_CAR + " or "
                    + ExceptionMessage.INVALID_DRIVING_LICENSE_NUMBER);
        }

        return clientTrips;
    }

    private String[] getTripParams() {

        System.out.println(Message.TOWN_TO_LEAVE);
        String townName = scanner.nextLine();


        System.out.println(Message.ADDRESS_TO_LEAVE);
        String atAddress = scanner.nextLine();

        return new String[]{townName, atAddress};
    }

    private double calculateTaxAmount(Date startTripTime, Date endTripTime) {

        long drivingPeriod = startTripTime.getTime() - endTripTime.getTime();

        minutesDrived = Math.abs(TimeUnit.MINUTES.convert(drivingPeriod, TimeUnit.MILLISECONDS));

        return minutesDrived * TripQueries.TAX_CONSTANT;

    }
}