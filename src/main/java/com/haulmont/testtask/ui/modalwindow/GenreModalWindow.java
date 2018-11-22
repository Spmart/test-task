package com.haulmont.testtask.ui.modalwindow;

import com.haulmont.testtask.dao.GenreDAO;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.ui.validator.TextFieldValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GenreModalWindow extends Window {

    private static final String ADD_ERROR_MESSAGE = "Ошибка! Данные не добавлены.";
    private static final String INVALID_STRING_MESSAGE = "Введите от 2 до 50 символов.";
    private static final String UNNECESSARY_SPACES_MESSAGE = "В начале и в конце поля не должно быть пробелов";

    private final TextField genreTextField = new TextField("Жанр");
    private final Button okButton = new Button(FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отменить");

    public GenreModalWindow(String caption) {
        super(caption);
        setupModalWindow();

        okButton.addClickListener(clickEvent -> {
            GenreDAO genreDAO = new GenreDAO();
            boolean isAdded = genreDAO.add(new Genre(genreTextField.getValue()));
            if (isAdded) close();
            else Notification.show(ADD_ERROR_MESSAGE);
        });
    }

    public GenreModalWindow(String caption, Genre genre) {
        super(caption);
        setupModalWindow();

        genreTextField.setValue(genre.getName());
        checkInputValue();

        okButton.addClickListener(clickEvent -> {
            GenreDAO genreDAO = new GenreDAO();
            boolean isUpdated = genreDAO.update(new Genre(genre.getId(), genreTextField.getValue()));
            if (isUpdated) close();
            else Notification.show(ADD_ERROR_MESSAGE);
        });
    }

    private void setupModalWindow() {
        center();
        okButton.setEnabled(false);
        VerticalLayout mainLayout = new VerticalLayout();
        CssLayout buttonsLayout = new CssLayout();

        genreTextField.setSizeFull();
        genreTextField.setRequired(true);
        genreTextField.setNullSettingAllowed(false);
        genreTextField.addValidator(new StringLengthValidator(INVALID_STRING_MESSAGE, 2, 50, false));
        genreTextField.addValidator(new RegexpValidator("^[^\\s](.*)[^\\s]$", UNNECESSARY_SPACES_MESSAGE));
        genreTextField.addValidator(new TextFieldValidator());
        genreTextField.addValueChangeListener(valueChangeEvent -> checkInputValue());

        buttonsLayout.addComponents(okButton, cancelButton);
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        cancelButton.addClickListener(clickEvent -> close());

        mainLayout.addComponents(genreTextField, buttonsLayout);
        mainLayout.setSpacing(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setMargin(true);

        setSizeUndefined();
        setContent(mainLayout);
        setResizable(false);
        setClosable(false);
        setModal(true);
    }

    private void checkInputValue() {
        if (genreTextField.isValid()) okButton.setEnabled(true);
        else okButton.setEnabled(false);
    }
}
