package com.splitwise.splitwise.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.splitwise.splitwise.model.entity.GroupEntity;
import com.splitwise.splitwise.model.entity.SettleTransEntity;
import com.splitwise.splitwise.model.entity.UserEntity;


@Repository
public interface SettleTransRepo extends CrudRepository<SettleTransEntity, Long> {

    @Query(value = "select t from SettleTransEntity t where t.userA = :userA and t.userB = :userB and t.groupEntity = :group")
    SettleTransEntity findByUserAndGroup(@Param("userA") final UserEntity userA, @Param("userB") final UserEntity userBId,
            @Param("group") final GroupEntity groupEntity);
}
