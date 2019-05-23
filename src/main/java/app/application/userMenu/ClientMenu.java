package app.application.userMenu;

import app.dao.service.BillService;
import app.dao.service.CarService;
import app.dao.service.ClientService;
import app.dao.service.TripService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class ClientMenu {
    private EntityManager entityManager;

    ClientMenu(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void run() throws Failure {
        int choice;
        Scanner scanner = new Scanner(System.in);

        ClientService clientService = new ClientService(entityManager);
        CarService carService = new CarService(entityManager);
        TripService tripService = new TripService(entityManager);
        BillService billService = new BillService(entityManager);
        CreditCardMenu creditCardMenu = new CreditCardMenu(entityManager);
        PaymentMethodMenu paymentMethodMenu = new PaymentMethodMenu(entityManager);

        try {
            do {
                System.out.println();
                System.out.println("===========================================");
                System.out.println("| 1. Start new Trip                       |");
                System.out.println("| 2. End Trip                             |");
                System.out.println("| 3. Pay                                  |");
                System.out.println("| 4. Get bill                             |");
                System.out.println("| 5. List with trips                      |");
                System.out.println("| 6. Credit Cards                         |");
                System.out.println("| 5. Update profile                       |");
                System.out.println("| Press 0 to EXIT                         |");
                System.out.println("===========================================");

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 0:
                        System.out.println("Bye");
                        break;
                    case 1:

                        carService.getCarByTown().forEach(System.out::println);
                        tripService.startTrip();

                        break;
                    case 2:
                        tripService.endTrip();
                        break;
                    case 3:
                        tripService.showTaxAmount();
                        paymentMethodMenu.run();

                        break;
                    case 4:
                        billService.createBill();
                        break;
                    case 5:
                        tripService.getAllTripsForClient().forEach(System.out::println);
                        break;
                    case 6:
                        creditCardMenu.run();
                        break;
                    case 7:
                        clientService.updateClientProfileInformation();
                        break;
                    default:
                        System.out.println("Invalid selection");
                }

            } while (choice != 0);
        } catch (NumberFormatException e) {
            System.out.println("Enter correct number instead of letter");
            run();
        }
    }
}
