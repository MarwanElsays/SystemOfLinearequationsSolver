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

    public Bisection(String function, int precision, double xUpper, double xLower, int SFs) {

        this.function = function;
        this.precision = precision;
        this.xUpper = getRoundedValue(xUpper);
        this.xLower = getRoundedValue(xLower);
        this.epsilonTolerance = getRoundedValue(0.5 * Math.pow(10, (2-SFs)));

        long startTime = System.nanoTime();
        this.calcRoot();
        this.time = System.nanoTime() - startTime;

    }

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

        if (function(this.xLower) * function(this.xUpper) > 0){
            System.out.println("Unacceptable");
            return;
        }
        else if(function(this.xLower) == 0){
            this.xRoot = this.xLower;
            return;
        }
        else if(function(this.xUpper) == 0){
            this.xRoot = this.xUpper;
            return;
        }

        this.xRoot = getRoundedValue((this.xUpper + this.xLower) / 2);
        this.fUpper = function(this.xUpper); this.fLower = function(this.xLower); this.fRoot = function(this.xRoot);
        this.relativeError = Double.MAX_VALUE;
        double prevRoot = xRoot;
        this.maxIterations--;

        while(this.maxIterations != 0) {

            if((this.fRoot == 0) || (this.relativeError <= this.epsilonTolerance)){break;}
            if(this.fUpper * this.fRoot > 0){
                this.xUpper = this.xRoot;
                this.fUpper = this.fRoot;
            }
            else if(this.fUpper * this.fRoot < 0){
                this.xLower = this.xRoot;
                this.fLower = this.fRoot;
            }

            this.xRoot = (this.xUpper + this.xLower) / 2;
            this.fRoot = function(this.xRoot);
            if(this.xRoot != 0){
                this.relativeError = getRoundedValue(Math.abs ((this.xRoot - prevRoot) / this.xRoot) * 100);
            }
            prevRoot = xRoot;
            this.maxIterations--;
        }

    }

    public double getRoot(){return this.xRoot;}

    public long getTime(){return this.time;}

}