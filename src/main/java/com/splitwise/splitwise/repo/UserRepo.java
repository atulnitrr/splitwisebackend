package com.splitwise.splitwise.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.splitwise.splitwise.model.entity.UserEntity;


@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByName(final String name);
    UserEntity findByEmail(final String email);



}
