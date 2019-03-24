package com.example.jaroslav.cfttask;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class NumericExpression {
    private String expression;
    private String textError;
    private static int iteratorTokens;
    private static int iteratorNumbers;
    private static String tokens;
    private static ArrayList<BigDecimal> numbers;
    private ArrayList<BigDecimal> operands = new ArrayList<>();
    private ArrayList<Operator> operators = new ArrayList<>();

    private NumericExpression() { }

    NumericExpression(String text) {
        setText(text);
    }

    public void setText(String text) {
        if (text.contains(",")) {
            text = text.replace(',','.');
        }
        expression = "(" + text + ")";
        if (ErrorExpression.haveError(text)) {
            textError = ErrorExpression.getTextError();
            return;
        }
        numbers = new ArrayList<>();
        iteratorTokens = 0;
        iteratorNumbers = 0;
        String[] strNumbers = expression.split("[*()/+]+|(?<=[0-9])-(?=[0-9])");
        for (String number : strNumbers) {
            if (!number.equals("")) {
                numbers.add(new BigDecimal(number));
            }
        }
        tokens = Pattern.compile("(?<=[+*/(])-?[0-9.]+|[0-9.]+").matcher(text).replaceAll("x");
    }

    public String getText() {
        return expression;
    }

    public String getResult() {
        if (textError != null) { return textError; }

        while (iteratorTokens < tokens.length()) {
            char token = tokens.charAt(iteratorTokens);
            switch (token) {
                case '(':
                    iteratorTokens ++;
                    NumericExpression subExpression = new NumericExpression();
                    operands.add(new BigDecimal(subExpression.getResult()));
                    break;
                case ')':
                    iteratorTokens ++;
                    return compute();
                case 'x':
                    operands.add(numbers.get(iteratorNumbers));
                    iteratorNumbers ++;
                    iteratorTokens ++;
                    break;
                default:
                    operators.add(new Operator(token));
                    iteratorTokens ++;
                    break;
            }
        }
        return compute();
    }

    private String compute() {
        if (operators.size() == 0 & operands.size() == 1) {
            return operands.get(0).toString();
        }
        int i = 0;
        while (i < operators.size()){
            if (operators.get(i).getPriority() == 1) {
                BigDecimal number = operators.get(i).compute(operands.get(i), operands.get(i + 1));
                operators.remove(i);
                operands.remove(i);
                operands.set(i,number);
            } else {
                i++;
            }
        }
        i = 0;
        while (i < operators.size()) {
            BigDecimal number = operators.get(i).compute(operands.get(i), operands.get(i + 1));
            operators.remove(i);
            operands.remove(i);
            operands.set(i,number);
        }
        return operands.get(0).toString();
    }
}
