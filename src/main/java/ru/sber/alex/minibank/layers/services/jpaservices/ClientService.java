package ru.sber.alex.minibank.layers.services.jpaservices;

import ru.sber.alex.minibank.entities.ClientEntity;

/**
 * Интерфейс сервиса сущности БД "Клиент".
 */
public interface ClientService {

    /**
     * Возвращает пользователя с указанным логином.
     * @param login логин пользователя.
     * @return сущность БД "Клиент"
     */
    ClientEntity getClient(String login);
}
