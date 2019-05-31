package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.ClientEntity;

import java.util.List;

public interface ClientRepo extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findAll();
    ClientEntity findByLogin(String login);
}
