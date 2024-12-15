package com.example.database_final_javafx.utils;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class InputFormatter {

    public static Boolean areStringsEmpty(String... strings) {
        Boolean result = false;
        for(String string : strings){
            if(string == null || string.trim().isEmpty()) {
                result = true;
            }
        }
        return  result;
    }

    public static TextFormatter<Integer> createIntegerInputFormatter() {
        return new TextFormatter<>(
                change -> {
                    String newText = change.getControlNewText();

                    if (newText.isEmpty()) {
                        return change;
                    }

                    if (newText.matches("[1-9]\\d*")) {
                        return change;
                    }

                    return null;
                });
    }

}
