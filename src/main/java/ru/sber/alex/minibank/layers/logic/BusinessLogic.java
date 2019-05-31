package ru.sber.alex.minibank.layers.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sber.alex.minibank.dto.ClientDto;
import ru.sber.alex.minibank.dto.TransactionDto;
import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.layers.services.ClientServiceImpl;
import ru.sber.alex.minibank.layers.services.OperationServiceImpl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessLogic {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private OperationServiceImpl operationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public int registerAcc(ClientDto client){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setLogin(client.getLogin());
        clientEntity.setPass(bCryptPasswordEncoder.encode(client.getPass()));
        clientEntity.setEmail(client.getEmail());
        clientEntity.setName(client.getName());
        clientEntity.setSurname(client.getSurname());
        clientEntity.setSecondName(client.getSecondName());
        return clientService.addClient(clientEntity);
    }

    public int makePush(TransactionDto transaction){
        final OperationEntity operationEntity = new OperationEntity();
        operationEntity.setAccountsId(transaction.getAccFrom());
        operationEntity.setDictOperationID(2);
        operationEntity.setSumm(transaction.getSumm());
        operationEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return operationService.pushMoney(operationEntity);
    }

    public int makePull(TransactionDto transaction){
        final OperationEntity operationEntity = new OperationEntity();
        operationEntity.setAccountsId(transaction.getAccFrom());
        operationEntity.setDictOperationID(3);
        operationEntity.setSumm(transaction.getSumm());
        operationEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return operationService.pullMoney(operationEntity);
    }

    public int makeTransfer(TransactionDto transaction){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //операция со стороны инициатора перевода
        final OperationEntity operationTo = new OperationEntity();
        operationTo.setAccountsId(transaction.getAccFrom());        //инициатор
        operationTo.setSeconAccountId(transaction.getAccTo());      //адресат
        operationTo.setDictOperationID(4);                          //перевод адресату
        operationTo.setSumm(transaction.getSumm());
        operationTo.setTimestamp(timestamp);

        //операция со стороны адресата перевода
        final OperationEntity operationFrom = new OperationEntity();
        operationFrom.setAccountsId(transaction.getAccTo());        //адресат
        operationFrom.setSeconAccountId(transaction.getAccFrom());  //инициатор
        operationFrom.setDictOperationID(5);                        //перевод от инициатора
        operationFrom.setSumm(transaction.getSumm());
        operationFrom.setTimestamp(timestamp);

        return operationService.transferMoney(operationTo, operationFrom);
    }

    public Map<ClientEntity, List<OperationEntity>> getClientHistory(String clientLogin){
        Map<ClientEntity, List<OperationEntity>> map = new HashMap<>();

        ClientEntity client = clientService.getClient(clientLogin);
        List<AccountEntity> clientAccounts = client.getAccounts();
        Integer accountId = clientAccounts.get(0).getId();
        List<OperationEntity> operations = operationService.findByAccountsId(accountId);
        map.put(client, operations);

        return map;
    }
}
