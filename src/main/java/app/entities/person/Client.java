package app.entities.person;

import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.base.BaseEntity;
import app.entities.car.Car;
import app.entities.payment.CreditCard;
import app.entities.payment.Vaucher;
import app.entities.trip.Trip;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client extends BaseEntity {

    private String firstName;
    private String lastName;
    private String phone;
    private String drivingLicenseNumber;
    private String username ;
    private String password ;
    private String email;
    private Address address;
    private Town town;
    private Car car;
    private Set<Vaucher> vaucher ;
    private Set<CreditCard> creditCards;
    private Set<Trip> trips;

    public Client(String firstName, String lastName, String phone, String password, String drivingLicenseNumber, String username, String email, Town town , Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.username = username;
        this.email = email;
        this.town = town;
        this.address = address;
    }

    public Client() {
    }

    @Column(name = "username",nullable = false , unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password",nullable = false )
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name", length = 255, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 255, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "phone", length = 10, unique = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "driving_license_number", length = 20, nullable = false, unique = true)
    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    @Column(name = "email", length = 100, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(  name = "client_address_id", referencedColumnName = "id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(  name = "client_town_id", referencedColumnName = "id")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @OneToOne(mappedBy = "client")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL)
    public Set<Vaucher> getVaucher() {
        return vaucher;
    }

    public void setVaucher(Set<Vaucher> vaucher) {
        this.vaucher = vaucher;
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @OneToMany(mappedBy = "client", targetEntity = Trip.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return String.format("Client : %s %s " , this.getFirstName() , this.getLastName() );
    }


}
