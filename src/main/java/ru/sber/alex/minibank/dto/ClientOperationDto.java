package ru.sber.alex.minibank.dto;

import java.util.Objects;

public class ClientOperationDto {

    private ClientDto clientDto;
    private OperationDto operationDto;

    public ClientOperationDto() {
    }

    public ClientOperationDto(ClientDto clientDto, OperationDto operationDto) {
        this.clientDto = clientDto;
        this.operationDto = operationDto;
    }

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public OperationDto getOperationDto() {
        return operationDto;
    }

    public void setOperationDto(OperationDto operationDto) {
        this.operationDto = operationDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientOperationDto that = (ClientOperationDto) o;
        return Objects.equals(clientDto, that.clientDto) &&
                Objects.equals(operationDto, that.operationDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientDto, operationDto);
    }

    @Override
    public String toString() {
        return "ClientOperationDto{" +
                "clientDto=" + clientDto +
                ", operationDto=" + operationDto +
                '}';
    }
}
