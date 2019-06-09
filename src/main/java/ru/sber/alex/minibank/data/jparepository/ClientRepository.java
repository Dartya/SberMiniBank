package ru.sber.alex.minibank.data.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.data.entities.ClientEntity;

import java.util.List;

/**
 * Интерфейс сервиса сущности БД "Клиент".
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

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


    /**
     * Возвращает клиента с указанным e-mail
     * @param email e-mail пользователя
     * @return сущность БД "Клиент"
     */
    ClientEntity findByEmail(String email);
}
