package com.splitwise.splitwise.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.splitwise.splitwise.model.entity.GroupEntity;


@Repository
public interface GroupRepo extends CrudRepository<GroupEntity, Long> {

    GroupEntity findByName(final String name);




}
