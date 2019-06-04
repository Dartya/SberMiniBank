package ru.sber.alex.minibank.data.jparepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.data.entities.AccountEntity;
import ru.sber.alex.minibank.data.entities.OperationEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Реализация сервиса сущности БД "Операция".
 */
@Repository
@Slf4j
public class OperationRepoService {

    /**
     * Код завершения операции - ошибка
     */
    private final static int ERROR = -1;
    /**
     * Код завершения операции - успех
     */
    private final static int OK = 1;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AccountRepository accountRepository;

    private AccountEntity getAccount(int id){
        return accountRepository.getById(id);
    }

    public List<OperationEntity> findByAccountsId(Integer id){
        return operationRepository.findByAccountsId(id);
    }

    /**
     * Достает счет из репозитория, прибавляет сумму из операции, сохраняет аккаунт и операцию
     * @param operationEntity Сущность БД "Операция"
     * @return код успешности операции: 1 - ОК, -1 - ошибка.
     */
    @Transactional
    public int pushMoney(OperationEntity operationEntity){
        try {
            System.out.println("operationEntity acc id = "+operationEntity.getAccountsId());
            final AccountEntity accountEntity = getAccount(operationEntity.getAccountsId());
            System.out.println("accountEtity id = "+accountEntity.getId());
            accountEntity.setDeposit(accountEntity.getDeposit().add(operationEntity.getSumm()));

            operationEntity.addClient(accountEntity.getClient());

            entityManager.merge(accountEntity);
            return OK;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * Достает аккаунт из репозитория, проверяет, не будет ли сумма меньше 0, вычитает сумму из операции, сохраняет аккаунт и операцию.
     * @param operationEntity Сущность БД "Операция"
     * @param login логин текущего пользователя
     * @return код успешности операции: 1 - ОК, -1 - ошибка.
     */
    @Transactional
    public int pullMoney(OperationEntity operationEntity, String login){
        try {
            final AccountEntity accountEntity = getAccount(operationEntity.getAccountsId());
            if (accountEntity.getDeposit().subtract(operationEntity.getSumm()).doubleValue() >= 0.00 &&
                    accountEntity.getClient().getLogin().equals(login)) {

                accountEntity.setDeposit(accountEntity.getDeposit().subtract(operationEntity.getSumm()));
                operationEntity.addClient(accountEntity.getClient());
                entityManager.merge(accountEntity);
                return OK;
            }else return ERROR;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * Выполняет транзакцию: переводит средства с однгого счета на другой счет
     * Достает из репозитория счет, из которого осуществляется перевод;
     * проверяет, не станет ли депозит меньше 0 после окончания операции;
     * достает из репозитория счет, в который осущетсвляется перевод;
     * вычитает из счета инициатора перевода, прибавляет к счету адресата сумму;
     * обновляет два счета и добавляет две операции в БД.
     * @param operationTo Операция адресата
     * @param operationFrom Операция инициатора
     * @param login логин инициатора для подтверждения валидности операции
     * @return код завершения операции
     */
    @Transactional
    public int transferMoney(OperationEntity operationTo, OperationEntity operationFrom, String login){
        try {
            final AccountEntity accountTo = getAccount(operationTo.getSeconAccountId());  //аккаунт адресата
            if (accountTo == null) return ERROR; //если аккаунта не существует - ошибка

            final AccountEntity accountFrom = getAccount(operationTo.getAccountsId());    //аккаунт инициатора
            if (accountFrom.getDeposit().subtract(operationFrom.getSumm()).doubleValue() >= 0.00 &&
                    accountFrom.getClient().getLogin().equals(login)) {
                accountFrom.setDeposit(accountFrom.getDeposit().subtract(operationFrom.getSumm()));
                accountTo.setDeposit(accountTo.getDeposit().add(operationFrom.getSumm()));

                entityManager.merge(accountFrom);                   //апдейт счета адресата
                operationFrom.addClient(accountFrom.getClient());   //запись о переводе со стороны адресата

                entityManager.merge(accountTo);                     //апдейт счета инициатора
                operationTo.addClient(accountTo.getClient());       //запись о переводе со стороны инициатора

                return OK;
            } else return ERROR;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
}
