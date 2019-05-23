package app.application.adminMenu;

import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenuLoader {

    public void loadUserMenu(EntityManager manager) throws Failure {
        Scanner scanner = new Scanner(System.in);
        int choice;

        try {

            do {
                System.out.println();

                System.out.println("======================================");
                System.out.println("|      Electricity Cars               |");
                System.out.println("======================================");
                System.out.println("|      Loged as Admin                 |");
                System.out.println("======================================");
                System.out.println("| 1. Operations over Employee         |");
                System.out.println("| 2. Operations over Car              |");
                System.out.println("| 3. Car services made by Employee    |");
                System.out.println("| 4. Operations over Vaucher          |");
                System.out.println("| Press 0 to EXIT                     |");
                System.out.println("=======================================");

                choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Bye");
                        break;
                    case 1:
                        EmployeeMenu employeeMenu = new EmployeeMenu(manager);
                        employeeMenu.run();
                        break;
                    case 2:
                        CarMenu carMenu = new CarMenu(manager);
                        carMenu.run();
                        break;
                    case 3:
                        CarServiceMenu carServiceMenu = new CarServiceMenu(manager);
                        carServiceMenu.run();
                        break;
                    case 4:
                        VaucherMenu vaucherMenu = new VaucherMenu(manager);
                        vaucherMenu.run();
                        break;
                    default:
                        System.out.println("Invalid selection");
                }

            } while (choice != 0);
        } catch (InputMismatchException e) {
            System.out.println("Enter number ");
            loadUserMenu(manager);
        }
    }
}
