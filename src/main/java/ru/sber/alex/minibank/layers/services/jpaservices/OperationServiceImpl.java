package ru.sber.alex.minibank.layers.services.jpaservices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.repository.OperationRepo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final static int ERROR = -1;
    private final static int OK = 1;

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
    //todo заменить Entity на Dto, затем трансформировать на входе, далее по той же логике
    @Transactional
    public int pushMoney(OperationEntity operationEntity, String login){
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
    //достаем аккаунт из репозитория, проверяем, не будет ли сумма меньше 0, вычитаем сумму из операции, сохраняем аккаунт и операцию
    //todo заменить Entity на Dto, затем трансформировать на входе, далее по той же логике
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
    //достаем аккаунт из репозитория, из которого осущ-ся перевод;
    //проверяем, не станет ли меньше 0;
    //достаем аккаунт из репозитория, в который осущ-ся перевод;
    //отнимаем у инициатора, прибавляем у адресата
    //сохраняем два аккаунта и операции
    //todo заменить Entity на Dto, затем трансформировать на входе, далее по той же логике
    @Transactional
    public int transferMoney(OperationEntity operationTo, OperationEntity operationFrom, String login){
        try {
            final AccountEntity accountTo = getAccount(operationTo.getSeconAccountId());  //аккаунт адресата
            if (accountTo == null) return ERROR; //если аккаунта не существует

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
