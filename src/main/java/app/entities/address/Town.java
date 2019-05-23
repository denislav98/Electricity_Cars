package app.entities.address;

import app.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "town")
public class Town extends BaseEntity {

    private String townName;

    public Town() {
    }

    @Column(name = "town_name", nullable = false)
    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    @Override
    public String toString() {
        return String.format(" %s " , townName);
    }

}
