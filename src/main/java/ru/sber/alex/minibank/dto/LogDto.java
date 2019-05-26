package ru.sber.alex.minibank.dto;

import java.util.Objects;

public class LogDto {
    
    private int clientId;
    private int operationId;

    public LogDto() {
    }

    public LogDto(int clientId, int operationId) {
        this.clientId = clientId;
        this.operationId = operationId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogDto logDto = (LogDto) o;
        return clientId == logDto.clientId &&
                operationId == logDto.operationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, operationId);
    }

    @Override
    public String toString() {
        return "LogDto{" +
                "clientId=" + clientId +
                ", operationId=" + operationId +
                '}';
    }
}
