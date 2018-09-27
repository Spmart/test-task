package com.haulmont.testtask.entity;

public class GenreStats {

    private Genre genre;
    private int booksCount;

    public GenreStats(Genre genre, int booksCount) {
        this.genre = genre;
        this.booksCount = booksCount;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(int booksCount) {
        this.booksCount = booksCount;
    }
}
