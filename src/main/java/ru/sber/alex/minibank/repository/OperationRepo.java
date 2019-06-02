package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.OperationEntity;

import java.util.List;

/**
 * Интерфейс - JpaRepository сущности БД "Операция"
 */
public interface OperationRepo extends JpaRepository<OperationEntity, Long> {

    /**
     * Возвращает все операции из репозитория
     * @return List операций
     */
    List<OperationEntity> findAll();

    /**
     * Возвращает все операции указанного клиента
     * @param id id клиента
     * @return List операций
     */
    List<OperationEntity> findByAccountsId(Integer id);
}
