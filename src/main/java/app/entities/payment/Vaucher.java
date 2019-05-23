package app.entities.payment;

import app.entities.base.BaseEntity;
import app.entities.person.Client;
import app.entities.bill.Bill;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vaucher")
public class Vaucher extends BaseEntity {

    private String vaucherNumber;
    private double amount;
    private Date releasedOn;
    private Date expiryDate;
    private boolean isUsed;
    private Client client;

    public Vaucher() {
    }

    public Vaucher(String vaucherNumber, double amount, Date releasedOn, Date expiryDate, boolean isUsed, Client client) {
        this.vaucherNumber = vaucherNumber;
        this.amount = amount;
        this.releasedOn = releasedOn;
        this.expiryDate = expiryDate;
        this.isUsed = isUsed;
        this.client = client;
    }

    @Column(name = "vaucher_number", length = 10, nullable = false, unique = true)
    public String getVaucherNumber() {
        return vaucherNumber;
    }

    public void setVaucherNumber(String vaucherNumber) {
        this.vaucherNumber = vaucherNumber;
    }

    @Column(name = "vaucher_amount", nullable = false)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(name = "released_on")
    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    @Column(name = "date_of_expiry")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Column(name = "is_vaucher_used")
    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return String.format("Vaucher : %s with amount %s on client %s", this.vaucherNumber, this.amount, this.getClient());
    }
}
