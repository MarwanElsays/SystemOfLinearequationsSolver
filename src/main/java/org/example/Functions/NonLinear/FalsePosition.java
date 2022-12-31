package org.example.Functions.NonLinear;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

// False Position is one of bracketing methods to find the roots for a non-linear function
public class FalsePosition {
    private final Expression expression;
    private final int precision;
    private double result;
    private long time;
    StringBuilder stringBuilder = new StringBuilder();

    public FalsePosition(String function , int precision){
        expression = new ExpressionBuilder(function).variable("x").build();
        this.precision = precision;
    }

    public void solve(double xLower , double xUpper , int maxIterations , double epsilon){
        stringBuilder.append("Finding the root between ").append(xLower).append(" and ").append(xLower).append("\n\n");
        if (f(xLower) * f(xUpper) >= 0){
            // If this condition is true, it means there is no root between lower and upper bound ( initial guess)
            stringBuilder.append("Unacceptable Guess...\n" + "There is no root between ").append(xLower).append(" and ").append(xUpper).append("\n\n");
            return;
        }
        stringBuilder.append("Acceptable Guess, now let's iterate\n\n");
        // if the value of (f(xLower) * f(xUpper)) is less than 0, it means there is a root between lower and upper bound ( initial guess)
        long startTime = System.nanoTime();
        // We will iterate until the relative error is less than some epsilon, so we need two variables,
        // one for the new value of xRoot, and other one for the old value of xRoot
        // Or we will iterate until we reach the maximum number of iterations
        double xRootOld , xRootNew = 0, relativeError = Double.MAX_VALUE;
        int iteration = 0;
        boolean stop = false;
        while (!stop && iteration < maxIterations){
            iteration++;
            stringBuilder.append("Iteration #").append(iteration).append("\n\n").append("Calculating the new value of xRoot");
            xRootOld = xRootNew;
            // To avoid Runtime Errors, we must check we will not divide by zero
            stringBuilder.append("First, let's check that the condition (f(xUpper) - f(xLower) == 0) is false to avoid dividing by zero...\n");
            if(f(xUpper) - f(xLower) == 0){
                xRootNew = getRoundedValue((xUpper + xLower) / 2);
                stringBuilder.append("f(xUpper) - f(xLower) == 0 already, so the new value of xRoot will be (xUpper + xLower) / 2 which equals ").append(xRootNew).append("\n\n");
            }
            else{
                // Linear Interpolation
                xRootNew = getRoundedValue(xLower - ((f(xLower) * (xUpper - xLower)) / (f(xUpper) - f(xLower))));
                stringBuilder.append("f(xUpper) - f(xLower) != 0, so the new value of xRoot will be xLower - ((f(xLower) * (xUpper - xLower)) / (f(xUpper) - f(xLower))) which equals ").append(xRootNew).append("\n\n");
            }
            // Same as linear interpolation equation, we must check we will not divide by zero to avoid Runtime errors
            if(xRootNew != 0) {
                relativeError = getRoundedValue(Math.abs((xRootNew - xRootOld) / xRootNew) * 100);
                stringBuilder.append("Calculating the relative error...\n" + "The new value of relative error equals ").append(relativeError).append("\n\n");
            }
            if (f(xRootNew) == 0){
                // We finally reach the root, so there is no need to iterate more
                stringBuilder.append("We found the exact root between ").append(xLower).append(" and ").append(xLower).append("\n\n");
                break;
            }else if(f(xRootNew) * f(xLower) < 0){
                // The root is between xLower and xRootNew
                stringBuilder.append("After Calculating the new value of xRoot, we will observe (that f(xRootNew) * f(xLower) < 0), so the root is between ").append(xLower).append(" and ").append(xRootNew).append("\n\n");
                xUpper = xRootNew;
            }else{
                // The root is between xRootNew and xUpper
                stringBuilder.append("After Calculating the new value of xRoot, we will observe (that f(xRootNew) * f(xLower) > 0), so the root is between ").append(xRootNew).append(" and ").append(xUpper).append("\n\n");
                xLower = xRootNew;
            }
            // check if the relative error is less than the epsilon
            stringBuilder.append("Before going to the next iteration we need to check first the breaking condition (relativeError <= epsilon)\n\n");
            stop = relativeError <= epsilon;
        }
        this.time = System.nanoTime() - startTime;
        result = xRootNew;
        if (iteration == maxIterations){
            stringBuilder.append("We have reached the maximum of iterations, so the approximate value of the root equals ").append(result).append(" with relative error equals").append(relativeError).append("%");
        }else{
            stringBuilder.append("The approximate value of the root equals ").append(result).append(" with relative error equals ").append(relativeError).append("%");
        }
    }

    public double getRoot(){
        return result;
    }

    public String getTime(){
        return String.valueOf(this.time);
    }

    private double f(double x) {
        return getRoundedValue(expression.setVariable("x", x).evaluate());
    }

    private double getRoundedValue(double x){
        return BigDecimal.valueOf(x).setScale(precision , RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public String toString(){
        return stringBuilder.toString();
    }
}
