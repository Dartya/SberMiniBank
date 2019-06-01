package ru.sber.alex.minibank.layers.services.jpaservices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.repository.ClientRepo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final static int ERROR = -1;
    private final static int OK = 1;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private OperationService operationService;

    //потом нужно перенести в сервис пользователей
    @Override
    public ClientEntity getClient(String login) {
        return clientRepo.findByLogin(login);
    }

    //todo заменить Entity на Dto, затем трансформировать на входе, далее по той же логике
    @Transactional
    public int addClient(ClientEntity client){
        try{
            entityManager.persist(client);
            entityManager.flush();
            int newClientId = client.getId();
            System.out.println("id of an added client = "+newClientId);

            final AccountEntity accountEntity = new AccountEntity(newClientId, 1, new BigDecimal(0));
            entityManager.persist(accountEntity);
            entityManager.flush();
            final OperationEntity createClientOperationEntity = new OperationEntity(
                    accountEntity.getId(),
                    null,
                    1,
                    new BigDecimal(0),
                    new Timestamp(System.currentTimeMillis())
            );

            createClientOperationEntity.addClient(client);
            entityManager.persist(createClientOperationEntity);
            entityManager.flush();
            return OK;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    //todo заменить Entity на Dto, затем трансформировать на входе, далее по той же логике
    public  Map<ClientEntity, List<OperationEntity>> getClientOperationsMap(String clientLogin){
        Map<ClientEntity, List<OperationEntity>> map = new HashMap<>();

        ClientEntity client = getClient(clientLogin);
        List<AccountEntity> clientAccounts = client.getAccounts();

        for (int i = 0; i < clientAccounts.size(); i++) {
            Integer accountId = clientAccounts.get(i).getId();
            List<OperationEntity> operations = operationService.findByAccountsId(accountId);
            map.put(client, operations);
        }

        return map;
    }
}
