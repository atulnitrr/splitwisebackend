package com.splitwise.splitwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.splitwise.splitwise.model.request.TransactionRequest;
import com.splitwise.splitwise.service.TransactionService;


@RestController
@RequestMapping("/trans")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<?> addTrans(@RequestBody  final TransactionRequest transactionRequest) {
        transactionService.addTransaction(transactionRequest);
        return ResponseEntity.ok().body("Added users");
    }

    @GetMapping(path = "/{groupName}/{userName}")
    public ResponseEntity<?> getBalance(@PathVariable("userName") final String userName,
            @PathVariable("groupName") final String groupName) {
        double balance = transactionService.userBalanceInGroup(groupName, userName);
        return ResponseEntity.ok().body(balance);
    }


    @GetMapping(path = "/{groupName}")
    public ResponseEntity<?> getBalanceOfGroup(
            @PathVariable(name = "groupName", required = true) final String groupName) {
        return ResponseEntity.ok().body(transactionService.groupBalanceByUser(groupName));
    }


    @GetMapping(path = "/balance/user/{userName}")
    private ResponseEntity<?> getPersonBalanceInALlGroup(@PathVariable(name = "userName", required = true) final String userName) {

        return ResponseEntity.ok().body(transactionService.getUserBalanceInALlGroup(userName));
    }


}
