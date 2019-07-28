package com.splitwise.splitwise.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.splitwise.splitwise.exception.SplitwiseAppException;
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
    public List<String> getAllGroups() {
        final Iterator<GroupEntity> iterator = groupRepo.findAll().iterator();
        final List<String> groups = new ArrayList<>();

        while (iterator.hasNext()) {
            final GroupEntity groupEntity = iterator.next();
            groups.add(groupEntity.getName());
        }
        return groups;
    }

    @Override
    public void addGroup(final AddGroupRequest addGroupRequest) {
        final GroupEntity groupEntity = new GroupEntity();
        System.out.println(addGroupRequest.getName());
        groupEntity.setName(addGroupRequest.getName());
        try {
            groupRepo.save(groupEntity);
        } catch (final DataIntegrityViolationException e) {
            throw new SplitwiseAppException("Group already exist");
        }
    }

    @Override
    public void addUserToGroup(final UserGroupRequest request) {
        GroupEntity currentGroup = groupRepo.findByName(request.getGroupName());
        if (currentGroup == null) {
            currentGroup = new GroupEntity();
            currentGroup.setName(request.getGroupName());
        }

        List<String> allUsers = request.getUserNames();

        for (int i = 0; i < allUsers.size(); i++) {
            final String userName = allUsers.get(i);
            UserEntity userEntity = userRepo.findByName(userName);

            if (userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setName(userName);
            }

            userEntity.addUserToGroup(currentGroup);
            userRepo.save(userEntity);
        }


    }
}
