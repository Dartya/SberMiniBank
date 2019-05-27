package ru.sber.alex.minibank.layers.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.repository.ClientRepo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Repository
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final static int ERROR = -1;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ClientRepo clientRepo;

    //потом нужно перенести в сервис пользователей
    @Override
    public ClientEntity getClient(String login) {
        ClientEntity client = clientRepo.findByLogin(login);
        return client;
    }

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
                    new Timestamp(System.currentTimeMillis())
            );
            entityManager.persist(createClientOperationEntity);
            entityManager.flush();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
}
