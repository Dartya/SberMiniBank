package ru.sber.alex.minibank.data.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.data.entities.AccountEntity;

/**
 * Интерфейс сервиса сущности БД "Счет".
 */
@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    /**
     * Возвращает счет с указанным id.
     * @param id номер счета
     * @return сущность БД "Счет"
     */
    AccountEntity getById(int id);
}
