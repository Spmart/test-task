package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.GenreDAO;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.ui.MainUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GenreForm extends FormLayout {

    private static final String GENRE_LABEL = "name";

    private static final String GENRE_HEADER = "Жанр";

    private MainUI mainUI;
    private GenreDAO genreDAO = new GenreDAO();
    private Grid dataGrid = new Grid();
    private Button addButton = new Button("Добавить");
    private Button editButton = new Button("Изменить");
    private Button deleteButton = new Button("Удалить");
    private Button showStatsButton = new Button("Показать статистику");

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

        /* Setup a statistics button */
        showStatsButton.addClickListener(clickEvent -> {
            //TODO: Modal window for showing stats
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

    private void disableEditDeleteStatsButtons() {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        showStatsButton.setEnabled(false);
    }
}
