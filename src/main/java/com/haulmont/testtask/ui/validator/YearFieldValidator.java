package com.haulmont.testtask.ui.validator;

import com.vaadin.data.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

public class YearFieldValidator implements Validator {
    @Override
    public void validate(Object value) throws InvalidValueException {
        if (!(value instanceof String || StringUtils.isNumeric(value.toString()))) {
            throw new InvalidValueException("Год должен состоять только из цифр");
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        try {
            if (Integer.valueOf((String) value) < 0 || Integer.valueOf((String) value) > currentYear) {  //Year can't be from future
                throw new InvalidValueException("Укажите год от нулевого до текущего");
            }
        } catch (NumberFormatException e) {
            throw new InvalidValueException("Год должен состоять только из цифр");
        }
    }
}
