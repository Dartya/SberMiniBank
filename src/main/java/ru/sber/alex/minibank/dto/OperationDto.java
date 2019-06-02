package ru.sber.alex.minibank.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class OperationDto {
    
    private int id;
    private int accountId;
    private Integer secondAccId;
    private int operationCode;
    private BigDecimal summ;
    private Timestamp timestamp;

    private List<ClientDto> clientDtos;
    private DictOperationDto dictOperationDto;

    public OperationDto() {
    }

    public OperationDto(int id, int accountId, Integer secondAccId, int operationCode, BigDecimal summ, Timestamp timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.secondAccId = secondAccId;
        this.operationCode = operationCode;
        this.summ = summ;
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

    public Integer getSecondAccId() {
        return secondAccId;
    }

    public void setSecondAccId(Integer secondAccId) {
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

    public List<ClientDto> getClientDtos() {
        return clientDtos;
    }

    public void setClientDtos(List<ClientDto> clientDtos) {
        this.clientDtos = clientDtos;
    }

    public BigDecimal getSumm() {
        return summ;
    }

    public void setSumm(BigDecimal summ) {
        this.summ = summ;
    }

    public DictOperationDto getDictOperationDto() {
        return dictOperationDto;
    }

    public void setDictOperationDto(DictOperationDto dictOperationDto) {
        this.dictOperationDto = dictOperationDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDto that = (OperationDto) o;
        return id == that.id &&
                accountId == that.accountId &&
                operationCode == that.operationCode &&
                Objects.equals(secondAccId, that.secondAccId) &&
                Objects.equals(summ, that.summ) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(clientDtos, that.clientDtos) &&
                Objects.equals(dictOperationDto, that.dictOperationDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, secondAccId, operationCode, summ, timestamp, clientDtos, dictOperationDto);
    }

    @Override
    public String toString() {
        return "OperationDto{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", secondAccId=" + secondAccId +
                ", operationCode=" + operationCode +
                ", summ=" + summ +
                ", timestamp=" + timestamp +
                ", clientDtos=" + clientDtos +
                ", dictOperationDto=" + dictOperationDto.getOperation() +
                '}';
    }
}
