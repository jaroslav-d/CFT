package com.example.jaroslav.cfttask;


import java.math.BigDecimal;

public class Operator {
    private String sign;
    private int priority;

    Operator(String sign) {
        if (sign.matches("[+\\-/*]")) {
            this.sign = sign;
        }
        setPriority(this.sign);
    }

    Operator(char sign) {
        if (String.valueOf(sign).matches("[+\\-/*]")) {
            this.sign = String.valueOf(sign);
        }
        setPriority(this.sign);
    }

    public BigDecimal compute(BigDecimal x, BigDecimal y) {
        switch (sign) {
            case "+":
                return x.add(y);
            case "-":
                return x.subtract(y);
            case "*":
                return x.multiply(y);
            case "/":
                return x.divide(y,5, BigDecimal.ROUND_UP);
        }
        return null;
    }

    public int getPriority() {
        return priority;
    }

    private void setPriority(String sign) {
        switch (sign) {
            case "+":
                priority = 0;
                break;
            case "-":
                priority = 0;
                break;
            case "*":
                priority = 1;
                break;
            case "/":
                priority = 1;
                break;
        }
    }
}
