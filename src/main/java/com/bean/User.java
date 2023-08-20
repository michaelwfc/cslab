package com.bean;

public class User {
    public int id;
    public String name;
    public School school;

    public User(int id, String name, School school) {
        this.id = id;
        this.name = name;
        this.school = school;
    }
}
