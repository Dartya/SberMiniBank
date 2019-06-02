package ru.sber.alex.minibank.dto;

import java.util.List;
import java.util.Objects;

/**
 * DTO объекта клиент-операция.
 */
public class ClientOperationDto {

    private ClientDto clientDto;
    private List<OperationDto> operationDto;

    public ClientOperationDto() {
    }

    public ClientOperationDto(ClientDto clientDto, List<OperationDto> operationDto) {
        this.clientDto = clientDto;
        this.operationDto = operationDto;
    }

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public List<OperationDto> getOperationDto() {
        return operationDto;
    }

    public void setOperationDto(List<OperationDto> operationDto) {
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
