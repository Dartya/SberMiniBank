package ru.sber.alex.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.alex.minibank.entities.OperationEntity;

import java.util.List;

public interface OperationRepo extends JpaRepository<OperationEntity, Long> {

    List<OperationEntity> findAllByAccountId(int id);
    List<OperationEntity> findAll();
    OperationEntity findByAccountsId(int id);
}
