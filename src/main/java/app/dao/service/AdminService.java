package app.dao.service;

import app.dao.contract.Dao;
import app.dao.daoImpl.AdminDao;
import app.entities.persondetails.Admin;
import app.utils.constants.ExceptionMessage;
import app.utils.constants.Message;
import app.utils.queries.AdminQueries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Scanner;

public class AdminService {
    private final Dao<Admin> adminDao;
    private Scanner scanner = new Scanner(System.in);
    private EntityManager entityManager;

    public AdminService(EntityManager entityManager) {
        this.entityManager = entityManager;
        adminDao = new AdminDao(entityManager);
    }

    public boolean isSignIn() {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println(Message.USERNAME);
            String username = scanner.nextLine();

            System.out.println(Message.PASSWORD);
            String password = scanner.nextLine();

            Admin admin = entityManager
                    .createQuery(
                            AdminQueries.FROM_ADMIN + ":username" +
                                    AdminQueries.WITH_PASSWORD + ":pass", Admin.class)
                    .setParameter("username", username)
                    .setParameter("pass", password)
                    .getSingleResult();

            return true;

        } catch (NoResultException e) {
            System.out.println(ExceptionMessage.WRONG_INVALID_USERNAME_OR_PASSWORD);
        }

        return false;
    }

    public void adminRegistration() {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println(Message.USERNAME);
            String username = scanner.nextLine();

            System.out.println(Message.PASSWORD);
            String password = scanner.nextLine();

            Admin admin = new Admin(username, password);

            adminDao.save(admin);

        } catch (NullPointerException e) {
            System.out.println("Empty field entered");
        }


    }
}
