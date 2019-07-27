package com.splitwise.splitwise.model.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private Set<UserEntity> users;

//
//    @OneToMany(mappedBy = "groupEntity")
//    private Set<TransactionEntity> transactions;
//
//    public Set<TransactionEntity> getTransactions() {
//        return transactions;
//    }
//
//    public void setTransactions(final Set<TransactionEntity> transactions) {
//        this.transactions = transactions;
//    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final Set<UserEntity> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override public String toString() {
        return "GroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
