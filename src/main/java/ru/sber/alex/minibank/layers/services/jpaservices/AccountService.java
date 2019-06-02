package ru.sber.alex.minibank.layers.services.jpaservices;

import ru.sber.alex.minibank.entities.AccountEntity;

/**
 * Интерфейс сервиса сущности БД "Счет".
 */
public interface AccountService {

    /**
     * Возвращает счет с указанным id.
     * @param id номер счета
     * @return сущность БД "Счет"
     */
    AccountEntity getById(int id);
}
