package com.haulmont.testtask.ui;

import com.haulmont.testtask.ui.form.AuthorForm;
import com.haulmont.testtask.ui.form.BookForm;
import com.haulmont.testtask.ui.form.GenreForm;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private static final String AUTHORS_TAB_CAPTION = "Авторы";
    private static final String GENRES_TAB_CAPTION = "Жанры";
    private static final String BOOKS_TAB_CAPTION = "Книги";

    private TabSheet tabSheet = new TabSheet();
    private AuthorForm authorForm = new AuthorForm(this);
    private GenreForm genreForm = new GenreForm(this);
    private BookForm bookForm = new BookForm(this);

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        VerticalLayout authorsTab = new VerticalLayout();
        authorsTab.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        authorsTab.addComponent(authorForm);

        VerticalLayout genresTab = new VerticalLayout();
        genresTab.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        genresTab.addComponent(genreForm);


        VerticalLayout booksTab = new VerticalLayout();
        booksTab.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        booksTab.addComponent(bookForm);


        tabSheet.addTab(authorsTab, AUTHORS_TAB_CAPTION);
        tabSheet.addTab(genresTab, GENRES_TAB_CAPTION);
        tabSheet.addTab(booksTab, BOOKS_TAB_CAPTION);
        tabSheet.addSelectedTabChangeListener(selectedTabChangeEvent -> bookForm.update());

        layout.addComponent(tabSheet);
        setContent(layout);
    }
}