package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.AuthorDAO;
import com.haulmont.testtask.dao.BookDAO;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.ui.MainUI;
import com.haulmont.testtask.ui.modalwindow.BookModalWindow;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.*;

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
    private AuthorDAO authorDAO = new AuthorDAO();
    private Grid dataGrid = new Grid();
    private Button addButton = new Button(ADD_CAPTION);
    private Button editButton = new Button(EDIT_CAPTION);
    private Button deleteButton = new Button(DELETE_CAPTION);

    private final List<String> publishersList = new LinkedList<>(Arrays.asList("Москва", "Питер", "O`Reilly"));
    private TextField filterByNameTextField = new TextField();
    private ComboBox filterByAuthorComboBox = new ComboBox();
    private ComboBox filterByPublisherComboBox = new ComboBox();

    public BookForm(MainUI mainUI) {
        this.mainUI = mainUI;
        VerticalLayout mainLayout = new VerticalLayout();
        update();

        /* Setup a search bar */
        CssLayout searchBar = setupSearchBar();

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
            BookModalWindow modalWindow = new BookModalWindow(ADD_CAPTION);
            UI.getCurrent().addWindow(modalWindow);
            modalWindow.addCloseListener(closeEvent -> {
                update();
                disableEditAndDeleteButtons();
            });
        });

        /* Setup an edit button */
        editButton.addClickListener(clickEvent -> {
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
        mainLayout.addComponents(searchBar, dataGrid, buttonsLayout);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(searchBar, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setSpacing(true);
        addComponents(mainLayout);
    }

    private CssLayout setupSearchBar() {
        final String BOOKNAME_INPUT_PROMPT = "Название";
        final String AUTHOR_INPUT_PROMPT = "Автор";
        final String PUBLISHER_INPUT_PROMPT = "Издатель";

        final String DEFAULT_COMBO_BOX_ITEM = "default";



        final Map<String, String> filter = new HashMap<>();


        Button applyButton = new Button(FontAwesome.CHECK);
        Button clearButton = new Button(FontAwesome.TIMES);

        filterByNameTextField.setInputPrompt(BOOKNAME_INPUT_PROMPT);

        filterByAuthorComboBox.setContainerDataSource(new BeanItemContainer<>(Author.class, authorDAO.getAll()));
        filterByAuthorComboBox.setNullSelectionAllowed(true);
        filterByAuthorComboBox.setNullSelectionItemId(DEFAULT_COMBO_BOX_ITEM);
        filterByAuthorComboBox.setRequired(false);
        filterByAuthorComboBox.setInputPrompt(AUTHOR_INPUT_PROMPT);

        filterByPublisherComboBox.setContainerDataSource(new BeanItemContainer<>(String.class, publishersList));
        filterByPublisherComboBox.setNullSelectionAllowed(true);
        filterByPublisherComboBox.setNullSelectionItemId(DEFAULT_COMBO_BOX_ITEM);
        filterByPublisherComboBox.setRequired(false);
        filterByPublisherComboBox.setInputPrompt(PUBLISHER_INPUT_PROMPT);

        applyButton.addClickListener(clickEvent -> {
            filter.clear();
            if (filterByNameTextField.getValue() != null && filterByNameTextField.getValue().length() > 0)
                filter.put(BOOK_NAME_LABEL, filterByNameTextField.getValue());
            if (filterByAuthorComboBox.getValue() != null) {
                String value = filterByAuthorComboBox.getValue().toString();
                value = value.split(" ")[0];
                filter.put(AUTHOR_NAME_LABEL, value);
            }
            if (filterByPublisherComboBox.getValue() != null) filter.put(PUBLISHER_LABEL, filterByPublisherComboBox.getValue().toString());

            BeanItemContainer<Book> container = new BeanItemContainer<>(Book.class, bookDAO.getAll(filter));
            dataGrid.setContainerDataSource(container);
        });

        clearButton.addClickListener(clickEvent -> {
            update();
            filterByNameTextField.clear();
            filterByAuthorComboBox.select(DEFAULT_COMBO_BOX_ITEM);
            filterByPublisherComboBox.select(DEFAULT_COMBO_BOX_ITEM);
        });

        CssLayout searchBar = new CssLayout(filterByNameTextField, filterByAuthorComboBox, filterByPublisherComboBox, clearButton, applyButton);
        searchBar.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        return searchBar;
    }

    public void update() {
        BeanItemContainer<Book> container = new BeanItemContainer<>(Book.class, bookDAO.getAll());
        dataGrid.setContainerDataSource(container);
    }

    public void updateSearchBar() {
        filterByAuthorComboBox.setContainerDataSource(new BeanItemContainer<>(Author.class, authorDAO.getAll()));
        filterByPublisherComboBox.setContainerDataSource(new BeanItemContainer<>(String.class, publishersList));
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
