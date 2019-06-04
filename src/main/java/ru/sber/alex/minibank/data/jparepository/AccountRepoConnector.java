package ru.sber.alex.minibank.data.jparepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.data.entities.AccountEntity;

/**
 * Реализация сервиса сущности БД "Счет".
 */
@Repository
@Slf4j
public class AccountRepoConnector {

    @Autowired
    private AccountRepo accountRepo;

    /**
     * Достает из репозитория счет с указанным id.
     * @param id номер счета
     * @return сущность БД "Счет"
     */
    public AccountEntity getById(int id){
        return accountRepo.getById(id);
    }
}
