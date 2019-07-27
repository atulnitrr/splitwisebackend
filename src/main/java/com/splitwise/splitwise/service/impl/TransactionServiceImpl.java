package com.splitwise.splitwise.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.splitwise.splitwise.exception.SplitwiseAppException;
import com.splitwise.splitwise.model.entity.GroupEntity;
import com.splitwise.splitwise.model.entity.SettleTransEntity;
import com.splitwise.splitwise.model.entity.TransactionEntity;
import com.splitwise.splitwise.model.entity.UserEntity;
import com.splitwise.splitwise.model.request.TransDetail;
import com.splitwise.splitwise.model.request.TransactionRequest;
import com.splitwise.splitwise.repo.GroupRepo;
import com.splitwise.splitwise.repo.SettleTransRepo;
import com.splitwise.splitwise.repo.TransactionRepo;
import com.splitwise.splitwise.repo.UserRepo;
import com.splitwise.splitwise.service.TransactionService;


@Service
public class TransactionServiceImpl implements TransactionService  {

    private TransactionRepo transactionRepo;
    private SettleTransRepo settleTransRepo;
    private UserRepo userRepo;
    private GroupRepo groupRepo;


    @Autowired
    public TransactionServiceImpl(final TransactionRepo transactionRepo,
            final SettleTransRepo settleTransRepo, final UserRepo userRepo, final GroupRepo groupRepo) {
        this.transactionRepo = transactionRepo;
        this.settleTransRepo = settleTransRepo;
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
            // add amount to transaction table
            addAmount(transDetail.getAmount(), userEntity, groupEntity);


            settle(transDetail.getAmount(), userEntity, groupEntity);
        }

    }

    private void addAmount(final double amount, final UserEntity userEntity, final GroupEntity groupEntity) {
        final TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionDate(new Date());
        transactionEntity.addTransaction(userEntity, groupEntity);
        transactionRepo.save(transactionEntity);
    }

    private void settle(final double amount, final UserEntity userEntity, final GroupEntity groupEntity) {
        final Set<UserEntity> allUsers =  groupEntity.getUsers();
        final Iterator<UserEntity> iterator = allUsers.iterator();
        final double amountToSettle = amount / (allUsers.size() );
        while (iterator.hasNext()) {
            final UserEntity currentUser = iterator.next();
            if (currentUser.getName().equals(userEntity.getName())) {
                continue;
            }
            updateTrans(userEntity, currentUser, groupEntity, amountToSettle);
        }
    }

    private void updateTrans(final UserEntity paidBy, final UserEntity paidTo,
            final GroupEntity groupEntity, double amountToSettle) {

        SettleTransEntity settleTransEntity = settleTransRepo.findByUserAndGroup(paidBy,
                paidTo, groupEntity);

        // here A -> B = +20 it means a will get 20 from B
        if (settleTransEntity != null) {
            settleTransEntity.setAmount(settleTransEntity.getAmount() + amountToSettle);
            settleTransRepo.save(settleTransEntity);
            return;
        }


        settleTransEntity = settleTransRepo.findByUserAndGroup(paidTo,
                paidBy, groupEntity);

        if (settleTransEntity != null) {
            settleTransEntity.setAmount(settleTransEntity.getAmount() - amountToSettle);
            settleTransRepo.save(settleTransEntity);
            return;
        }

        settleTransEntity = new SettleTransEntity();
        settleTransEntity.setAmount(amountToSettle);
        settleTransEntity.setUserA(paidBy);
        settleTransEntity.setUserB(paidTo);
        settleTransEntity.setGroupEntity(groupEntity);

        settleTransRepo.save(settleTransEntity);
    }


}
