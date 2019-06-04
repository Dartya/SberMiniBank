package ru.sber.alex.minibank.businesslogic.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.alex.minibank.businesslogic.dto.TransactionDto;
import ru.sber.alex.minibank.data.entities.AccountEntity;
import ru.sber.alex.minibank.data.entities.OperationEntity;
import ru.sber.alex.minibank.data.jparepository.AccountRepository;
import ru.sber.alex.minibank.data.jparepository.OperationRepoService;

import java.sql.Timestamp;


@Service
@Slf4j
public class AccountService {
/* Оставляю здесь до форка на микросервисную архитектуру
    @Autowired
    private RestTemplate restTemplate;
*/
    @Autowired
    private OperationRepoService operationService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailService mailService;

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
            AccountEntity accountEntity = accountRepository.getById(transaction.getAccTo());

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
}
