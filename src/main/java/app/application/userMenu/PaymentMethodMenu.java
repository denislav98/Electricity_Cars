package app.application.userMenu;

import app.dao.service.CreditCardService;
import app.dao.service.VaucherService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class PaymentMethodMenu {

    private EntityManager entityManager;

    public PaymentMethodMenu(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void run() throws Failure {
        int choice;
        Scanner scanner = new Scanner(System.in);

        CreditCardService creditCardService = new CreditCardService(entityManager);
        VaucherService vaucherService = new VaucherService(entityManager);
        ClientMenu clientMenu = new ClientMenu(entityManager);

        try {
            do {
                System.out.println();
                System.out.println("===========================================");
                System.out.println("| 1. Pay with credit card                 |");
                System.out.println("| 2. Pay with vaucher                     |");
                System.out.println("===========================================");

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 0:
                        System.out.println("Bye");
                        clientMenu.run();
                        break;
                    case 1:
                        creditCardService.payWithCreditCard();
                        break;
                    case 2:
                        vaucherService.payWithVaucherAfterTrip();
                        clientMenu.run();
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
