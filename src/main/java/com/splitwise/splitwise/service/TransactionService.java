package com.splitwise.splitwise.service;

import java.util.List;
import java.util.Map;
import com.splitwise.splitwise.model.request.TransactionRequest;


public interface TransactionService {

    void addTransaction(final TransactionRequest request);

    double userBalanceInGroup(final String groupName, final String userName);

    Map<String, Double> groupBalanceByUser(final String groupName);
}
