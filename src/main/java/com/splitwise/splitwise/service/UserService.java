package com.splitwise.splitwise.service;

import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;


public interface UserService {

    void addGroup(final AddGroupRequest groupName);
    void addUserToGroup(final UserGroupRequest request);

}