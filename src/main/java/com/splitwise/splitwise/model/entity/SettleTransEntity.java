package com.splitwise.splitwise.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "settle_trans")
public class SettleTransEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "settle_id")
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usera_id", referencedColumnName = "user_id")
    private UserEntity userA;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userb_id", referencedColumnName = "user_id")
    private UserEntity userB;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private GroupEntity groupEntity;

    private double amount;



    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public UserEntity getUserB() {
        return userB;
    }

    public void setUserB(final UserEntity userB) {
        this.userB = userB;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(final GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public UserEntity getUserA() {
        return userA;
    }

    public void setUserA(final UserEntity userA) {
        this.userA = userA;
    }

}
