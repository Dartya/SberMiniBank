package ru.sber.alex.minibank.data.jparepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.businesslogic.dto.ClientOperationDto;
import ru.sber.alex.minibank.businesslogic.dto.OperationDto;
import ru.sber.alex.minibank.data.entities.AccountEntity;
import ru.sber.alex.minibank.data.entities.ClientEntity;
import ru.sber.alex.minibank.data.entities.OperationEntity;
import ru.sber.alex.minibank.businesslogic.logic.TransferConverter;

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
public class ClientRepoService {

    /**
     * Код завершения операции - ошибка
     */
    private final static int ERROR = -1;
    /**
     * Код завершения операции - успех
     */
    private final static int OK = 1;

    private final EntityManager entityManager;

    private final ClientRepository clientRepository;

    private final OperationRepository operationRepository;

    public ClientRepoService(EntityManager entityManager, ClientRepository clientRepository, OperationRepository operationRepository) {
        this.entityManager = entityManager;
        this.clientRepository = clientRepository;
        this.operationRepository = operationRepository;
    }

    /**
     * Достает из репозитория клиента с указанным логином.
     * @param login логин пользователя.
     * @return сущность БД "Клиент"
     */

    private ClientEntity getClient(String login) {
        return clientRepository.findByLogin(login);
    }

    /**
     * Выполняет транзакцию регистрации клиента.
     * Регистрирует клиента, берет его id, создает ему дефолтный рублевый счет с указанием владельца, записывает операцию создания счета.
     * @param client Сущность БД "Клиент"
     * @return код успешности операции: 1 - ОК, -1 - общая ошибка, -2 - ошибка по логину, -3 - ошибка по e-mail.
     */
    @Transactional
    public int addClient(ClientEntity client){

        try{
            ClientEntity clientEntity1 = clientRepository.findByLogin(client.getLogin());
            ClientEntity clientEntity2 = clientRepository.findByEmail(client.getEmail());

            if (clientEntity1 != null && clientEntity1.getLogin().equals(client.getLogin())) return -2;
            if (clientEntity2 != null && clientEntity2.getEmail().equals(client.getEmail())) return -3;

            entityManager.persist(client);
            entityManager.flush();
            int newClientId = client.getId();

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
            List<OperationEntity> operations = operationRepository.findByAccountsId(accountId);

            for (OperationEntity operationEntity : operations) {
                operationDtos.add(TransferConverter.operationEntityToDto(operationEntity));
            }
            clientOperationDtoList.add(new ClientOperationDto(TransferConverter.clientEntityToDto(client), operationDtos));
        }
        return clientOperationDtoList;
    }
}
