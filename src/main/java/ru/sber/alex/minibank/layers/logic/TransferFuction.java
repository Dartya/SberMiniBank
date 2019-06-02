package ru.sber.alex.minibank.layers.logic;

import ru.sber.alex.minibank.dto.*;
import ru.sber.alex.minibank.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержит функции конвертации объектов из Entity в DTO и из DTO в Entity
 */
public class TransferFuction {

    /**
     * Переводит ClientEntity в ClientDto
     * @param clientEntity
     * @return заполненный ClientDto
     */
    public static ClientDto clientEntityToDto(ClientEntity clientEntity){
        ClientDto clientDto = new ClientDto(
                clientEntity.getLogin(),
                clientEntity.getPass(),
                clientEntity.getEmail(),
                clientEntity.getName(),
                clientEntity.getSurname(),
                clientEntity.getSecondName());

        clientDto.setId(clientEntity.getId());

        List<AccountDto> accountDtos = new ArrayList<>();
        List<AccountEntity> accountEntities = clientEntity.getAccounts();
        if (accountEntities != null) {
            for (AccountEntity accountEntity : accountEntities) {
                accountDtos.add(accountEntityToDto(accountEntity));
            }
        }
        clientDto.setAccountDtos(accountDtos);

        return clientDto;
    }

    /**
     * Переводит ClientDto в ClientEntity
     * @param clientDto
     * @return заполненный ClientEntity
     */
    public static ClientEntity clientDtoToEntity(ClientDto clientDto){
        ClientEntity clientEntity = new ClientEntity(
                clientDto.getId(),
                clientDto.getLogin(),
                clientDto.getPass(),
                clientDto.getEmail(),
                clientDto.getName(),
                clientDto.getSurname(),
                clientDto.getSecondName()
        );

        return clientEntity;
    }

    /**
     * Переводит AccountEntity в AccountDto
     * @param accountEntity
     * @return заполненный AccountDto
     */
    public static AccountDto accountEntityToDto(AccountEntity accountEntity){
        AccountDto accountDto = new AccountDto(
                accountEntity.getId(),
                accountEntity.getClientId(),
                accountEntity.getCurrencyID(),
                accountEntity.getDeposit()
        );

        ClientDto clientDto = new ClientDto(
                accountEntity.getClient().getLogin(),
                accountEntity.getClient().getPass(),
                accountEntity.getClient().getEmail(),
                accountEntity.getClient().getName(),
                accountEntity.getClient().getSurname(),
                accountEntity.getClient().getSecondName()
        );
        clientDto.setId(accountEntity.getClient().getId());
        accountDto.setClient(clientDto);
        accountDto.setCurrency(currencyEntityToDto(accountEntity.getCurrency()));

        return accountDto;
    }

    /**
     * Переводит AccountDto в AccountEntity
     * @param accountDto
     * @return заполненный AccountEntity
     */
    public static AccountEntity accountDtoToEntity(AccountDto accountDto){
        AccountEntity accountEntity = new AccountEntity(
                accountDto.getClientId(),
                accountDto.getCurrencyID(),
                accountDto.getDeposit()
        );

        ClientEntity clientEntity = new ClientEntity(
                accountDto.getClient().getId(),
                accountDto.getClient().getLogin(),
                accountDto.getClient().getPass(),
                accountDto.getClient().getEmail(),
                accountDto.getClient().getName(),
                accountDto.getClient().getSurname(),
                accountDto.getClient().getSecondName()
        );
        accountEntity.setId(accountDto.getId());
        accountEntity.setClient(clientEntity);

        return accountEntity;
    }

    /**
     * Переводит OperationEntity в OperationDto
     * @param operationEntity
     * @return заполненный OperationDto
     */
    public static OperationDto operationEntityToDto(OperationEntity operationEntity){
        Integer secAccId;

        if (operationEntity.getSeconAccountId() != null){
            secAccId = operationEntity.getSeconAccountId();
        }else{ secAccId = null;}

        OperationDto operationDto = new OperationDto(
                operationEntity.getId(),
                operationEntity.getAccountsId(),
                secAccId,
                operationEntity.getDictOperationID(),
                operationEntity.getSumm(),
                operationEntity.getTimestamp()
        );
        DictOperationDto dictOperationDto = dictOperationEntityToDto(operationEntity.getDictOperation());
        operationDto.setDictOperationDto(dictOperationDto);

        List<ClientDto> clientDtos = new ArrayList<>();
        List<ClientEntity> clientEntities = operationEntity.getClients();
        if (clientEntities != null) {
            for (ClientEntity clientEntity : clientEntities) {
                clientDtos.add(clientEntityToDto(clientEntity));
            }
        }
        operationDto.setClientDtos(clientDtos);

        return operationDto;
    }

    /**
     * Переводит OperationDto в OperationEntity
     * @param operationDto
     * @return заполненный OperationEntity
     */
    public static OperationEntity operationDtoToEntity(OperationDto operationDto){
        OperationEntity operationEntity = new OperationEntity(
                operationDto.getAccountId(),
                operationDto.getSecondAccId(),
                operationDto.getOperationCode(),
                operationDto.getSumm(),
                operationDto.getTimestamp()
        );
        List<ClientEntity> clientEntities = new ArrayList<>();
        List<ClientDto> clientDtos = operationDto.getClientDtos();
        for (ClientDto clientDto: clientDtos) {
            clientEntities.add(clientDtoToEntity(clientDto));
        }
        operationEntity.setClients(clientEntities);

        return operationEntity;
    }

    /**
     * Переводит DictOperationsEntity в DictOperationDto
     * @param entity
     * @return заполненный DictOperationDto
     */
    public static DictOperationDto dictOperationEntityToDto(DictOperationsEntity entity){
        return new DictOperationDto(entity.getId(), entity.getOperation());
    }

    /**
     * Переводит CurrenciesEntity в CurrencyDto
     * @param entity
     * @return заполненный CurrencyDto
     */
    public static CurrencyDto currencyEntityToDto(CurrenciesEntity entity){
        CurrencyDto currencyDto = new CurrencyDto(
                entity.getId(),
                entity.getCurrencyCode(),
                entity.getCurrency());

        return currencyDto;
    }
}
