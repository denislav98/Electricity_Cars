package app.application.adminMenu;

import app.dao.service.VaucherService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class VaucherMenu {
    private EntityManager entityManager;
    private Scanner scanner = new Scanner(System.in);

    VaucherMenu(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void run() throws Failure {
        int choice;

        VaucherService vaucherService = new VaucherService(entityManager);

        do {
            System.out.println();
            System.out.println("===========================================");
            System.out.println("| 1. Add Vaucher                          |");
            System.out.println("| 2. Update Vaucher                       |");
            System.out.println("| 3. Delete Vaucher                       |");
            System.out.println("| 4. List of Clients with Vauchers        |");
            System.out.println("| Press 0 to EXIT                         |");
            System.out.println("===========================================");

            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Bye");
                    break;
                case 1:
                    vaucherService.addVacher();
                    break;
                case 2:
                    vaucherService.updateVaucher();
                    break;
                case 3:
                    vaucherService.deleteVaucher();
                    break;
                case 4:
                    vaucherService.getAllVauchers()
                            .forEach(System.out::println);
                    break;
                default:
                    System.out.println("Invalid selection");
            }

        } while (choice != 0);
    }
}
