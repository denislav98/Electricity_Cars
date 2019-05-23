package app.application.mainMenu;

import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {

    public static void main(String[] args) throws Failure {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("spark");

        EntityManager manager = entityManagerFactory.createEntityManager();

        MainMenu menu = new MainMenu();
        menu.loadMeinMenu(manager);

        manager.close();
        entityManagerFactory.close();

        System.out.println("Success!");
    }

}
