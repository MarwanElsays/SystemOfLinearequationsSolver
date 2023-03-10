package org.example.Functions.NonLinear;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Newton_Raphson {

    private Expression expression;
    private int significantFigures;
    private long time;
    private String steps = "Solving By Newton-Raphson Method:\nXi+1 = Xi - f(x) / df(x)\n";

    public Newton_Raphson(String function, int significantFigures) {
        expression = new ExpressionBuilder(function).variable("x").build();
        this.significantFigures = significantFigures;
    }

    public void solve(double initialGuess, double epsilon, int maxIterations) {
        time = System.nanoTime();
        double relativeError = 100;
        int iterations = 0;
        double x = initialGuess;
        double xNew = 0;
        while (relativeError > epsilon && iterations < maxIterations && getRoundedValue(df(x)) != 0) {
            double f = getRoundedValue(f(x));
            double df = getRoundedValue(df(x));
            xNew = getRoundedValue(x - f / df);
            if (xNew != 0)
                relativeError = Math.abs((xNew - x) / xNew)*100;
            iterations++;
            steps += "Iteration " + iterations + ": " + "Xi+1 = " + x + " - " + f + " / " + df + " = " + xNew + "\n";
            steps += "error = " + getRoundedValue(relativeError) + "%\n\n";
            x = xNew;
        }
        steps+="The Root = " + xNew;
        steps+="\nThe calculated root is "+ checkValid(xNew);
        time = System.nanoTime() - time;
    }

    public String getTime(){
        return String.valueOf(time);
    }

    public String getSteps(){
        return steps;
    }
    
    private double f(double x) {
        return getRoundedValue(expression.setVariable("x", x).evaluate());
    }

    private double df(double x) {
        double h = 0.11;
        return (-f(x + 2 * h) + 8 * f(x + h) - 8 *  f(x - h) + f(x - 2 * h)) / (12 * h);
    }


    private String checkValid(double xNew) {
        if (Math.abs(f(xNew)) < 0.01)
            return "valid";
        return "invalid";
    }

    private double getRoundedValue(double x){
        return BigDecimal.valueOf(x).setScale(significantFigures , RoundingMode.HALF_UP).doubleValue();
    }
}
