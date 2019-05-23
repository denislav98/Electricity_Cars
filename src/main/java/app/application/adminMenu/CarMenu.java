package app.application.adminMenu;

import app.dao.service.CarService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class CarMenu {
    private EntityManager entityManager;

    CarMenu(EntityManager manager) {
        this.entityManager = manager;
    }

    public void run() throws Failure {

        CarService carService = new CarService(entityManager);
        int choice = 0;
        do {
            System.out.println();
            System.out.println("==============================");
            System.out.println("|        1. Add new Car      |");
            System.out.println("|        2. Update Car       |");
            System.out.println("|        3. Delete Car       |");
            System.out.println("|        4. List of Cars     |");
            System.out.println("| Press 0 to EXIT            |");
            System.out.println("==============================");

            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Bye");
                    break;
                case 1:
                    carService.addCar();
                    break;
                case 2:
                    carService.updateCar();
                    break;
                case 3:
                    carService.deleteCar();
                    break;
                case 4:
                    carService.getAllCars().forEach(System.out::println);
                    break;
                default:
                    System.out.println("Invalid selection");
            }

        } while (choice != 0);

    }
}
