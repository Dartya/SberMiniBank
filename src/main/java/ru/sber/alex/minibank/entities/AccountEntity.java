package ru.sber.alex.minibank.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="client_id")
    private int clientId;
    @Column(name="currency_id")
    private int currencyID;
    private BigDecimal deposit;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name="client_id", insertable = false, updatable = false)
    private ClientEntity client;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name="currency_id", insertable = false, updatable = false)
    private CurrenciesEntity currency;

    @OneToMany(mappedBy="accountId", fetch=FetchType.EAGER)
    private List<OperationEntity> operationsAccId;

    @OneToMany(mappedBy="secondAccountId", fetch=FetchType.EAGER)
    private List<OperationEntity> operationsSecAccId;


    public AccountEntity() {
    }

    public AccountEntity(int clientId, int currencyID, BigDecimal deposit) {
        this.clientId = clientId;
        this.currencyID = currencyID;
        this.deposit = deposit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.currencyID = currencyID;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return id == that.id &&
                clientId == that.clientId &&
                currencyID == that.currencyID &&
                Objects.equals(deposit, that.deposit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, currencyID, deposit);
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", currencyID=" + currencyID +
                ", deposit=" + deposit +
                '}';
    }
}
