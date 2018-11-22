package com.haulmont.testtask.ui.validator;

import com.vaadin.data.Validator;
import org.apache.commons.lang3.StringUtils;

public class MiddleNameFieldValidator implements Validator {
    @Override
    public void validate(Object value) throws Validator.InvalidValueException {
        if (!(value instanceof String) || (!value.toString().isEmpty()
                && (StringUtils.isWhitespace(value.toString()) || !StringUtils.isAlpha(value.toString())))) {
            throw new Validator.InvalidValueException("Invalid value!");
        }
    }
}
