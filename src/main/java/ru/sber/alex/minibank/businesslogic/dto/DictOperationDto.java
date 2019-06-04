package ru.sber.alex.minibank.businesslogic.dto;

import java.util.Objects;

/**
 * DTO сущности названия операции.
 */
public class DictOperationDto {
    private int id;
    private String operation;

    public DictOperationDto() {
    }

    public DictOperationDto(int id, String operation) {
        this.id = id;
        this.operation = operation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictOperationDto that = (DictOperationDto) o;
        return id == that.id &&
                Objects.equals(operation, that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation);
    }

    @Override
    public String toString() {
        return "DictOperationDto{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                '}';
    }
}
