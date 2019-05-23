package app.entities.service;

import app.entities.base.BaseEntity;
import app.entities.car.Car;
import app.entities.person.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "service")
public class Service extends BaseEntity {

    private String serviceType;
    private Date serviceDate;
    private Car cars;
    private Employee employee;

    public Service(String serviceType, Date serviceDate, Car cars, Employee employee) {
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.cars = cars;
        this.employee = employee;
    }

    public Service() {

    }

    @Column(name = "service_type")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }


    @Column(name = "service_date")
    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    @ManyToOne
    public Car getCars() {
        return cars;
    }

    public void setCars(Car cars) {
        this.cars = cars;
    }

    @ManyToOne
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceType=" + serviceType +
                ", serviceDate=" + serviceDate +
                ", cars=" + cars +
                ", employee=" + employee +
                '}';
    }

}
