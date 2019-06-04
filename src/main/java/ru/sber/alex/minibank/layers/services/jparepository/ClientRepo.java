package ru.sber.alex.minibank.layers.services.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.ClientEntity;

import java.util.List;

/**
 * Интерфейс сервиса сущности БД "Клиент".
 */
public interface ClientRepo extends JpaRepository<ClientEntity, Long> {

    /**
     * Возвращает клиента с указанным логином
     * @param login логин пользователя
     * @return сущность БД "Клиент"
     */
    ClientEntity findByLogin(String login);

    /**
     * Возвращает всех клиентов
     * @return List с клиентами
     */
    List<ClientEntity> findAll();
}
