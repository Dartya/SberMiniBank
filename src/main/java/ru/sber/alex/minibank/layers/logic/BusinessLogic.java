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
import ru.sber.alex.minibank.layers.services.commonservices.MailService;
import ru.sber.alex.minibank.layers.services.jpaservices.AccountService;
import ru.sber.alex.minibank.layers.services.jpaservices.ClientServiceImpl;
import ru.sber.alex.minibank.layers.services.jpaservices.OperationServiceImpl;

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

    @Autowired
    private AccountService accountService;

    @Autowired
    private MailService mailService;

    public int registerAcc(ClientDto client){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setLogin(client.getLogin());
        clientEntity.setPass(bCryptPasswordEncoder.encode(client.getPass()));
        clientEntity.setEmail(client.getEmail());
        clientEntity.setName(client.getName());
        clientEntity.setSurname(client.getSurname());
        clientEntity.setSecondName(client.getSecondName());

        //todo трнасформировать Entity в Dto
        return clientService.addClient(clientEntity);
    }

    public int makePush(TransactionDto transaction){
        final OperationEntity operationEntity = new OperationEntity();
        operationEntity.setAccountsId(transaction.getAccFrom());
        operationEntity.setDictOperationID(2);
        operationEntity.setSumm(transaction.getSumm());
        operationEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        //todo трнасформировать Entity в Dto
        return operationService.pushMoney(operationEntity, transaction.getLogin());
    }

    public int makePull(TransactionDto transaction){
        final OperationEntity operationEntity = new OperationEntity();
        operationEntity.setAccountsId(transaction.getAccFrom());
        operationEntity.setDictOperationID(3);
        operationEntity.setSumm(transaction.getSumm());
        operationEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        //todo трнасформировать Entity в Dto
        return operationService.pullMoney(operationEntity, transaction.getLogin());
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

        //todo трнасформировать Entity в Dto
        int result = operationService.transferMoney(operationTo, operationFrom, transaction.getLogin());

        if (result != -1){
            AccountEntity accountEntity = accountService.getById(transaction.getAccTo());

            final String message = "На Ваш счет №"+transaction.getAccTo()+
                    " поступил перевод средств со счета №"
                    +transaction.getAccFrom()+" на сумму "
                    +transaction.getSumm()+".\n\nВаш СбербМиниБанк.";

            mailService.send(accountEntity.getClient().getEmail(), "Перевод средств", message);
            return 1;
        }
        return -1;
    }

    //todo заменить Entity на Dto
    public Map<ClientEntity, List<OperationEntity>> getClientHistory(String clientLogin){

        return clientService.getClientOperationsMap(clientLogin);
    }
}
