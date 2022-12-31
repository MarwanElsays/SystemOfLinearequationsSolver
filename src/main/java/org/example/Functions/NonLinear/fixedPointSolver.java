package org.example.Functions.NonLinear;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class fixedPointSolver {
    private String stringExp;
    private double initialguess;
    private double eps;
    private int noOfIter;
    private double Xr;
    private double time;
    private int significantfigures;

    public fixedPointSolver(String stringExp, double initialguess, double eps, int noOfIter,int significantfigures) {
        this.stringExp = stringExp;
        this.initialguess = initialguess;
        this.eps = eps;
        this.noOfIter = noOfIter;
        this.significantfigures = significantfigures;
        double timeBeforeExecution = System.currentTimeMillis();
        Solve();
        this.time = System.currentTimeMillis() - timeBeforeExecution;
    }

    public double getXr() {
        return Xr;
    }

    public String getTime() {
        return String.valueOf(time);
    }

    public void Solve(){
        int iteration = 0;
        double relError = 100;
        double xOld = initialguess;
        double xr;

        do{
            xr = g(xOld);
            if(xr != 0)relError = relativeError(xOld,xr);
            iteration++;
            xOld = xr;
        }while(iteration < noOfIter && relError > eps);

        this.Xr = xr;
    }

    public double g(double x){
        Expression e = new ExpressionBuilder(stringExp)
                .variables("x")
                .build()
                .setVariable("x", x);

        double result = e.evaluate();
        return getRoundedValue(result);
    }

    public double relativeError(double xOld,double xr){
        return (Math.abs(xr-xOld)/xr)*100;
    }

    private double getRoundedValue(double x){
        return BigDecimal.valueOf(x).setScale(significantfigures , RoundingMode.HALF_UP).doubleValue();
    }
}
