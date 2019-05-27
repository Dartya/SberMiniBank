package ru.sber.alex.minibank.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sber.alex.minibank.entities.ClientEntity;

public interface ClientRepo extends CrudRepository<ClientEntity, Long> {
}
