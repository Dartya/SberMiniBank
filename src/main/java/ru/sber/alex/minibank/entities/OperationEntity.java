package ru.sber.alex.minibank.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="operations")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="accounts_id")
    private int accountsId;
    @Column(name="secon_account_id")
    private int seconAccountId;
    @Column(name="dict_operation_id")
    private int dictOperationID;
    private Timestamp timestamp;

    @ManyToMany
    @JoinTable (name="logs",
            joinColumns=@JoinColumn (name="operation_id"),
            inverseJoinColumns=@JoinColumn(name="clients_id"))
    private List<OperationEntity> clients;

    public OperationEntity() {
    }

    public OperationEntity(int accountsId, int seconAccountId, int dictOperationID, Timestamp timestamp) {
        this.accountsId = accountsId;
        this.seconAccountId = seconAccountId;
        this.dictOperationID = dictOperationID;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountsId() {
        return accountsId;
    }

    public void setAccountsId(int accountsId) {
        this.accountsId = accountsId;
    }

    public int getSeconAccountId() {
        return seconAccountId;
    }

    public void setSeconAccountId(int seconAccountId) {
        this.seconAccountId = seconAccountId;
    }

    public int getDictOperationID() {
        return dictOperationID;
    }

    public void setDictOperationID(int dictOperationID) {
        this.dictOperationID = dictOperationID;
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
        OperationEntity that = (OperationEntity) o;
        return id == that.id &&
                accountsId == that.accountsId &&
                seconAccountId == that.seconAccountId &&
                dictOperationID == that.dictOperationID &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountsId, seconAccountId, dictOperationID, timestamp);
    }

    @Override
    public String toString() {
        return "OperationEntity{" +
                "id=" + id +
                ", accountsId=" + accountsId +
                ", seconAccountId=" + seconAccountId +
                ", dictOperationID=" + dictOperationID +
                ", timestamp=" + timestamp +
                '}';
    }
}
