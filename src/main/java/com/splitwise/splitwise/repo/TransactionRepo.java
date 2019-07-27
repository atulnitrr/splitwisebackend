package com.splitwise.splitwise.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.splitwise.splitwise.model.entity.TransactionEntity;


@Repository
public interface TransactionRepo extends CrudRepository<TransactionEntity, Long> {


}
