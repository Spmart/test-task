package com.haulmont.testtask.entity;

public class Author {
    private long id;  //TODO: Id generator.
    private String firstName;
    private String lastName;
    private String middleName;  //TODO: Define empty string here, if this arg is missed

    public Author(long id, String firstName, String lastName, String middleName) {
        this(firstName, lastName, middleName);
        this.id = id;
    }

    public Author(String firstName, String lastName, String middleName) {
        this(firstName, lastName);
        this.middleName = middleName;
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {  //TODO: Check difference between Long and long
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return (lastName + " " + firstName + " " + middleName);
    }
}
