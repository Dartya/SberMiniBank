package ru.sber.alex.minibank.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sber.alex.minibank.entities.AccountEntity;

public interface AccountRepo extends CrudRepository<AccountEntity, Long> {

    AccountEntity getById(Integer id);
}
