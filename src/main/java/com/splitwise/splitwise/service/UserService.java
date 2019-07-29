package com.splitwise.splitwise.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;


public interface UserService extends UserDetailsService  {

    void addGroup(final AddGroupRequest groupName);
    void addUserToGroup(final UserGroupRequest request);
    List<String> getAllGroups();
    List<String> getAllUsersOfGroup(final String groupName);

}
