package ru.sber.alex.minibank.layers.services.jpaservices;

import ru.sber.alex.minibank.entities.OperationEntity;

import java.util.List;

/**
 * Интерфейс сервиса сущности БД "Операция".
 */
public interface OperationService {

    /**
     * Возвращает List с операциями пользователя с указанным id.
     * @param id id пользователя
     * @return
     */
    List<OperationEntity> findByAccountsId(Integer id);
}
