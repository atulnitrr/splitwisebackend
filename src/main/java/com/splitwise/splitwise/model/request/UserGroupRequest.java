package com.splitwise.splitwise.model.request;

import java.util.List;


public class UserGroupRequest {

    private List<String> userNames;
    private String groupName;

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(final List<String> userNames) {
        this.userNames = userNames;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
}
