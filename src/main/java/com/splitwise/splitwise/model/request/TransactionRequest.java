package com.splitwise.splitwise.model.request;

import java.util.List;


public class TransactionRequest {

    private String groupName;

    private List<TransDetail> transDetail;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public List<TransDetail> getTransDetail() {
        return transDetail;
    }

    public void setTransDetail(final List<TransDetail> transDetail) {
        this.transDetail = transDetail;
    }
}
