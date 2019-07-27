package com.splitwise.splitwise.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.splitwise.splitwise.exception.SplitwiseAppException;
import com.splitwise.splitwise.model.entity.GroupEntity;
import com.splitwise.splitwise.model.entity.TransactionEntity;
import com.splitwise.splitwise.model.entity.UserEntity;
import com.splitwise.splitwise.model.request.TransDetail;
import com.splitwise.splitwise.model.request.TransactionRequest;
import com.splitwise.splitwise.repo.GroupRepo;
import com.splitwise.splitwise.repo.TransactionRepo;
import com.splitwise.splitwise.repo.UserRepo;
import com.splitwise.splitwise.service.TransactionService;


@Service
public class TransactionServiceImpl implements TransactionService  {

    private TransactionRepo transactionRepo;
    private UserRepo userRepo;
    private GroupRepo groupRepo;


    @Autowired
    public TransactionServiceImpl(final TransactionRepo transactionRepo, final UserRepo userRepo,
            final GroupRepo groupRepo) {
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }



    @Override
    public void addTransaction(final TransactionRequest request) {
        final String groupName = request.getGroupName();
        final GroupEntity groupEntity = groupRepo.findByName(groupName);
        if (groupEntity == null) {
            throw new SplitwiseAppException("Group not found -->" + groupName);
        }

        final List<TransDetail> transDetailList = request.getTransDetail();

        for (int i = 0; i < transDetailList.size(); i++) {
            final TransDetail transDetail = transDetailList.get(i);
            final String userName = transDetail.getPaidBy();
            final UserEntity userEntity = userRepo.findByName(userName);
            if (userEntity == null) {
                throw new SplitwiseAppException("User not found " + userName);
            }
            addAmount(transDetail.getAmount(), userEntity, groupEntity);
        }

    }

    private void addAmount(final double amount, final UserEntity userEntity, final GroupEntity groupEntity) {
        final TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionDate(new Date());
        transactionEntity.addTransaction(userEntity, groupEntity);
        transactionRepo.save(transactionEntity);
    }
}
