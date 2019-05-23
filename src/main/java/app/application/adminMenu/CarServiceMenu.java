package app.application.adminMenu;

import app.dao.service.CarServiceService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class CarServiceMenu {
    private EntityManager entityManager;

    CarServiceMenu(EntityManager manager) {
        this.entityManager = manager;
    }

    public void run() throws Failure {

        Scanner scanner = new Scanner(System.in);
        int choice;

        CarServiceService carServiceService = new CarServiceService(entityManager);

        do {
            System.out.println();
            System.out.println("=========================================");
            System.out.println("| 1. Add car service                    |");
            System.out.println("| 2. List of services made by employee  |");
            System.out.println("| Press 0 to EXIT                       |");
            System.out.println("=========================================");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 0:
                    System.out.println("Bye");
                    break;
                case 1:
                    carServiceService.addCarService();
                    break;
                case 2:
                    carServiceService.getAllServicesMadeByEmployee();
                    break;
                default:
                    System.out.println("Invalid selection");
            }

        } while (choice != 0);

    }
}
