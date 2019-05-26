package ru.sber.alex.minibank.entities;

import javax.persistence.*;
import java.util.Objects;
/*
@Entity
@Table(name="logs")*/
public class LogsEntity {
/*

    @Column(name="clients_id")
    private int clientsId;

    @Column(name="operation_id")
    private int operationId;

    public LogsEntity() {
    }

    public LogsEntity(int clientsId, int operationId) {
        this.clientsId = clientsId;
        this.operationId = operationId;
    }

    public int getClientsId() {
        return clientsId;
    }

    public void setClientsId(int clientsId) {
        this.clientsId = clientsId;
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
        LogsEntity logsEntity = (LogsEntity) o;
        return clientsId == logsEntity.clientsId &&
                operationId == logsEntity.operationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientsId, operationId);
    }

    @Override
    public String toString() {
        return "LogsEntity{" +
                "clientsId=" + clientsId +
                ", operationId=" + operationId +
                '}';
    }
*/
}
