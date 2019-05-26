package ru.sber.alex.minibank.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class OperationDto {
    
    private int id;
    private int accountId;
    private int secondAccId;
    private int operationCode;
    private Timestamp timestamp;

    public OperationDto() {
    }

    public OperationDto(int id, int accountId, int secondAccId, int operationCode, Timestamp timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.secondAccId = secondAccId;
        this.operationCode = operationCode;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getSecondAccId() {
        return secondAccId;
    }

    public void setSecondAccId(int secondAccId) {
        this.secondAccId = secondAccId;
    }

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDto that = (OperationDto) o;
        return id == that.id &&
                accountId == that.accountId &&
                secondAccId == that.secondAccId &&
                operationCode == that.operationCode &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, secondAccId, operationCode, timestamp);
    }

    @Override
    public String toString() {
        return "OperationDto{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", secondAccId=" + secondAccId +
                ", operationCode=" + operationCode +
                ", timestamp=" + timestamp +
                '}';
    }
}
