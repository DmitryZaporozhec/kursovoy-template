package kursovoy.model;


import kursovoy.model.constants.UserStatus;

import java.util.*;

public class User extends AbstractModel {
    private String firstName;
    private String lastName;
    private int age;
    private String login;
    private String password;
    private UserStatus status;
    private long failLoginCount;
    private Date lastLogin;
    private String smsCode;
    private String email;
    private String passPhrase;
    private List<UserIpHistory> userIpHistoryList;


    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public User(int userId, String firstName, String lastName, int age) {
        this.id = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public long getFailLoginCount() {
        return failLoginCount;
    }

    public void setFailLoginCount(long failLoginCount) {
        this.failLoginCount = failLoginCount;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<UserIpHistory> getUserIpHistoryList() {
        return userIpHistoryList;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public void setUserIpHistoryList(List<UserIpHistory> userIpHistoryList) {
        this.userIpHistoryList = userIpHistoryList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String phone) {
        this.email = phone;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }
}
