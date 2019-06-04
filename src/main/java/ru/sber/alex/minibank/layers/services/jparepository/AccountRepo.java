package ru.sber.alex.minibank.layers.services.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.AccountEntity;

/**
 * Интерфейс сервиса сущности БД "Счет".
 */
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    /**
     * Возвращает счет с указанным id.
     * @param id номер счета
     * @return сущность БД "Счет"
     */
    AccountEntity getById(int id);
}
