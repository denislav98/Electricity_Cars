package app.application.userMenu;

import app.dao.service.ClientService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserMenuLoader {

    public void loadUserMenu(EntityManager manager) throws Failure {
        int choice;

        ClientService clientService = new ClientService(manager);

        try {

            do {
                System.out.println();
                System.out.println("======================================");
                System.out.println("|      Welcome !                      |");
                System.out.println("======================================");
                System.out.println("| 1. Registration                     |");
                System.out.println("| 2. Sign in                          |");
                System.out.println("| Press 0 to EXIT                     |");
                System.out.println("=======================================");

                Scanner scanner = new Scanner(System.in);

                choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Bye");
                        break;
                    case 1:
                        clientService.addClient();
                        break;
                    case 2:
                        if (clientService.isSignIn()) {
                            ClientMenu clientMenu = new ClientMenu(manager);
                            clientMenu.run();
                        }
                        break;
                    default:
                        System.out.println("Invalid selection");
                }

            } while (choice != 0);
        } catch (InputMismatchException e) {
            System.out.println("Enter number instead of letter");
            loadUserMenu(manager);
        }
    }
}
