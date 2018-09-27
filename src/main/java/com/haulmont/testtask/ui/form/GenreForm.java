package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.GenreDAO;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.ui.MainUI;
import com.haulmont.testtask.ui.modalwindow.GenreModalWindow;
import com.haulmont.testtask.ui.modalwindow.GenreStatsModalWindow;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GenreForm extends FormLayout {

    private static final String ADD_CAPTION = "Добавить";
    private static final String EDIT_CAPTION = "Изменить";
    private static final String DELETE_CAPTION = "Удалить";
    private static final String STATISTICS_CAPTION = "Показать статистику";

    private static final String ID_LABEL = "id";
    private static final String GENRE_LABEL = "name";

    private static final String GENRE_HEADER = "Жанр";

    private static final String DELETE_ERROR_MESSAGE = "Удаление невозможно. Существуют книги этого жанра.";

    private MainUI mainUI;
    private GenreDAO genreDAO = new GenreDAO();
    private Grid dataGrid = new Grid();
    private Button addButton = new Button(ADD_CAPTION);
    private Button editButton = new Button(EDIT_CAPTION);
    private Button deleteButton = new Button(DELETE_CAPTION);
    private Button showStatsButton = new Button(STATISTICS_CAPTION);

    public GenreForm(MainUI mainUI) {
        this.mainUI = mainUI;
        VerticalLayout mainLayout = new VerticalLayout();
        update();

        /* Setup a grid */
        dataGrid.addSelectionListener(selectionEvent -> {
            if (selectionEvent.getSelected() != null) {
                enableEditDeleteStatsButtons();
            }
        });
        //dataGrid.setSizeFull();
        dataGrid.setColumns(GENRE_LABEL);
        dataGrid.getColumn(GENRE_LABEL).setHeaderCaption(GENRE_HEADER);

        /* Disable buttons while nothing selected */
        disableEditAndDeleteButtons();

        /* Setting up an add button */
        addButton.addClickListener(clickEvent -> {
            //TODO: Modal window for adding
            GenreModalWindow modalWindow = new GenreModalWindow(ADD_CAPTION);
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
                GenreModalWindow modalWindow = new GenreModalWindow(EDIT_CAPTION, genreDAO.getById(id));
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
                boolean isDeleted = genreDAO.delete(id);
                if (isDeleted) {
                    update();
                    disableEditAndDeleteButtons();
                } else {
                    Notification.show(DELETE_ERROR_MESSAGE, Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        /* Setup a statistics button */
        showStatsButton.addClickListener(clickEvent -> {
            //TODO: Modal window for showing stats
            UI.getCurrent().addWindow(new GenreStatsModalWindow());
        });

        /* Setup layouts */
        setMargin(true);
        setSizeFull();
        CssLayout buttonsLayout = new CssLayout(addButton, editButton, deleteButton, showStatsButton);
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        mainLayout.addComponents(dataGrid, buttonsLayout);
        mainLayout.setComponentAlignment(dataGrid, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSpacing(true);
        addComponents(mainLayout);
    }

    private void update() {
        BeanItemContainer<Genre> container = new BeanItemContainer<>(Genre.class, genreDAO.getAll());
        dataGrid.setContainerDataSource(container);
    }

    private void enableEditDeleteStatsButtons() {
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        showStatsButton.setEnabled(true);
    }

    private void disableEditAndDeleteButtons() {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
}
