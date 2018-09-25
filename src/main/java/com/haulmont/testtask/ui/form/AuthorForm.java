package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.AuthorDAO;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.ui.MainUI;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class AuthorForm extends FormLayout {

    private static final String FIRST_NAME_LABEL = "firstName";
    private static final String LAST_NAME_LABEL = "lastName";
    private static final String MIDDLE_NAME_LABEL = "middleName";

    private static final String FIRST_NAME_HEADER = "Имя";
    private static final String LAST_NAME_HEADER = "Фамилия";
    private static final String MIDDLE_NAME_HEADER = "Отчество";

    private MainUI mainUI;
    private AuthorDAO authorDAO = new AuthorDAO();
    private Grid dataGrid = new Grid();
    private Button addButton = new Button("Добавить");
    private Button editButton = new Button("Изменить");
    private Button deleteButton = new Button("Удалить");

    public AuthorForm(MainUI mainUI) {
        this.mainUI = mainUI;
        VerticalLayout mainLayout = new VerticalLayout();
        update();  //SHOULD be there!

        /* Setup a grid */
        dataGrid.addSelectionListener(selectionEvent -> {
            if (selectionEvent.getSelected() != null) {
                enableEditAndDeleteButtons();
            }
        });
        //dataGrid.setSizeFull();
        dataGrid.setColumns(FIRST_NAME_LABEL, LAST_NAME_LABEL, MIDDLE_NAME_LABEL);
        dataGrid.getColumn(FIRST_NAME_LABEL).setHeaderCaption(FIRST_NAME_HEADER);
        dataGrid.getColumn(LAST_NAME_LABEL).setHeaderCaption(LAST_NAME_HEADER);
        dataGrid.getColumn(MIDDLE_NAME_LABEL).setHeaderCaption(MIDDLE_NAME_HEADER);

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

    private void update() {
        BeanItemContainer<Author> container = new BeanItemContainer<>(Author.class, authorDAO.getAll());
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
