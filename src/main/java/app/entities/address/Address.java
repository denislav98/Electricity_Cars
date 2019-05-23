package app.entities.address;

import app.entities.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    private String addressText;
    private Town town;

    public Address() {
    }

    public Address(String addressText) {
        this.addressText = addressText;
    }

    @Column(name = "address_text", length = 500, nullable = false)
    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return String.format("Town : %s , Address %s ", this.town.getTownName() , this.addressText );
    }


}
