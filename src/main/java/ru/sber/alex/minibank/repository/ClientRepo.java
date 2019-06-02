package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.ClientEntity;

import java.util.List;

/**
 * Интерфейс - JpaRepository сущности БД "Клиент"
 */
public interface ClientRepo extends JpaRepository<ClientEntity, Long> {

    /**
     * Возвращает всех клиентов
     * @return List с клиентами
     */
    List<ClientEntity> findAll();

    /**
     * Возвращает клиента с указанным логином
     * @param login логин пользователя
     * @return сущность БД "Клиент"
     */
    ClientEntity findByLogin(String login);
}
