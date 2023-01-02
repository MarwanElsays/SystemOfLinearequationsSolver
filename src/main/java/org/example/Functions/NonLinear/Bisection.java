package org.example.Functions.NonLinear;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Bisection {

    private String function;
    private long time;
    private double xUpper, xLower, xRoot, fUpper, fLower, fRoot, epsilonTolerance, relativeError;
    private int maxIterations = 50, precision;
    StringBuilder stringBuilder = new StringBuilder();

    public Bisection(String function, int precision, double xUpper, double xLower) {

        this.function = function;
        this.precision = precision;
        this.xUpper = getRoundedValue(xUpper);
        this.xLower = getRoundedValue(xLower);
        this.epsilonTolerance = 0.00001;

        long startTime = System.nanoTime();
        this.calcRoot();
        this.time = System.nanoTime() - startTime;

    }

    public Bisection(String function, int precision, double xUpper, double xLower, double relativeError) {

        this.function = function;
        this.precision = precision;
        this.xUpper = getRoundedValue(xUpper);
        this.xLower = getRoundedValue(xLower);
        this.epsilonTolerance = relativeError;

        long startTime = System.nanoTime();
        this.calcRoot();
        this.time = System.nanoTime() - startTime;

    }

    // public Bisection(String function, int precision, double xUpper, double xLower, int SFs) {
    //     this.function = function;
    //     this.precision = precision;
    //     this.xUpper = getRoundedValue(xUpper);
    //     this.xLower = getRoundedValue(xLower);
    //     this.epsilonTolerance = getRoundedValue(0.5 * Math.pow(10, (2-SFs)));

    //     long startTime = System.nanoTime();
    //     this.calcRoot();
    //     this.time = System.nanoTime() - startTime;

    // }

    private double getRoundedValue(double value){
        return BigDecimal.valueOf(value).setScale(this.precision , RoundingMode.HALF_UP).doubleValue();
    }
    private double function(double x){
        Expression e = new ExpressionBuilder(this.function)
                .variables("x", "pi", "e").build()
                .setVariable("x", x)
                .setVariable("pi", Math.PI)
                .setVariable("e", Math.E);
        return getRoundedValue(e.evaluate());
    }

    private void calcRoot(){
        stringBuilder.append("Finding the root between ").append(xLower).append(" and ").append(xLower).append("\n\n");
        if (function(this.xLower) * function(this.xUpper) > 0){
            stringBuilder.append("Unacceptable Guess...\n" + "There is no root between ").append(xLower).append(" and ").append(xUpper).append("\n\n");
            return;
        }
        else if(function(this.xLower) == 0){
            this.xRoot = this.xLower;
            stringBuilder.append("We Found The Root, Which Is Equal : ").append(xRoot).append("\n\n");
            return;
        }
        else if(function(this.xUpper) == 0){
            this.xRoot = this.xUpper;
            stringBuilder.append("We Found The Root, Which Is Equal : ").append(xRoot).append("\n\n");
            return;
        }

        stringBuilder.append("Acceptable Guess, now let's iterate\n\n");

        int iteration = 1;
        this.xRoot = getRoundedValue((this.xUpper + this.xLower) / 2);

        stringBuilder.append("Iteration #").append(iteration).append("\n\n").append("Calculating the new value of xRoot");

        this.fUpper = function(this.xUpper); this.fLower = function(this.xLower); this.fRoot = function(this.xRoot);
        this.relativeError = Double.MAX_VALUE;
        double prevRoot = xRoot;
        this.maxIterations--;
        boolean flag = false;

        while(iteration < maxIterations) {

            iteration++;
            if((this.fRoot == 0) || (this.relativeError <= this.epsilonTolerance)){
                stringBuilder.append("We found the exact root between ").append(xLower).append(" and ").append(xUpper).append("\n\n");
                flag = (this.fRoot == 0);
                break;
            }
            if(this.fUpper * this.fRoot > 0){
                stringBuilder.append("After Calculating the new value of xRoot, we will observe (that f(xRootNew) * f(xLower) > 0), so the root is between ").append(xRoot).append(" and ").append(xUpper).append("\n\n");
                this.xUpper = this.xRoot;
                this.fUpper = this.fRoot;
            }
            else if(this.fUpper * this.fRoot < 0){
                stringBuilder.append("After Calculating the new value of xRoot, we will observe (that f(xRootNew) * f(xLower) < 0), so the root is between ").append(xLower).append(" and ").append(xRoot).append("\n\n");
                this.xLower = this.xRoot;
                this.fLower = this.fRoot;
            }
            this.xRoot = (this.xUpper + this.xLower) / 2;
            stringBuilder.append("Iteration #").append(iteration).append("\n\n").append("Calculating the new value of xRoot");
            this.fRoot = function(this.xRoot);
            if(this.xRoot != 0){
                this.relativeError = getRoundedValue(Math.abs ((this.xRoot - prevRoot) / this.xRoot) * 100);
                stringBuilder.append("Calculating the relative error...\n" + "The new value of relative error equals ").append(relativeError).append("\n\n");
            }
            prevRoot = xRoot;
            this.maxIterations--;
        }
        if(flag){
            stringBuilder.append("We found the exact root : ").append(xRoot).append("\n\n");
        }
        else if (iteration == maxIterations){
            stringBuilder.append("We have reached the maximum of iterations, so the approximate value of the root equals ").append(xRoot).append(" with relative error equals").append(relativeError).append("%");
        }else{
            stringBuilder.append("The approximate value of the root equals ").append(xRoot).append(" with relative error equals ").append(relativeError).append("%");
        }

    }

    public double getRoot(){return this.xRoot;}

    public String getTime(){return String.valueOf(this.time);}

    public String getSteps() {return stringBuilder.toString();}

}