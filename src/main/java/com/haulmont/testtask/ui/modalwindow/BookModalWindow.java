package com.haulmont.testtask.ui.modalwindow;

import com.haulmont.testtask.dao.AuthorDAO;
import com.haulmont.testtask.dao.BookDAO;
import com.haulmont.testtask.dao.GenreDAO;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.ui.validator.TextFieldValidator;
import com.haulmont.testtask.ui.validator.YearFieldValidator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BookModalWindow extends Window {

    private static final String ADD_ERROR_MESSAGE = "Ошибка! Данные не добавлены.";
    private static final String INVALID_BOOKNAME_MESSAGE = "Введите название от 1 до 100 символов.";
    private static final String INVALID_YEAR_MESSAGE = "Неправильно введен год. Корректные значения - от 0 до текущего.";
    private static final String INVALID_CITY_MESSAGE = "Название города должно содержать от 2 до 30 символов";
    private static final String UNNECESSARY_SPACES_MESSAGE = "В начале и в конце поля не должно быть пробелов";

    private static final List<String> publishersList = new LinkedList<>(Arrays.asList("Москва", "Питер", "O`Reilly"));

    private final TextField bookNameTextField = new TextField("Название");
    private final ComboBox authorNameComboBox = new ComboBox("Автор");
    private final ComboBox genreComboBox = new ComboBox("Жанр");
    private final ComboBox publisherComboBox = new ComboBox("Издатель");
    private final TextField yearTextField = new TextField("Год");
    private final TextField cityTextField = new TextField("Город");
    private final Button okButton = new Button(FontAwesome.CHECK);
    private final Button cancelButton = new Button("Отменить");

    public BookModalWindow(String caption) {
        super(caption);
        setupModalWindow();

        okButton.addClickListener(clickEvent -> {
            BookDAO bookDAO = new BookDAO();
            boolean isAdded = bookDAO.add(new Book(
                    bookNameTextField.getValue(),
                    (Author) authorNameComboBox.getValue(),
                    (Genre) genreComboBox.getValue(),
                    (String) publisherComboBox.getValue(),
                    Short.valueOf(yearTextField.getValue()),
                    cityTextField.getValue()));
            if (isAdded) close();
            else Notification.show(ADD_ERROR_MESSAGE);
        });

    }

    public BookModalWindow(String caption, Book book) {
        super(caption);
        setupModalWindow();

        bookNameTextField.setValue(book.getName());
        yearTextField.setValue(String.valueOf(book.getYear()));
        cityTextField.setValue(book.getCity());

        okButton.addClickListener(clickEvent -> {
            BookDAO bookDAO = new BookDAO();
            boolean isUpdated = bookDAO.update(new Book(
                    book.getId(),
                    bookNameTextField.getValue(),
                    (Author) authorNameComboBox.getValue(),
                    (Genre) genreComboBox.getValue(),
                    (String) publisherComboBox.getValue(),
                    Short.valueOf(yearTextField.getValue()),
                    cityTextField.getValue()));
            if (isUpdated) close();
            else Notification.show(ADD_ERROR_MESSAGE);
        });
    }

    private void setupModalWindow() {
        center();
        okButton.setEnabled(false);
        VerticalLayout mainLayout = new VerticalLayout();
        CssLayout buttonsLayout = new CssLayout();

        GenreDAO genreDAO = new GenreDAO();
        AuthorDAO authorDAO = new AuthorDAO();

        bookNameTextField.setSizeFull();
        bookNameTextField.setRequired(true);
        bookNameTextField.setNullSettingAllowed(false);
        bookNameTextField.addValidator(new StringLengthValidator(INVALID_BOOKNAME_MESSAGE, 1, 100, false));
        bookNameTextField.addValidator(new TextFieldValidator());
        bookNameTextField.addValidator(new RegexpValidator("^[^\\s](.*)[^\\s]$", UNNECESSARY_SPACES_MESSAGE));
        bookNameTextField.addValueChangeListener(valueChangeEvent -> checkInputValues());

        authorNameComboBox.setSizeFull();
        authorNameComboBox.setContainerDataSource(new BeanItemContainer<>(Author.class, authorDAO.getAll()));
        authorNameComboBox.setRequired(true);
        authorNameComboBox.setNullSelectionAllowed(false);
        authorNameComboBox.addValueChangeListener(valueChangeEvent -> checkInputValues());

        genreComboBox.setSizeFull();
        genreComboBox.setContainerDataSource(new BeanItemContainer<>(Genre.class, genreDAO.getAll()));
        genreComboBox.setRequired(true);
        genreComboBox.setNullSelectionAllowed(false);
        genreComboBox.addValueChangeListener(valueChangeEvent -> checkInputValues());

        publisherComboBox.setSizeFull();
        publisherComboBox.setContainerDataSource(new BeanItemContainer<>(String.class, publishersList));
        publisherComboBox.setRequired(true);
        publisherComboBox.setNullSelectionAllowed(false);
        publisherComboBox.addValueChangeListener(valueChangeEvent -> checkInputValues());

        yearTextField.setSizeFull();
        yearTextField.setRequired(true);
        yearTextField.setNullSettingAllowed(false);
        //yearTextField.addValidator(new RegexpValidator("^\\d{4}$", INVALID_YEAR_MESSAGE));
        yearTextField.addValidator(new YearFieldValidator());
        yearTextField.addValueChangeListener(valueChangeEvent -> checkInputValues());

        cityTextField.setSizeFull();
        cityTextField.setRequired(true);
        cityTextField.setNullSettingAllowed(false);
        cityTextField.addValidator(new StringLengthValidator(INVALID_CITY_MESSAGE, 2, 30, false));
        cityTextField.addValidator(new TextFieldValidator());
        cityTextField.addValidator(new RegexpValidator("^[^\\s](.*)[^\\s]$", UNNECESSARY_SPACES_MESSAGE));
        cityTextField.addValueChangeListener(valueChangeEvent -> checkInputValues());


        buttonsLayout.addComponents(okButton, cancelButton);
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        okButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        cancelButton.addClickListener(clickEvent -> close());

        mainLayout.addComponents(bookNameTextField, authorNameComboBox, genreComboBox,
                publisherComboBox, yearTextField, cityTextField, buttonsLayout);
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
        if (bookNameTextField.isValid() && yearTextField.isValid() && cityTextField.isValid() && cityTextField.isValid()) {
            okButton.setEnabled(true);
        }
        else okButton.setEnabled(false);
    }
}
