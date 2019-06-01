package ru.sber.alex.minibank.dto;

import ru.sber.alex.minibank.entities.CurrenciesEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AccountDto {

    private int id;
    private int clientId;
    private int currencyID;
    private BigDecimal deposit;

    private ClientDto client;
    private CurrenciesEntity currency;
    private List<OperationDto> operationsAccId;
    private List<OperationDto> operationsSecAccId;

    public AccountDto() {
    }

    public AccountDto(int id, int clientId, int currencyID, BigDecimal deposit) {
        this.id = id;
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

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public CurrenciesEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrenciesEntity currency) {
        this.currency = currency;
    }

    public List<OperationDto> getOperationsAccId() {
        return operationsAccId;
    }

    public void setOperationsAccId(List<OperationDto> operationsAccId) {
        this.operationsAccId = operationsAccId;
    }

    public List<OperationDto> getOperationsSecAccId() {
        return operationsSecAccId;
    }

    public void setOperationsSecAccId(List<OperationDto> operationsSecAccId) {
        this.operationsSecAccId = operationsSecAccId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return id == that.id &&
                clientId == that.clientId &&
                currencyID == that.currencyID &&
                Objects.equals(deposit, that.deposit) &&
                Objects.equals(client, that.client) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(operationsAccId, that.operationsAccId) &&
                Objects.equals(operationsSecAccId, that.operationsSecAccId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, currencyID, deposit, client, currency, operationsAccId, operationsSecAccId);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", currencyID=" + currencyID +
                ", deposit=" + deposit +
                ", client=" + client +
                ", currency=" + currency +
                ", operationsAccId=" + operationsAccId +
                ", operationsSecAccId=" + operationsSecAccId +
                '}';
    }
}
