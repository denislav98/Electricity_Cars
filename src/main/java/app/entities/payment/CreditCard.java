package app.entities.payment;

import app.entities.base.BaseEntity;
import app.entities.bill.Bill;
import app.entities.person.Client;

import javax.persistence.*;

@Entity
@Table(name = "credit_card")
public class CreditCard extends BaseEntity {

    private String cardNumber ;
    private String cardType;
    private String expirationMonth;
    private String expirationYear;
    private Client client;

    public CreditCard() {

    }

    public CreditCard(String cardNumber, String cardType, String expirationMonth, String expirationYear, Client client) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.client = client;
    }

    @Column(name = "card_number", length = 10, nullable = false, unique = true)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Column(name = "car_type", length = 10, nullable = false)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
