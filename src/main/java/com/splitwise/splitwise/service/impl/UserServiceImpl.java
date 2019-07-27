package com.splitwise.splitwise.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.splitwise.splitwise.model.entity.GroupEntity;
import com.splitwise.splitwise.model.entity.UserEntity;
import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;
import com.splitwise.splitwise.repo.GroupRepo;
import com.splitwise.splitwise.repo.UserRepo;
import com.splitwise.splitwise.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private GroupRepo groupRepo;

    @Autowired
    public UserServiceImpl(final UserRepo userRepo, final GroupRepo groupRepo) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }

    @Override
    public void addGroup(final AddGroupRequest addGroupRequest) {
        final GroupEntity groupEntity = new GroupEntity();
        System.out.println(addGroupRequest.getName());
        groupEntity.setName(addGroupRequest.getName());
        groupRepo.save(groupEntity);
    }

    @Override
    public void addUserToGroup(final UserGroupRequest request) {
        final Set<UserEntity> userEntities;
        GroupEntity currentGroup = groupRepo.findByName(request.getGroupName());

        if (currentGroup == null) {
            userEntities = new HashSet<>();
            currentGroup = new GroupEntity();
            currentGroup.setName(request.getGroupName());
        } else {
            userEntities = currentGroup.getUsers();
        }

        final UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getUserName());
        userEntities.add(userEntity);
        currentGroup.setUsers(userEntities);
        userEntity.addUserToGroup(currentGroup);
        userRepo.save(userEntity);
    }
}
