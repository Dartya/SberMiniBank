package ru.sber.alex.minibank.layers.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.alex.minibank.entities.AccountEntity;
import ru.sber.alex.minibank.repository.AccountRepo;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final static int ERROR = -1;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountRepo accountRepo;

    public AccountEntity getById(int id){
        return accountRepo.getById(id);
    }


}
