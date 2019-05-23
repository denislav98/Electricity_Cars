package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.CreditCardDao;
import app.dao.view.ResultParser;
import app.entities.payment.CreditCard;
import app.entities.person.Client;
import app.utils.constants.ClientMessages;
import app.utils.constants.CreditCardMessage;
import app.utils.constants.ExceptionMessage;
import app.utils.constants.Message;
import app.utils.exeptions.CreditCardException;
import app.utils.queries.ClientQuieries;
import app.utils.queries.CreditCardQueries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Scanner;

public class CreditCardService {
    private final Dao<CreditCard> creditCardDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;
    private Client client;
    private CreditCard card;

    public CreditCardService(EntityManager entityManager) {
        this.entityManager = entityManager;
        creditCardDao = new CreditCardDao(entityManager);
    }

    private CreditCard createCreditCard() {

        System.out.println(CreditCardMessage.CREDIT_CARD_NUMBER);
        String creditCardNumber = scanner.nextLine();

        System.out.println(CreditCardMessage.CARD_TYPE);
        String cardType = scanner.nextLine();

        System.out.println(CreditCardMessage.EXPIRY_MONTH);
        String monthOfExpiry = scanner.nextLine();

        System.out.println(CreditCardMessage.EXPIRY_YEAR);
        String yearOfExpiry = scanner.nextLine();

        if (creditCardNumber.isEmpty() || cardType.isEmpty()
                || monthOfExpiry.isEmpty() || yearOfExpiry.isEmpty()) {
            throw new CreditCardException(ExceptionMessage.EMPTY_FIELD);
        }

        System.out.println(ClientMessages.USERNAME);
        String username = scanner.nextLine();

        client = entityManager
                .createQuery(ClientQuieries.CLIENT_WITH_USERNAME + ClientQuieries.USERNAME_PARAM, Client.class)
                .setParameter(ClientQuieries.USERNAME_ACTUAL, username)
                .getSingleResult();


        return new CreditCard(creditCardNumber, cardType, monthOfExpiry, yearOfExpiry, client);
    }

    public void addCreditCardToClient() {

        try {

            card = createCreditCard();

            creditCardDao.save(card);
            ResultParser resultParser = new ResultParser();
            resultParser.printCreditCardInfo(card, client);

        } catch (CreditCardException e) {
            System.err.println(ExceptionMessage.EMPTY_FIELD);
            System.err.println(Message.REENTER);
            System.out.println();
        }
    }

    public void updateClientCreditCard() {
        System.out.println(Message.CARD_NUMBER);
        String cardNumber = scanner.nextLine();

        try {

            card = entityManager
                    .createQuery(CreditCardQueries.FROM_CREDIT_CARD + CreditCardQueries.CARD_PARAM, CreditCard.class)
                    .setParameter(CreditCardQueries.CARD_PARAM_ACTUAL, cardNumber)
                    .getSingleResult();

            CreditCard creditCard = createCreditCard();

            String cardNum = creditCard.getCardNumber();
            String cardType = creditCard.getCardType();
            String monthOfExpiry = creditCard.getExpirationMonth();
            String yearOfExpiry = creditCard.getExpirationYear();
            Client client = creditCard.getClient();

            String[] cardParams = new String[]{
                    cardNum, cardType, monthOfExpiry, yearOfExpiry
            };

            card.setClient(client);

            creditCardDao.update(card, cardParams);

            System.out.println("Card : " + card.getCardNumber() + " updated");

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.NO_SUCH_EMPLOYEE);
        }
    }

    public void payWithCreditCard() {

        try {

            System.out.println(CreditCardMessage.CREDIT_CARD_NUMBER);

            String creditCardNumber = scanner.nextLine();

            CreditCard card = entityManager
                    .createQuery(CreditCardQueries.FROM_CREDIT_CARD_WITH_NUMBER + CreditCardQueries.CARD_PARAM, CreditCard.class)
                    .setParameter(CreditCardQueries.CARD_PARAM_ACTUAL, creditCardNumber)
                    .getSingleResult();

            System.out.println(CreditCardMessage.PAID_SUCCESSFULLY + card.getCardNumber());

        } catch (NoResultException e) {
            System.out.println(CreditCardMessage.NO_SUCH_CARD);
        }
    }

    public void deleteCreditCard() {
        System.out.println(CreditCardMessage.CREDIT_CARD_NUMBER);
        String cardNumber = scanner.nextLine();

        try {

            card = entityManager
                    .createQuery(CreditCardQueries.FROM_CREDIT_CARD_WITH_NUMBER + CreditCardQueries.CARD_PARAM, CreditCard.class)
                    .setParameter(CreditCardQueries.CARD_PARAM_ACTUAL, cardNumber)
                    .getSingleResult();

            creditCardDao.delete(card);

            System.out.println(CreditCardMessage.DELETED + cardNumber + Message.DELETED);

        } catch (NoResultException e) {
            System.out.println(CreditCardMessage.NO_SUCH_CARD);
        }
    }
}
