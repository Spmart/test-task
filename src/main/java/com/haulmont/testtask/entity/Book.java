package com.haulmont.testtask.entity;

import java.util.Date;

public class Book {
    private long id;
    private String name;
    private Author author;
    private Genre genre;
    private String publisher;  //TODO: Check type/define new class
    private Date year;  //TODO: Check type. May be better define this as string
    private String city;

    public Book(long id, String name, Author author, Genre genre, String publisher, Date year, String city) {
        this(name, author, genre, publisher, year, city);
        this.id = id;
    }

    public Book(String name, Author author, Genre genre, String publisher, Date year, String city) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.year = year;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
