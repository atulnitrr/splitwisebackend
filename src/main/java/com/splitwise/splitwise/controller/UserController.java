package com.splitwise.splitwise.controller;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.splitwise.splitwise.dto.UserDto;
import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.RegisterUserRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;
import com.splitwise.splitwise.model.response.RegisterUserResponse;
import com.splitwise.splitwise.service.UserService;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(final UserService userService, final ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    public ResponseEntity<?> registerUser(@Valid  @RequestBody final RegisterUserRequest registerUserRequest) {
        final UserDto userDtoRequest = new UserDto();
        modelMapper.map(registerUserRequest, userDtoRequest);
        final UserDto savedUser = userService.registerUser(userDtoRequest);
        final RegisterUserResponse response = new RegisterUserResponse();
        modelMapper.map(savedUser, response);
        LOGGER.info("Successfully added user -> {}", savedUser.getName() );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(path = "/addgroup")
    public ResponseEntity<?> addGroup (@RequestBody final AddGroupRequest addGroup) {
        userService.addGroup(addGroup);
        return ResponseEntity.ok().body("added user succesfully");
    }

    @PostMapping(path = "/addusertogroup")
    public ResponseEntity<?> addUserToGroup(@RequestBody final UserGroupRequest request) {
        userService.addUserToGroup(request);
        return ResponseEntity.ok().body("Added user to group");
    }

    @GetMapping(path = "/groups")
    public ResponseEntity<?> getALlGroups() {
        return ResponseEntity.ok().body(userService.getAllGroups());

    }


    @GetMapping(path = "/{groupName}")
    public ResponseEntity<?> getAllUserOfGroup(@PathVariable(name = "groupName", required = true) final String groupName) {
        return ResponseEntity.ok().body(userService.getAllUsersOfGroup(groupName));
    }


}
