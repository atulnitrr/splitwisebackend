package com.splitwise.splitwise.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.splitwise.splitwise.model.entity.UserEntity;


@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {



}
