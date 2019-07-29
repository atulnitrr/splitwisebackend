package com.splitwise.splitwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.splitwise.splitwise.model.request.AddGroupRequest;
import com.splitwise.splitwise.model.request.UserGroupRequest;
import com.splitwise.splitwise.model.response.RegisterUserResponse;
import com.splitwise.splitwise.service.UserService;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }



    @PostMapping
    public ResponseEntity<?> registerUser() {

        return ResponseEntity.ok().body("Added succes");
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
