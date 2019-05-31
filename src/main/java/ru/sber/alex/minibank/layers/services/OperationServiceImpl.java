package ru.sber.alex.minibank.layers.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.repository.OperationRepo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final static int ERROR = -1;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OperationRepo operationRepo;

    @Autowired
    private AccountServiceImpl accountService;

    private AccountEntity getAccount(int id){
        return accountService.getById(id);
    }

    @Override
    public List<OperationEntity> findByAccountsId(Integer id){
        return operationRepo.findByAccountsId(id);
    }

    //достаем аккаунт из репозитория, прибавляем сумму из операции, сохраняем аккаунт и операцию
    @Transactional
    public int pushMoney(OperationEntity operationEntity){
        try {
            System.out.println("operationEntity acc id = "+operationEntity.getAccountsId());
            final AccountEntity accountEntity = getAccount(operationEntity.getAccountsId());
            System.out.println("accountEtity id = "+accountEntity.getId());
            accountEntity.setDeposit(accountEntity.getDeposit().add(operationEntity.getSumm()));
            entityManager.merge(accountEntity);
            entityManager.persist(operationEntity);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    //достаем аккаунт из репозитория, проверяем, не будет ли сумма меньше 0, вычитаем сумму из операции, сохраняем аккаунт и операцию
    @Transactional
    public int pullMoney(OperationEntity operationEntity){
        try {
            final AccountEntity accountEntity = getAccount(operationEntity.getAccountsId());
            if (accountEntity.getDeposit().subtract(operationEntity.getSumm()).doubleValue() >= 0.00) {
                accountEntity.setDeposit(accountEntity.getDeposit().subtract(operationEntity.getSumm()));
                entityManager.merge(accountEntity);
                entityManager.persist(operationEntity);
                return 1;
            }else return ERROR;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    //достаем аккаунт из репозитория, из которого осущ-ся перевод;
    //проверяем, не станет ли меньше 0;
    //достаем аккаунт из репозитория, в который осущ-ся перевод;
    //отнимаем у инициатора, прибавляем у адресата
    //сохраняем два аккаунта и операции
    @Transactional
    public int transferMoney(OperationEntity operationTo, OperationEntity operationFrom){
        try {
            final AccountEntity accountTo = getAccount(operationTo.getSeconAccountId());  //аккаунт адресата
            if (accountTo == null) return -1; //если аккаунта не существует

            final AccountEntity accountFrom = getAccount(operationTo.getAccountsId());    //аккаунт инициатора
            if (accountFrom.getDeposit().subtract(operationFrom.getSumm()).doubleValue() >= 0.00) {
                accountFrom.setDeposit(accountFrom.getDeposit().subtract(operationFrom.getSumm()));
                accountTo.setDeposit(accountTo.getDeposit().add(operationFrom.getSumm()));

                entityManager.merge(accountFrom);       //апдейт счета адресата
                entityManager.persist(operationFrom);   //запись о переводе со стороны адресата

                entityManager.merge(accountTo);         //апдейт счета инициатора
                entityManager.persist(operationTo);     //запись о переводе со стороны инициатора

                return 1;
            } else return ERROR;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
}
