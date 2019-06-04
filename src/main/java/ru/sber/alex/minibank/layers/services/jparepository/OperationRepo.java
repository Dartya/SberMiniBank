package ru.sber.alex.minibank.layers.services.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.OperationEntity;

import java.util.List;

/**
 * Интерфейс сервиса сущности БД "Операция".
 */
public interface OperationRepo extends JpaRepository<OperationEntity, Long> {

    /**
     * Возвращает все операции из репозитория
     * @return List операций
     */
    List<OperationEntity> findAll();

    /**
     * Возвращает List с операциями пользователя с указанным id.
     * @param id id пользователя
     * @return
     */
    List<OperationEntity> findByAccountsId(Integer id);
}
