package com.example.edubridge;

public class UserModalClass {
    String firstName, address;

    public UserModalClass() {
    }

    public UserModalClass(String firstName, String address) {
        this.firstName = firstName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return address;
    }

    public void setLastName(String address) {
        this.address = address;
    }
}
