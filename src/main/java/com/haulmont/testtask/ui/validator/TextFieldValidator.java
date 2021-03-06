package com.haulmont.testtask.ui.validator;

import com.vaadin.data.Validator;
import org.apache.commons.lang3.StringUtils;

public class TextFieldValidator implements Validator {
    @Override
    public void validate(Object value) throws InvalidValueException {
        if (!(value instanceof String) || StringUtils.isWhitespace(value.toString()) || StringUtils.isNumeric(value.toString())) {
            throw new InvalidValueException("Поле не должно состоять только из цифр или пробелов");
        }
    }
}
