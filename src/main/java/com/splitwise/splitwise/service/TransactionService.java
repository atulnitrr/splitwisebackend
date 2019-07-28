package com.splitwise.splitwise.service;

import java.util.List;
import java.util.Map;
import com.splitwise.splitwise.model.request.TransactionRequest;
import com.splitwise.splitwise.model.response.UserBalanceResponse;


public interface TransactionService {

    void addTransaction(final TransactionRequest request);

    double userBalanceInGroup(final String groupName, final String userName);

    double getUserBalanceInALlGroup(final String userName);

    List<UserBalanceResponse> groupBalanceByUser(final String groupName);
}
