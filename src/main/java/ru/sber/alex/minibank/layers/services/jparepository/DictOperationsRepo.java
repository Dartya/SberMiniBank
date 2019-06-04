package ru.sber.alex.minibank.layers.services.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.DictOperationsEntity;

/**
 * Интерфейс - JpaRepository сущности БД "Название операции"
 */
public interface DictOperationsRepo extends JpaRepository<DictOperationsEntity, Long> {
}
