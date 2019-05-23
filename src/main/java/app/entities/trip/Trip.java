package app.entities.trip;

import app.entities.address.Town;
import app.entities.address.Address;
import app.entities.base.BaseEntity;
import app.entities.car.Car;
import app.entities.person.Client;
import app.entities.bill.Bill;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trip")
public class Trip extends BaseEntity {

    private Address fromAddress;
    private Address toAddress;
    private Date start_time;
    private Date end_time;
    private double chargeAmount;
    private Car car;
    private Client client;
    private Bill bill;
    private Town town;

    public Trip() {
    }

    public Trip(Address fromAddress, Date start_time, Car car, Client client, Town town) {
        this.fromAddress = fromAddress;
        this.start_time = start_time;
        this.car = car;
        this.client = client;
        this.town = town;
    }

    public Trip(Address toAddress, Date end_time, double chargeAmount) {
        this.toAddress = toAddress;
        this.end_time = end_time;
        this.chargeAmount = chargeAmount;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Address getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(Address fromAddress) {
        this.fromAddress = fromAddress;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Address getToAddress() {
        return toAddress;
    }

    public void setToAddress(Address toAddress) {
        this.toAddress = toAddress;
    }

    @Column(name = "start_trip_time")
    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    @Column(name = "end_trip_time")
    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    @Column(name = "charge_amount")
    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(  name = "trip_town_id", referencedColumnName = "id")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @OneToOne(mappedBy = "trip")
    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Trip : " +
                "fromAddress : " + fromAddress.getAddressText() +
                ", toAddress : " + toAddress.getAddressText() +
                ", start_time : " + start_time +
                ", end_time : " + end_time +
                ", chargeAmount : " + chargeAmount +
                ", car : " + car.getRegistrationNumber() +
                ", client : " + client.getUsername() +
                '}';
    }
}
