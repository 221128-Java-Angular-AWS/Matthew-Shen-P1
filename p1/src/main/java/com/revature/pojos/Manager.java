package com.revature.pojos;

public class Manager{
    private Integer managerId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public Manager(){
    }

    public Manager(Integer managerId, String firstName, String lastName, String username, String password) {
        this.managerId = managerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public Manager(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    
    public void setManagerId(Integer i){
        this.managerId = i;
    }

    public Integer getManagerId(){
        return managerId;
    }
    public String getFirstName() {
        return firstName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
