package ru.sber.alex.minibank.businesslogic.dto;

import java.util.Objects;

/**
 * DTO сущности валюты.
 */
public class CurrencyDto {

    private int id;
    private String currencyCode;
    private String currency;

    public CurrencyDto() {
    }

    public CurrencyDto(int id, String currencyCode, String currency) {
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
        CurrencyDto that = (CurrencyDto) o;
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
        return "CurrencyDto{" +
                "id=" + id +
                ", currencyCode='" + currencyCode + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
