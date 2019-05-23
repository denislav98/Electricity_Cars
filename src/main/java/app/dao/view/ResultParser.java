package app.dao.view;

import app.entities.bill.Bill;
import app.entities.car.Car;
import app.entities.payment.CreditCard;
import app.entities.person.Client;
import app.entities.person.Employee;
import app.entities.service.Service;
import app.utils.constants.EmployeeMessage;
import app.utils.constants.Message;

import java.util.List;

public class ResultParser {

    public void printEmployee(Employee employee) {
        System.out.println(
                EmployeeMessage.EMPLOYEE +
                        employee.getFirstName() + " " +
                        employee.getLastName() + " " +
                        Message.ADDED_SUCCESSFULLY);
    }

    public void printBillInformation(Bill bill) {

        System.out.println("Date : " + bill.getBillDate());
        System.out.println("Trip : " + bill.getTrip().getFromAddress() + " " + bill.getTrip().getToAddress());
        System.out.println("Charged : " + bill.getTrip().getChargeAmount() + " lv");
        System.out.println("Client : " + bill.getClient().getFirstName());

    }

    public void printCarInformation(Car car){
        System.out.println(
                Message.CAR +
                        car.getRegistrationNumber() + " " +
                        car.getCarModel() + " " +
                        Message.ADDED_SUCCESSFULLY);
    }

    public void printService(Service service) {
        System.out.println(
                "Service : " +
                        service.getServiceType() + " " +
                        " added successfully to Car : " +
                        service.getCars().getRegistrationNumber() + " " +
                        service.getCars().getCarModel() +
                        " from Employee : " + " " +
                        service.getEmployee().getFirstName() +
                        service.getEmployee().getLastName());
    }

    public void printAllServices(List<Service> services) {
        for (Service s : services) {
            System.out.println(
                    EmployeeMessage.EMPLOYEE +
                            s.getEmployee().getFirstName() + " " +
                            s.getEmployee().getLastName() +
                            Message.SERVICE + " " +
                            s.getServiceType() + " " + " at " +
                            s.getServiceDate() + " " + " on Car : " +
                            s.getCars().getRegistrationNumber() + " " +
                            s.getCars().getCarModel()
            );
        }
    }

    public void printClientInfo(Client client){
        System.out.println(
                Message.CLIENT +
                        client.getFirstName() + " " +
                        client.getLastName() + " " +
                        Message.ADDED_SUCCESSFULLY);
    }

    public void printCreditCardInfo(CreditCard card , Client client){
        System.out.println(Message.CARD +
                card.getCardNumber() +
                Message.ADDED_TO +
                Message.CLIENT +
                client.getFirstName() +
                " " +
                client.getLastName());
    }

}
