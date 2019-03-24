package com.example.jaroslav.cfttask;

import java.util.regex.Pattern;

public class ErrorExpression {
    private static String textError;

    public static boolean haveError(String text) {
        if (text.isEmpty()) {
            textError = "empty text";
            return true;
        }
        textError = searchBrackets(text);
        if (!textError.isEmpty()) {
            return true;
        }
        textError = searchOperators(text);

        return !textError.isEmpty();
    }

    public static String getTextError() {
        return textError;
    }

    private static String searchBrackets(String text) {
        int balance = 0;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) == '(') {
                balance -= 1;
            } else if (text.charAt(i) == ')') {
                balance += 1;
            }
            if (balance == 1) {
                return "excess bracket";
            }
        }
        if (balance != 0) {
            return "not enough brackets";
        }
        return "";
    }

    private static String searchOperators(String text) {
        if (text.contains("=")) {
            return "equal sign is not allowed";
        }
        if (Pattern.compile("[*/+(][*/+]").matcher(text).find() ||
                Pattern.compile("-[*/+)]").matcher(text).find()) {
            return "there are not enough numbers between operators";
        }
        if (Pattern.compile("\\([*/+]").matcher(text).find() ||
                Pattern.compile("[*/+]\\)").matcher(text).find()) {
            return "the operator is now after the bracket";
        }
        if (Pattern.compile("\\(\\)|\\)\\(").matcher(text).find()) {
            return "between brackets is empty";
        }
        if (Pattern.compile("[^0-9+\\-*/().,]").matcher(text).find()) {
            return "expression contain word and non-mathematical symbols";
        }
        return "";
    }
}
