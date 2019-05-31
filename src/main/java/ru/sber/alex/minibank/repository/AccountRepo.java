package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.AccountEntity;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    AccountEntity getById(Integer id);
}
