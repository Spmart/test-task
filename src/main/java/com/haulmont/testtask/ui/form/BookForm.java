package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.BookDAO;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.ui.MainUI;
import com.haulmont.testtask.ui.modalwindow.BookModalWindow;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class BookForm extends FormLayout {

    private static final String ADD_CAPTION = "Добавить";
    private static final String EDIT_CAPTION = "Изменить";
    private static final String DELETE_CAPTION = "Удалить";

    private static final String ID_LABEL = "id";
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

    private static final String DELETE_ERROR_MESSAGE = "Ошибка при удалении.";

    private MainUI mainUI;
    private BookDAO bookDAO = new BookDAO();
    private Grid dataGrid = new Grid();
    private Button addButton = new Button(ADD_CAPTION);
    private Button editButton = new Button(EDIT_CAPTION);
    private Button deleteButton = new Button(DELETE_CAPTION);

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

        /* Disable buttons while nothing selected */
        disableEditAndDeleteButtons();

        /* Setting up an add button */
        addButton.addClickListener(clickEvent -> {
            //TODO: Modal window for adding
            BookModalWindow modalWindow = new BookModalWindow(ADD_CAPTION);
            UI.getCurrent().addWindow(modalWindow);
            modalWindow.addCloseListener(closeEvent -> {
                update();
                disableEditAndDeleteButtons();
            });
        });

        /* Setup an edit button */
        editButton.addClickListener(clickEvent -> {
            //TODO: Modal window for editing
            Object selected = ((Grid.SingleSelectionModel) dataGrid.getSelectionModel()).getSelectedRow();
            if (selected != null) {
                long id = (long) dataGrid.getContainerDataSource().getItem(selected).getItemProperty(ID_LABEL).getValue();
                BookModalWindow modalWindow = new BookModalWindow(EDIT_CAPTION, bookDAO.getById(id));
                UI.getCurrent().addWindow(modalWindow);
                modalWindow.addCloseListener(closeEvent -> {
                    update();
                    disableEditAndDeleteButtons();
                });
            }
        });

        /* Setup a delete button */
        deleteButton.addClickListener(clickEvent -> {
            //TODO: Query and error message
            Object selected = ((Grid.SingleSelectionModel) dataGrid.getSelectionModel()).getSelectedRow();
            if (selected != null) {
                long id = (long) dataGrid.getContainerDataSource().getItem(selected).getItemProperty(ID_LABEL).getValue();
                boolean isDeleted = bookDAO.delete(id);
                if (isDeleted) {
                    update();
                    disableEditAndDeleteButtons();
                } else {
                    Notification.show(DELETE_ERROR_MESSAGE, Notification.Type.ERROR_MESSAGE);
                }
            }
        });

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
    }

    public void update() {
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
