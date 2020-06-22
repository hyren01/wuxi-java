package com.jn.primiary.beyondsoft.aspect;

/**
 * @author bystander
 * @date 2018/9/30
 */
public class TokenInfo {
    private String id;
    private String userId;
    private String userName;
    private String userAccount;

    public TokenInfo(){

    }

    public TokenInfo(String id, String userId, String userName, String userAccount){
        this.id=id;
        this.userId=userId;
        this.userName=userName;
        this.userAccount=userAccount;
    }

    public String getId() {
        return id;
    }

    public TokenInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TokenInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public TokenInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public TokenInfo setUserAccount(String userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                '}';
    }
}
