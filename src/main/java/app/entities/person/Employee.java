package app.entities.person;

import app.entities.address.Address;
import app.entities.address.Town;
import app.entities.base.BaseEntity;
import app.entities.service.Service;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Town town;
    private Address address;
    private Set<Service> madeServices;


    public Employee(String firstName, String lastName, String phone, String email, Town town, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.town = town;
        this.address = address;
    }

    public Employee(String firstName, String lastName, Set<Service> madeServices) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.madeServices = madeServices;
    }

    public Employee() {
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

    @Column(name = "email", length = 100, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_address_id", referencedColumnName = "id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_town_id", referencedColumnName = "id")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    public Set<Service> getMadeServices() {
        return madeServices;
    }

    public void setMadeServices(Set<Service> madeServices) {
        this.madeServices = madeServices;
    }

    @Override
    public String toString() {
        return String.format("Employee : %s %s %s %s ",
                this.firstName
                , this.lastName
                , this.address.getAddressText()
                , town.getTownName());
    }
}
