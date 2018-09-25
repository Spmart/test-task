package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.BookDAO;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.ui.MainUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class BookForm extends FormLayout {

    private static final String BOOK_NAME_LABEL = "name";
    private static final String AUTHOR_NAME_LABEL = "author";
    private static final String GENRE_NAME_LABEL = "genre";
    private static final String PUBLISHER_LABEL = "publisher";
    private static final String YEAR_LABEL = "year";
    private static final String CITY_LABEL = "city";

    private static final String BOOK_NAME_HEADER = "Название";
    private static final String AUTHOR_NAME_HEADER = "Автор";
    private static final String GENRE_NAME_HEADER = "Жанр";
    private static final String PUBLISHER_HEADER = "Издатель";
    private static final String YEAR_HEADER = "Год";
    private static final String CITY_HEADER = "Город";

    private MainUI mainUI;
    private BookDAO bookDAO = new BookDAO();
    private Grid dataGrid = new Grid();
    private Button addButton = new Button("Добавить");
    private Button editButton = new Button("Изменить");
    private Button deleteButton = new Button("Удалить");

    public BookForm(MainUI mainUI) {
        this.mainUI = mainUI;
        VerticalLayout mainLayout = new VerticalLayout();
        update();

        /* Setup a grid */
        dataGrid.addSelectionListener(selectionEvent -> {
            if (selectionEvent.getSelected() != null) {
                enableEditAndDeleteButtons();
            }
        });
        dataGrid.setSizeFull();
        dataGrid.setColumns(BOOK_NAME_LABEL, AUTHOR_NAME_LABEL, GENRE_NAME_LABEL, PUBLISHER_LABEL, YEAR_LABEL, CITY_LABEL);
        dataGrid.getColumn(BOOK_NAME_LABEL).setHeaderCaption(BOOK_NAME_HEADER);
        dataGrid.getColumn(AUTHOR_NAME_LABEL).setHeaderCaption(AUTHOR_NAME_HEADER);
        dataGrid.getColumn(GENRE_NAME_LABEL).setHeaderCaption(GENRE_NAME_HEADER);
        dataGrid.getColumn(PUBLISHER_LABEL).setHeaderCaption(PUBLISHER_HEADER);
        dataGrid.getColumn(YEAR_LABEL).setHeaderCaption(YEAR_HEADER);
        dataGrid.getColumn(CITY_LABEL).setHeaderCaption(CITY_HEADER);

        /* Setup layouts */
        setMargin(true);
        setSizeFull();
        CssLayout buttonsLayout = new CssLayout(addButton, editButton, deleteButton);
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        mainLayout.addComponents(dataGrid, buttonsLayout);
        mainLayout.setComponentAlignment(dataGrid, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSpacing(true);
        addComponents(mainLayout);

        /* Setting up an add button */
        addButton.addClickListener(clickEvent -> {
            //TODO: Modal window for adding
        });

        /* Setup an edit button */
        editButton.addClickListener(clickEvent -> {
            //TODO: Modal window for editing
        });

        /* Setup a delete button */
        deleteButton.addClickListener(clickEvent -> {
            //TODO: Query and error message
        });


    }

    private void update() {
        BeanItemContainer<Book> container = new BeanItemContainer<>(Book.class, bookDAO.getAll());
        dataGrid.setContainerDataSource(container);
    }

    private void enableEditAndDeleteButtons() {
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private void disableEditAndDeleteButtons() {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
}
