package com.splitwise.splitwise.service;

import java.util.List;
import com.splitwise.splitwise.model.request.TransactionRequest;


public interface TransactionService {

    void addTransaction(final TransactionRequest request);
}
