package ru.sber.alex.minibank.businesslogic.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.alex.minibank.businesslogic.dto.TransactionDto;
import ru.sber.alex.minibank.data.entities.OperationEntity;
import ru.sber.alex.minibank.data.jparepository.OperationRepoService;

import java.sql.Timestamp;

/**
 * Сервис контроллера вывода средств
 */
@Service
@Slf4j
public class PullService {

    @Autowired
    private OperationRepoService operationService;

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
}
