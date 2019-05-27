package ru.sber.alex.minibank.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sber.alex.minibank.entities.ClientEntity;

import java.util.List;

public interface ClientRepo extends CrudRepository<ClientEntity, Long> {

    List<ClientEntity> findAll();
    ClientEntity findByLogin(String login);
}
