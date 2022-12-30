package org.example.Functions.NonLinear;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FalsePosition {
    private final Expression expression;
    private final int precision;

    public FalsePosition(String function , int precision){
        expression = new ExpressionBuilder(function).variable("x").build();
        this.precision = precision;
    }

    public void solve(double xLower , double xUpper , int maxIterations , double epsilon){
        if (f(xLower) * f(xUpper) >= 0){
            System.out.println("Unacceptable");
            return;
        }
        double xRootOld , xRootNew = 0, relativeError = Double.MAX_VALUE;
        boolean stop = false;
        int iteration = 0;
        while (!stop && iteration < maxIterations){
            iteration++;
            xRootOld = xRootNew;
            if(f(xUpper) - f(xLower) == 0){
                xRootNew = getRoundedValue((xUpper + xLower) / 2);
            }
            else{
                xRootNew = getRoundedValue(xLower - ((f(xLower) * (xUpper - xLower)) / (f(xUpper) - f(xLower))));
            }
            if(xRootNew != 0) {
                relativeError = getRoundedValue(Math.abs((xRootNew - xRootOld) / xRootNew) * 100);
            }
            if (f(xRootNew) == 0){
                break;
            }else if(f(xRootNew) * f(xLower) < 0){
                xUpper = xRootNew;
            }else{
                xLower = xRootNew;
            }
            stop = relativeError <= epsilon;
        }
        System.out.println(xRootNew);
        System.out.println("After" + iteration + "Iterations");
    }

    private double f(double x) {
        return getRoundedValue(expression.setVariable("x", x).evaluate());
    }

    private double getRoundedValue(double x){
        return BigDecimal.valueOf(x).setScale(precision , RoundingMode.HALF_UP).doubleValue();
    }

}
