package ru.sber.alex.minibank.layers.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sber.alex.minibank.dto.ClientDto;
import ru.sber.alex.minibank.dto.ClientOperationDto;
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

/**
 * Основной класс бизнес-логики приложения - связывается с сервисами сущностей БД.
 * В атомарной версии приложения готовит объекты сущностей БД и передает их как параметры в соответствующие сервисы.
 * В микросервисной версии обменивается DTO с другими сервисами.
 */
@Service
public class BusinessLogic {
/* Оставляю здесь до форка на микросервисную архитектуру
    @Autowired
    private RestTemplate restTemplate;
*/
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

    /**
     * Готовит данные для передачи в сервис клиентов для последующей регистрации клиента.
     * @param client ДТО - заполненная форма регистрации
     * @return код успешности операции: 1 - ОК, -1 - ошибка.
     */
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

    /**
     * Готовит данные для передачи в сервис операций, часть общей логики пополнения счета.
     * @param transaction ДТО - заполненная форма транзакции.
     * @return код успешности операции: 1 - ОК, -1 - ошибка.
     */
    public int makePush(TransactionDto transaction){
        final OperationEntity operationEntity = new OperationEntity();
        operationEntity.setAccountsId(transaction.getAccFrom());
        operationEntity.setDictOperationID(2);
        operationEntity.setSumm(transaction.getSumm());
        operationEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return operationService.pushMoney(operationEntity);
    }

    /**
     * Готовит данные для передачи в сервис операций, часть общей логики выведения средств со счета.
     * @param transaction ДТО - заполненная форма транзакции.
     * @return код успешности операции: 1 - ОК, -1 - ошибка.
     */
    public int makePull(TransactionDto transaction){
        final OperationEntity operationEntity = new OperationEntity();
        operationEntity.setAccountsId(transaction.getAccFrom());
        operationEntity.setDictOperationID(3);
        operationEntity.setSumm(transaction.getSumm());
        operationEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return operationService.pullMoney(operationEntity, transaction.getLogin());
    }

    /**
     * Готовит данные для передачи в сервис операций, часть общей логики перевода средств с одного счета на другой.
     * Отправляет письмо на e-mail пользователя-адресату перевода.
     * @param transaction ДТО - заполненная форма транзакции.
     * @return код успешности операции: 1 - ОК, -1 - ошибка.
     */
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

        int result = operationService.transferMoney(operationTo, operationFrom, transaction.getLogin());

        if (result != -1){
            AccountEntity accountEntity = accountService.getById(transaction.getAccTo());

            final String message = "На Ваш счет №"+transaction.getAccTo()+
                    " поступил перевод средств со счета №"
                    +transaction.getAccFrom()+" на сумму "
                    +transaction.getSumm()+".\n\nВаш СбербМиниБанк." +
                    "\n\n(Яндекс, это не спам!!!)";

            mailService.send(accountEntity.getClient().getEmail(), "Перевод средств", message);
            return 1;
        }
        return -1;
    }

    /**
     * Запрашивает историю операций клиента с указанным в параметре логином.
     * @param clientLogin логин пользователя
     * @return List с заполненными ДТО ClientOperationDto
     */
    public List<ClientOperationDto> getClientHistory(String clientLogin){
        return clientService.getClientOperationsDto(clientLogin);
    }
}
