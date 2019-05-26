package ru.sber.alex.minibank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="currencies")
public class CurrenciesEntity {

    @Id
    private int id;
    @Column(name="currency_code")
    private String currencyCode;
    private String currency;

    public CurrenciesEntity() {
    }

    public CurrenciesEntity(int id, String currencyCode, String currency) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrenciesEntity that = (CurrenciesEntity) o;
        return id == that.id &&
                Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyCode, currency);
    }

    @Override
    public String toString() {
        return "CurrenciesEntity{" +
                "id=" + id +
                ", currencyCode='" + currencyCode + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
