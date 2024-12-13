package com.example.database_final_javafx.utils;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class InputFormatter {
    public static TextFormatter<String> createTextInputFormatter() {
        return  new TextFormatter<>(
                change -> {
                    String newText = change.getControlNewText();
                    return newText.trim().isEmpty() ? null : change;
                });
    }

    public static TextFormatter<Integer> createIntegerInputFormatter() {
        return new TextFormatter<>(new IntegerStringConverter(), 1,
                change -> {
                    // Allow only digits and ensure the number is greater than 0
                    String newText = change.getControlNewText();
                    if (newText.matches("\\d*")) { // Allow only numeric values
                        try {
                            int newValue = Integer.parseInt(newText);
                            return newValue > 0 ? change : null; // Allow only numbers > 0
                        } catch (NumberFormatException e) {
                            return null; // Reject invalid numbers
                        }
                    } else {
                        return null; // Reject non-numeric input
                    }
                });
    }
}
