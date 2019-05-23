package app.application.userMenu;

import app.dao.service.CreditCardService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class CreditCardMenu {

    private EntityManager entityManager;

    public CreditCardMenu(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void run() throws Failure {
        int choice;
        Scanner scanner = new Scanner(System.in);

        CreditCardService creditCardService = new CreditCardService(entityManager);
        ClientMenu clientMenu = new ClientMenu(entityManager);
        try {
            do {
                System.out.println();
                System.out.println("===========================================");
                System.out.println("| 1. Add credit card                      |");
                System.out.println("| 2. Update credit card                   |");
                System.out.println("| 3. Delete credit card                   |");
                System.out.println("| Press 0 to EXIT                         |");
                System.out.println("===========================================");

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 0:
                        System.out.println("Bye");
                        clientMenu.run();
                        break;
                    case 1:
                        creditCardService.addCreditCardToClient();
                        break;
                    case 2:
                        creditCardService.updateClientCreditCard();
                        break;
                    case  3:
                        creditCardService.deleteCreditCard();
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
