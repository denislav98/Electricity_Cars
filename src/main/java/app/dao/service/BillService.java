package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.BillDao;
import app.dao.view.ResultParser;
import app.entities.bill.Bill;
import app.entities.person.Client;
import app.entities.trip.Trip;
import app.utils.queries.ClientQuieries;
import app.utils.queries.TripQueries;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BillService {

    private final Dao<Bill> billDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;
    private ResultParser resultParser = new ResultParser();
    public BillService(EntityManager entityManager) {
        this.entityManager = entityManager;
        billDao = new BillDao(entityManager);
    }

    public void createBill() {
        System.out.println("Enter your driving license : ");
        String drivingLicense = scanner.nextLine();


        Client client = entityManager
                .createQuery(ClientQuieries.CLIENT_WITH_DRIVING_LICENSE
                        + ClientQuieries.LICENSE_NUMBER_PARAM, Client.class)
                .setParameter(ClientQuieries.LICENSE_NUMBER_ACTUAL, drivingLicense)
                .getSingleResult();

        List<Trip> trips = entityManager
                .createQuery(TripQueries.FROM_TRIP_WITH_CLIENT_ID
                        + TripQueries.ID_PARAM , Trip.class)
                .setParameter(TripQueries.ID_ACTUAL , client.getId())
                .getResultList();

        Date billDate = new Date();
        Bill bill = new Bill();
        for(Trip trip: trips){

            bill = new Bill(billDate , trip , client);
            billDao.save(bill);
        }

        resultParser.printBillInformation(bill);
    }



}
