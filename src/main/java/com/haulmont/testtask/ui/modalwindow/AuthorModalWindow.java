package com.haulmont.testtask.ui.modalwindow;

import com.haulmont.testtask.dao.AuthorDAO;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.ui.validator.MiddleNameFieldValidator;
import com.haulmont.testtask.ui.validator.TextFieldValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class AuthorModalWindow extends Window {

    private static final String ADD_ERROR_MESSAGE = "Ошибка! Данные не добавлены.";
    private static final String INVALID_STRING_MESSAGE_1 = "Введите от 2 до 30 символов.";
    private static final String INVALID_STRING_MESSAGE_2 = "Введите не более 30 символов.";

    private final TextField firstNameTextField = new TextField("Имя");
    private final TextField lastNameTextField = new TextField("Фамилия");
    private final TextField middleNameTextField = new TextField("Отчество (при наличии)");
    private final Button okButton = new Button(FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отменить");

    public AuthorModalWindow(String caption) {
        super(caption);
        setupModalWindow();

        okButton.addClickListener(clickEvent -> {
            AuthorDAO authorDAO = new AuthorDAO();
            boolean isAdded = authorDAO.add(new Author(
                    firstNameTextField.getValue(),
                    lastNameTextField.getValue(),
                    middleNameTextField.getValue()));
            if (isAdded) close();
            else Notification.show(ADD_ERROR_MESSAGE);
        });
    }

    public AuthorModalWindow(String caption, Author author) {
        super(caption);
        setupModalWindow();

        firstNameTextField.setValue(author.getFirstName());
        lastNameTextField.setValue(author.getLastName());
        middleNameTextField.setValue(author.getMiddleName());
        checkInputValues();

        okButton.addClickListener(clickEvent -> {
            AuthorDAO authorDAO = new AuthorDAO();
            boolean isUpdated = authorDAO.update(new Author(
                    author.getId(),
                    firstNameTextField.getValue(),
                    lastNameTextField.getValue(),
                    middleNameTextField.getValue()));
            if (isUpdated) close();
            else Notification.show(ADD_ERROR_MESSAGE);
        });
    }

    private void setupModalWindow() {
        center();
        okButton.setEnabled(false);
        VerticalLayout mainLayout = new VerticalLayout();
        CssLayout buttonsLayout = new CssLayout();

        firstNameTextField.setSizeFull();
        firstNameTextField.setRequired(true);
        firstNameTextField.setNullSettingAllowed(false);
        firstNameTextField.addValidator(new StringLengthValidator(INVALID_STRING_MESSAGE_1, 2, 30, false));
        firstNameTextField.addValidator(new TextFieldValidator());
        firstNameTextField.addValueChangeListener(valueChangeEvent -> checkInputValues());

        lastNameTextField.setSizeFull();
        lastNameTextField.setRequired(true);
        lastNameTextField.setNullSettingAllowed(false);
        lastNameTextField.addValidator(new StringLengthValidator(INVALID_STRING_MESSAGE_1, 2, 30, false));
        lastNameTextField.addValidator(new TextFieldValidator());
        lastNameTextField.addValueChangeListener(valueChangeEvent -> checkInputValues());

        middleNameTextField.setSizeFull();
        middleNameTextField.setRequired(false);
        middleNameTextField.setNullSettingAllowed(false);
        middleNameTextField.setValue("");
        middleNameTextField.addValidator(new StringLengthValidator(INVALID_STRING_MESSAGE_2, 0, 30, false));
        middleNameTextField.addValidator(new MiddleNameFieldValidator());
        middleNameTextField.addValueChangeListener(valueChangeEvent -> checkInputValues());

        buttonsLayout.addComponents(okButton, cancelButton);
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        cancelButton.addClickListener(clickEvent -> close());

        mainLayout.addComponents(firstNameTextField, lastNameTextField, middleNameTextField, buttonsLayout);
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

    private void checkInputValues() {
        if (firstNameTextField.isValid() && lastNameTextField.isValid() && middleNameTextField.isValid()) {
            okButton.setEnabled(true);
        }
        else okButton.setEnabled(false);
    }
}
