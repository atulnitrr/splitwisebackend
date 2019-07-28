package com.splitwise.splitwise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.splitwise.splitwise.exception.SplitwiseAppException;
import com.splitwise.splitwise.model.entity.GroupEntity;
import com.splitwise.splitwise.model.entity.SettleTransEntity;
import com.splitwise.splitwise.model.entity.TransactionEntity;
import com.splitwise.splitwise.model.entity.UserEntity;
import com.splitwise.splitwise.model.request.TransDetail;
import com.splitwise.splitwise.model.request.TransactionRequest;
import com.splitwise.splitwise.model.response.UserBalanceResponse;
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
    public double getUserBalanceInALlGroup(final String userName) {
        UserEntity userEntity = userRepo.findByName(userName);
        List<SettleTransEntity> settleTransEntities = settleTransRepo.findUserExpenses(userEntity);
        double totalPaid =
                settleTransEntities.stream().filter(t -> t.getUserA().equals(userEntity)).mapToDouble(t-> t.getAmount()).sum();

        double totalOwe =
                settleTransEntities.stream().filter(t->t.getUserB().equals(userEntity)).mapToDouble(t-> -t.getAmount()).sum();

        return totalPaid + totalOwe;
    }

    @Override
    public List<UserBalanceResponse> groupBalanceByUser(final String groupName) {

        final GroupEntity groupEntity = groupRepo.findByName(groupName);
        if (groupEntity == null) {
            throw new SplitwiseAppException("Group not found " + groupName);
        }


        final List<UserBalanceResponse> userBalanceResponses = new ArrayList<>();
        final Map<String, Double> map = new HashMap<>();

        List<SettleTransEntity> settleTransEntities = settleTransRepo.findGroupBalanceByUser(groupEntity);

        for (int i = 0; i < settleTransEntities.size(); i++) {

            final SettleTransEntity settleTransEntity = settleTransEntities.get(i);
            final String userA = settleTransEntity.getUserA().getName();
            final String userB = settleTransEntity.getUserB().getName();
            final Double amount = settleTransEntity.getAmount();

            if (map.containsKey(userA)) {
                map.put(userA,  map.get(userA) + amount);
            } else {
                map.put(userA,  amount);
            }

            if (map.containsKey(userB)) {
                map.put(userB, map.get(userB) - amount);
            } else {
                map.put(userB, -amount);
            }

        }

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            userBalanceResponses.add(new UserBalanceResponse(entry.getKey(), entry.getValue()));
        }

        return userBalanceResponses;
    }


    @Override
    public double userBalanceInGroup(final String groupName, final String userName) {
        final UserEntity userEntity = userRepo.findByName(userName);

        if (userEntity == null) {
            throw new SplitwiseAppException("User not found " + userName);
        }

        final GroupEntity groupEntity = groupRepo.findByName(groupName);

        if (groupEntity == null) {
            throw new SplitwiseAppException("Group not found " + groupName);
        }

       return groupBalanceByUser(groupName).stream().filter(userBalanceResponse -> userBalanceResponse.getName().equals(userName)).mapToDouble(user -> user.getAmout()).sum();
    }

    @Override
    @Transactional
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
//             add amount to transaction table
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
            settleTransRepo.updateAmount(settleTransEntity.getAmount() + amountToSettle, settleTransEntity.getId());
            return;
        }


        settleTransEntity = settleTransRepo.findByUserAndGroup(paidTo,
                paidBy, groupEntity);

        if (settleTransEntity != null) {
            settleTransRepo.updateAmount(settleTransEntity.getAmount() - amountToSettle, settleTransEntity.getId());
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
