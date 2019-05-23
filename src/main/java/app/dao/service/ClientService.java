package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.ClientDao;
import app.dao.view.ResultParser;
import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.person.Client;
import app.utils.constants.ExceptionMessage;
import app.utils.constants.Message;
import app.utils.exeptions.ClientException;
import app.utils.exeptions.Failure;
import app.utils.queries.ClientQuieries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Scanner;

public class ClientService {

    private final Dao<Client> clientDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;
    private Client client;

    public ClientService(EntityManager entityManager) {
        this.entityManager = entityManager;
        clientDao = new ClientDao(entityManager);
    }

    public void updateClientProfileInformation() {
        System.out.println(Message.USERNAME);
        String username = scanner.nextLine();

        try {

            client = entityManager
                    .createQuery(ClientQuieries.FROM_CLIENT_WITH_USERNAME + Message.USERNAME_PARAM, Client.class)
                    .setParameter(Message.USERNAME_PARAM_ACTUAL, username)
                    .getSingleResult();

            String[] clientParams = updateClient();
            clientDao.update(client, clientParams);

            System.out.println("Client : " + client.getUsername() + " updated");

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_USER);
        }

    }

    private String[] updateClient() {

        Client client1 = createClient();

        String username = client1.getUsername();
        String password = client1.getPassword();
        String firstName = client1.getFirstName();
        String lastName = client1.getLastName();
        String phone = client1.getPhone();
        String email = client1.getEmail();
        String townName = client1.getTown().getTownName();
        String addressText = client1.getAddress().getAddressText();
        String drivingLicense = client1.getDrivingLicenseNumber();

        return new String[]{username, password, firstName, lastName, phone, email, townName, addressText, drivingLicense};
    }

    public void addClient() throws Failure {

        try {
            client = createClient();

            clientDao.save(client);

            ResultParser resultParser = new ResultParser();
            resultParser.printClientInfo(client);
        } catch (Failure e) {
            System.err.println(ExceptionMessage.EMPTY_FIELD);
            System.err.println(Message.REENTER);
            System.out.println();
        }
    }

    private Client createClient() {

        System.out.println(Message.USERNAME);
        String username = scanner.nextLine();

        System.out.println(Message.PASSWORD);
        String password = scanner.nextLine();

        System.out.println(Message.FIRST_NAME);
        String firstName = scanner.nextLine();

        System.out.println(Message.LAST_NAME);
        String lastName = scanner.nextLine();

        System.out.println(Message.PHONE);
        String phone = scanner.nextLine();

        System.out.println(Message.EMAIL);
        String mail = scanner.nextLine();

        System.out.println(Message.TOWN);
        String townName = scanner.nextLine();

        System.out.println(Message.CLIENT_ADDRESS);
        String addressText = scanner.nextLine();

        System.out.println(Message.DRIVING_LICENSE_NUMBER);
        String drivingLicense = scanner.nextLine();

        if (firstName.isEmpty() || lastName.isEmpty() || addressText.isEmpty()
                || phone.isEmpty() || mail.isEmpty() || drivingLicense.isEmpty()
                || username.isEmpty() || password.isEmpty() || townName.isEmpty()) {
            throw new ClientException(ExceptionMessage.EMPTY_FIELD);
        }

        Town town = new Town();
        town.setTownName(townName);

        Address address = new Address();

        address.setAddressText(addressText);
        address.setTown(town);

        return new Client(firstName, lastName, phone, password, drivingLicense, username, mail, town, address);

    }

    public boolean isSignIn() {

        System.out.println(Message.USERNAME);
        String username = scanner.nextLine();

        System.out.println(Message.PASSWORD);
        String password = scanner.nextLine();

        try {
            client = entityManager
                    .createQuery(
                            ClientQuieries.FROM_CLIENT_WITH_USERNAME + ":username" +
                                    Message.WITH_PASSWORD + ":pass", Client.class)
                    .setParameter("username", username)
                    .setParameter("pass", password)
                    .getSingleResult();

            return true;

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.WRONG_INVALID_USERNAME_OR_PASSWORD);
        }

        return false;
    }
}
