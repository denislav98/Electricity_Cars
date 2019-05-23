package app.application.mainMenu;

import app.application.adminMenu.AdminMenuLoader;
import app.application.userMenu.UserMenuLoader;
import app.dao.service.AdminService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.InputMismatchException;
import java.util.Scanner;

final class MainMenu {

    void loadMeinMenu(EntityManager manager) throws Failure {
        int choice;

        try {

            do {
                System.out.println();
                System.out.println("======================================");
                System.out.println("|      Welcome !                      |");
                System.out.println("======================================");
                System.out.println("| 1.Login as Admin                    |");
                System.out.println("| 2.Login as User                     |");
                System.out.println("| Press 0 to EXIT                     |");
                System.out.println("=======================================");

                Scanner scanner = new Scanner(System.in);

                choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Bye");
                        break;
                    case 1:
                        AdminMenuLoader adminMenuLoader = new AdminMenuLoader();
                        AdminService adminService = new AdminService(manager);
                        if (adminService.isSignIn()) {
                            adminMenuLoader.loadUserMenu(manager);
                        }

                        loadMeinMenu(manager);

                        System.out.println();
                        break;
                    case 2:
                        UserMenuLoader userMenuLoader = new UserMenuLoader();
                        userMenuLoader.loadUserMenu(manager);
                        break;
                    default:
                        System.out.println("Invalid selection");
                }

            } while (choice != 0);
        } catch (InputMismatchException e) {
            System.out.println("Enter number instead of letter");
            loadMeinMenu(manager);
        }
    }
}
