package com.splitwise.splitwise.model.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    private Set<GroupEntity> groups;

    @OneToMany(mappedBy = "userEntity")
    private Set<TransactionEntity> transactions;


    public Set<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(final Set<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public void addUserToGroup(final GroupEntity groupEntity) {
        if (groups == null) {
            groups = new HashSet<>();
        }
        groups.add(groupEntity);
        groupEntity.getUsers().add(this);
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(final Set<GroupEntity> groups) {
        this.groups = groups;
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
}
