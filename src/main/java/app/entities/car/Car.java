package app.entities.car;

import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.base.BaseEntity;
import app.entities.person.Client;
import app.entities.service.Service;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "car")
public class Car extends BaseEntity {

    private String registrationNumber;
    private String carModel;
    private boolean isAvailable;
    private Town town;
    private Address address;
    private Set<Service> services;
    private Client client;

    public Car() {
    }

    public Car(String registrationNumber, String carModel, boolean isAvailable, Town town ,Address address) {
        this.registrationNumber = registrationNumber;
        this.carModel = carModel;
        this.isAvailable = isAvailable;
        this.town = town;
        this.address = address;
    }

    public Car(String registrationNumber, String carModel, boolean isAvailable,Town town, Set<Service> services, Client client) {
        this.registrationNumber = registrationNumber;
        this.carModel = carModel;
        this.isAvailable = isAvailable;
        this.town = town;
        this.services = services;
        this.client = client;
    }

    @Column(name = "registration_number", unique = true)
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Column(name = "car_model")
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Column(name = "is_available")
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_address_id", referencedColumnName = "id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_town_id", referencedColumnName = "id")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @OneToMany(mappedBy = "cars", cascade = CascadeType.ALL)
    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    @Override
    public String toString() {
        return String.format("Car registration number " +
                        ": %s , Model: %s , Available: " +
                        "%s Address: %s , Town : %s",
                this.registrationNumber, this.carModel,
                this.isAvailable, this.address.getAddressText() ,this.getTown() );
    }

}
