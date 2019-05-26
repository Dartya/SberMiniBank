package ru.sber.alex.minibank.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="DictOperationsEntity")
public class DictOperationsEntity {

    @Id
    private int id;
    private String operation;

    public DictOperationsEntity() {
    }

    public DictOperationsEntity(int id, String operation) {
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
        DictOperationsEntity that = (DictOperationsEntity) o;
        return id == that.id &&
                Objects.equals(operation, that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation);
    }

    @Override
    public String toString() {
        return "DictOperationsEntity{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                '}';
    }
}
