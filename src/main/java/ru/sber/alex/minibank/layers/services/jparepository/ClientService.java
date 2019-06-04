package ru.sber.alex.minibank.layers.services.jparepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.dto.ClientOperationDto;
import ru.sber.alex.minibank.dto.OperationDto;
import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.entities.ClientEntity;
import ru.sber.alex.minibank.entities.OperationEntity;
import ru.sber.alex.minibank.layers.logic.TransferFuction;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса сущности БД "Счет".
 */
@Repository
@Slf4j
public class ClientService {

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
    private ClientRepo clientRepo;

    @Autowired
    private OperationRepo operationRepo;

    /**
     * Достает из репозитория клиента с указанным логином.
     * @param login логин пользователя.
     * @return сущность БД "Клиент"
     */

    public ClientEntity getClient(String login) {
        return clientRepo.findByLogin(login);
    }

    /**
     * Выполняет транзакцию регистрации клиента.
     * Регистрирует клиента, берет его id, создает ему дефолтный рублевый счет с указанием владельца, записывает операцию создания счета.
     * @param client Сущность БД "Клиент"
     * @return код успешности операции: 1 - ОК, -1 - ошибка.
     */
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

    /**
     * Ищет все связанные с клиентом операции для вывода истории операций в личном кабинете.
     * @param clientLogin Сущность БД "Клиент"
     * @return List с заполненными объектами ClientOperationDto - парами ДТО "Клиент" и List его операций.
     */
    public List<ClientOperationDto> getClientOperationsDto(String clientLogin){
        List<ClientOperationDto> clientOperationDtoList = new ArrayList<>();
        List<OperationDto> operationDtos = new ArrayList<>();

        ClientEntity client = getClient(clientLogin);
        List<AccountEntity> clientAccounts = client.getAccounts();

        for (int i = 0; i < clientAccounts.size(); i++) {
            Integer accountId = clientAccounts.get(i).getId();
            List<OperationEntity> operations = operationRepo.findByAccountsId(accountId);

            for (OperationEntity operationEntity : operations) {
                operationDtos.add(TransferFuction.operationEntityToDto(operationEntity));
            }
            clientOperationDtoList.add(new ClientOperationDto(TransferFuction.clientEntityToDto(client), operationDtos));
        }
        return clientOperationDtoList;
    }
}
