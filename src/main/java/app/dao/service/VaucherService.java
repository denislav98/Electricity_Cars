package app.dao.service;

import app.application.userMenu.PaymentMethodMenu;
import app.dao.contract.Dao;
import app.dao.daoImpl.VaucherDao;
import app.entities.payment.Vaucher;
import app.entities.person.Client;
import app.utils.constants.ExceptionMessage;
import app.utils.constants.Message;
import app.utils.exeptions.Failure;
import app.utils.exeptions.VaucherException;
import app.utils.queries.ClientQuieries;
import app.utils.queries.VaucherQueries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.*;

public class VaucherService {
    private final Dao<Vaucher> vaucherDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;
    private Vaucher vaucher;

    public VaucherService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.vaucherDao = new VaucherDao(entityManager);
    }

    public void addVacher() {
        try {
            vaucher = createVaucher();

            vaucherDao.save(vaucher);

            System.out.println(Message.VAUCHER_ADDED_SUCCESSFULLY);

        } catch (Failure e) {
            System.err.println(ExceptionMessage.ERROR);
            System.err.println(ExceptionMessage.EMPTY_FIELD);
            System.err.println(Message.REENTER);
            System.out.println();
        }
    }

    private Vaucher createVaucher() {

        System.out.println(Message.CLIENT_DRIVING_LICENSE);
        String drivingLicense = scanner.nextLine();

        Client client = getClientWithDrivingLicense(drivingLicense);

        System.out.println(Message.VAUCHER_NUMBER);
        String vaucherNumber = scanner.nextLine();

        System.out.println(Message.VAUCHER_NEW_AMOUNT);
        double amount = 0;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Enter valid amount");
        }

        Date releasedOn = new Date();
        Date expiryDate = new Date();

        try {
            List<Date> dates = getVaucherIssueDate();
            releasedOn = dates.get(0);
            expiryDate = dates.get(1);

        } catch (IllegalArgumentException e) {
            System.err.println(ExceptionMessage.DATE_ERROR);
        }

        if (drivingLicense.isEmpty() || vaucherNumber.isEmpty() || amount == 0) {
            throw new Failure(ExceptionMessage.EMPTY_FIELD);
        }

        return new Vaucher(vaucherNumber, amount, releasedOn, expiryDate, false, client);
    }

    private List<Date> getVaucherIssueDate() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        System.out.println(Message.ISSUE_DAY);
        int day = Integer.parseInt(scanner.nextLine());

        cal.add(Calendar.DAY_OF_MONTH, day);

        System.out.println(Message.ISSUE_MONTH);
        int month = Integer.parseInt(scanner.nextLine());

        cal.add(Calendar.MONTH, month);

        System.out.println(Message.EXPIRY_YEAR);
        int year = Integer.parseInt(scanner.next());

        cal.add(Calendar.YEAR, year);

        java.util.Date expirationYear = cal.getTime();

        Date dateYear = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateYear);


        java.util.Date releasedOn = new Date();

        if (day < 0 || day > 31 || month < 1 || month > 12) {
            throw new IllegalArgumentException();
        }

        return new LinkedList<Date>() {{
            add(releasedOn);
            add(expirationYear);
        }};
    }

    private Client getClientWithDrivingLicense(String drivingLicense) {
        Client client = new Client();
        try {

            client = entityManager
                    .createQuery(ClientQuieries.CLIENT_WITH_DRIVING_LICENSE + ":license", Client.class)
                    .setParameter("license", drivingLicense)
                    .getSingleResult();

        } catch (NoResultException e) {
            System.out.println(Message.NO_SUCH_CLIENT_WITH_DRIVING_LICENSE + drivingLicense);
        }
        return client;
    }

    public void updateVaucher() {

        Scanner scanner = new Scanner(System.in);
        vaucher = new Vaucher();

        System.out.println(Message.VAUCHER_NUMBER_FOR_UPDATE);
        String vaucherNumber = scanner.nextLine();
        try {

            vaucher = entityManager
                    .createQuery(VaucherQueries.VAUCHER_WITH_NUMBER + ":vaucher", Vaucher.class)
                    .setParameter("vaucher", vaucherNumber)
                    .getSingleResult();

            String[] vaucherParams = updateParams();

            List<Date> dates = getVaucherIssueDate();

            Date releasedOn = new Date();
            Date expiryDate = new Date();

            try {

                releasedOn = dates.get(0);
                expiryDate = dates.get(1);

            } catch (IllegalArgumentException e) {
                System.err.println(ExceptionMessage.DATE_ERROR);
            }

            vaucher.setReleasedOn(releasedOn);
            vaucher.setExpiryDate(expiryDate);

            vaucherDao.update(vaucher, vaucherParams);

            System.out.println("Vaucher : " + vaucherNumber + " updated!");

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_VAUCHER);
            updateVaucher();
        } catch (VaucherException v) {
            System.err.println(ExceptionMessage.INVALID_AMOUNT_OR_NUMBER);
        }
    }


    private String[] updateParams() {

        System.out.println(Message.VAUCHER_NEW_NUMBER);
        String vaucherNumber = scanner.nextLine();

        System.out.println(Message.VAUCHER_NEW_AMOUNT);
        Double vaucherAmount = Double.valueOf(scanner.nextLine());

        if (vaucherNumber.isEmpty() || vaucherAmount == 0) {
            throw new VaucherException(ExceptionMessage.EMPTY_FIELD);
        }

        return new String[]{vaucherNumber, String.valueOf(vaucherAmount)};
    }

    public void deleteVaucher() {

        System.out.println(Message.VAUCHER_NUMBER);
        String vaucherNumber = scanner.nextLine();

        try {

            vaucher = entityManager
                    .createQuery(VaucherQueries.VAUCHER_WITH_NUMBER + Message.PARAM, Vaucher.class)
                    .setParameter(0, vaucherNumber)
                    .getSingleResult();

            vaucherDao.delete(vaucher);

            System.out.println(Message.VAUCHER + vaucher.getVaucherNumber() + Message.DELETED);

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_VAUCHER);
            deleteVaucher();
        }
    }

    public List<Vaucher> getAllVauchers() {

        return vaucherDao.getAll();
    }

    public void payWithVaucherAfterTrip() {

        System.out.println("Enter vaucher number : ");
        String vaucherNumber = scanner.nextLine();

        PaymentMethodMenu paymentMethodMenu = new PaymentMethodMenu(entityManager);

        try {
            vaucher = entityManager
                    .createQuery(VaucherQueries.VAUCHER_WITH_NUMBER + ":vaucher" + VaucherQueries.IS_VAUCHER_USED, Vaucher.class)
                    .setParameter("vaucher", vaucherNumber)
                    .getSingleResult();

            vaucher.setUsed(true);

            entityManager.getTransaction().begin();

            entityManager.merge(vaucher);

            entityManager.getTransaction().commit();

            System.out.println(Message.PAID_WITH_VAUCHER);
        } catch (NoResultException e) {
            System.out.println(Message.CLIENT_WITHOUT_VAUCHER);
            paymentMethodMenu.run();
        }
    }
}
