package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.AccountEntity;

/**
 * Интерфейс - JpaRepository сущности БД "Счет"
 */
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    /**
     * Возвращает счет с указанным номером
     * @param id номер счета
     * @return сущность БД "Счет"
     */
    AccountEntity getById(Integer id);
}
