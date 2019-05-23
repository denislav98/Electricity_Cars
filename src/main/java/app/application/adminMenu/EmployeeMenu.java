package app.application.adminMenu;

import app.dao.service.EmployeeService;
import app.utils.exeptions.Failure;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class EmployeeMenu {

    private EntityManager entityManager;
    private Scanner scanner = new Scanner(System.in);

    EmployeeMenu(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void run() throws Failure {
        int choice;

        EmployeeService employeeService = new EmployeeService(entityManager);

        do {
            System.out.println();
            System.out.println("===========================================");
            System.out.println("| 1. Add Employee                         |");
            System.out.println("| 2. Update Employee                      |");
            System.out.println("| 3. Delete Employee                      |");
            System.out.println("| 4. List of Empoyees                     |");
            System.out.println("===========================================");

            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Bye");
                    break;
                case 1:
                    employeeService.saveEmployee();
                    break;
                case 2:
                    employeeService.updateEmployee();
                    break;
                case 3:
                    employeeService.deleteEmployee();
                    break;
                case 4:
                    employeeService.getAllEmployees()
                            .forEach(System.out::println);
                    break;
                default:
                    System.out.println("Invalid selection");
            }

        } while (choice != 0);
    }
}
