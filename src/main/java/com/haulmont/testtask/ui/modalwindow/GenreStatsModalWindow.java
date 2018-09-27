package com.haulmont.testtask.ui.modalwindow;

import com.haulmont.testtask.dao.BookDAO;
import com.haulmont.testtask.entity.GenreStats;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GenreStatsModalWindow extends Window {

    private static final String WINDOW_HEADER = "Количество книг каждого жанра";
    private static final String GENRE_LABEL = "genre";
    private static final String BOOKS_COUNT_LABEL = "booksCount";
    private static final String GENRE_HEADER = "Жанр";
    private static final String BOOKS_COUNT_HEADER = "Количество книг";

    public GenreStatsModalWindow() {
        super(WINDOW_HEADER);
        center();

        VerticalLayout mainLayout = new VerticalLayout();
        Grid dataGrid = new Grid();
        mainLayout.addComponents(dataGrid);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        dataGrid.setContainerDataSource(new BeanItemContainer<>(GenreStats.class, new BookDAO().getGenreStats()));
        dataGrid.setColumns(GENRE_LABEL, BOOKS_COUNT_LABEL);
        dataGrid.getColumn(GENRE_LABEL).setHeaderCaption(GENRE_HEADER);
        dataGrid.getColumn(BOOKS_COUNT_LABEL).setHeaderCaption(BOOKS_COUNT_HEADER);

        setContent(mainLayout);
        setResizable(false);
        setClosable(true);
        setModal(true);
    }
}
