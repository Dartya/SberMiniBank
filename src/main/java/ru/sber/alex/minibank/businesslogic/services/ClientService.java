package ru.sber.alex.minibank.businesslogic.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.alex.minibank.businesslogic.dto.ClientDto;
import ru.sber.alex.minibank.businesslogic.dto.ClientOperationDto;
import ru.sber.alex.minibank.data.entities.ClientEntity;
import ru.sber.alex.minibank.data.jparepository.ClientRepoService;

import java.util.List;

/**
 * Основной класс бизнес-логики приложения - связывается с сервисами сущностей БД.
 * В атомарной версии приложения готовит объекты сущностей БД и передает их как параметры в соответствующие сервисы.
 * В микросервисной версии обменивается DTO с другими сервисами.
 */
@Service
@Slf4j
public class ClientService {
/* Оставляю здесь до форка на микросервисную архитектуру
    @Autowired
    private RestTemplate restTemplate;
*/
    @Autowired
    private ClientRepoService clientRepoService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

        return clientRepoService.addClient(clientEntity);
    }

    /**
     * Запрашивает историю операций клиента с указанным в параметре логином.
     * @param clientLogin логин пользователя
     * @return List с заполненными ДТО ClientOperationDto
     */
    public List<ClientOperationDto> getClientHistory(String clientLogin){
        return clientRepoService.getClientOperationsDto(clientLogin);
    }
}
