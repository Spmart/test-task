package com.haulmont.testtask.ui.validator;

import com.vaadin.data.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

public class YearFieldValidator implements Validator {
    @Override
    public void validate(Object value) throws InvalidValueException {
        if (!(value instanceof String && StringUtils.isNumeric(value.toString()))) {
            throw new InvalidValueException("Invalid value!");
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (Integer.valueOf((String) value) > currentYear) {
            throw new InvalidValueException("Invalid value!");
        }
    }
}
