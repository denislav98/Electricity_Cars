package app.entities.bill;

import app.entities.payment.CreditCard;
import app.entities.payment.Vaucher;
import app.entities.base.BaseEntity;
import app.entities.person.Client;
import app.entities.trip.Trip;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bill")
public class Bill extends BaseEntity {

    private Date billDate;
    private Trip trip;
    private Client client;

    public Bill() {
    }

    public Bill(Date billDate, Trip trip, Client client) {
        this.billDate = billDate;
        this.trip = trip;
        this.client = client;
    }

    @Column(name = "bill_date")
    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(  name = "client_id", referencedColumnName = "id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}