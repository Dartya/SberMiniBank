package ru.sber.alex.minibank.businesslogic.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO транзакции.
 */
public class TransactionDto {

    private int accFrom;
    private BigDecimal summ;
    private int accTo;
    private int operationCode;

    private String login;

    public TransactionDto() {
    }

    public TransactionDto(int accFrom, BigDecimal summ, int accTo, int operationCode) {
        this.accFrom = accFrom;
        this.summ = summ;
        this.accTo = accTo;
        this.operationCode = operationCode;
    }

    public int getAccFrom() {
        return accFrom;
    }

    public void setAccFrom(int accFrom) {
        this.accFrom = accFrom;
    }

    public BigDecimal getSumm() {
        return summ;
    }

    public void setSumm(BigDecimal summ) {
        this.summ = summ;
    }

    public int getAccTo() {
        return accTo;
    }

    public void setAccTo(int accTo) {
        this.accTo = accTo;
    }

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return accFrom == that.accFrom &&
                accTo == that.accTo &&
                operationCode == that.operationCode &&
                Objects.equals(summ, that.summ) &&
                Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accFrom, summ, accTo, operationCode, login);
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "accFrom=" + accFrom +
                ", summ=" + summ +
                ", accTo=" + accTo +
                ", operationCode=" + operationCode +
                ", login='" + login + '\'' +
                '}';
    }
}
