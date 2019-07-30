package com.splitwise.splitwise.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.splitwise.splitwise.dto.UserDto;
import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;
import com.splitwise.splitwise.model.response.UserResponse;


public interface UserService extends UserDetailsService  {

    UserDto registerUser(final UserDto userDto);
    void addGroup(final AddGroupRequest groupName);
    void addUserToGroup(final UserGroupRequest request);
    List<String> getAllGroups();
    List<UserResponse> getAllUsersOfGroup(final String groupName);
    List<UserResponse> getUsers(final int page, final int size);

}
