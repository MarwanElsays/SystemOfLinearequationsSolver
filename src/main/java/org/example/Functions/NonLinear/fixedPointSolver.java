package org.example.Functions.NonLinear;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class fixedPointSolver {
    private String gx;
    private String fx;
    private double initialguess;
    private double eps;
    private int noOfIter;
    private double Xr;
    private double time;
    private int significantfigures;
    private String Steps = "";

    public fixedPointSolver(String[] exp, double initialguess, double eps, int noOfIter, int significantfigures) {
        this.fx = exp[0];
        this.gx = exp[1];
        this.initialguess = initialguess;
        this.eps = eps;
        this.noOfIter = noOfIter;
        this.significantfigures = significantfigures;
        double timeBeforeExecution = System.nanoTime();
        Solve();
        this.time = System.nanoTime() - timeBeforeExecution;
    }

    public double getXr() {
        return Xr;
    }

    public String getTime() {
        return String.valueOf(time);
    }

    public String getSteps() {
        return Steps;
    }

    public void Solve() {
        int iteration = 0;
        double relError = 100;
        double xOld = initialguess;
        double xr;

        do {
            xr = eval(xOld, gx);
            iteration++;
            Steps += "Iteration: " + iteration + "\nXi+1 = " + gx.replaceAll("x", String.valueOf(xOld)) + " = " + xr
                    + "\n";
            if (xr != 0)
                relError = relativeError(xOld, xr);
            Steps += "εa% = |(" + xr + " - " + xOld + ")/" + xr + "|" + " *100% = " + relError + "%\n";
            Steps += "-------------------------------------------------------------\n";
            xOld = xr;

        } while (iteration < noOfIter && relError > eps && Math.abs(xr - initialguess) < 100);

        Steps += "The root = " + xr;
        Steps += "\nThe root is " + Validate(xr);
        this.Xr = xr;
    }

    public double eval(double x, String expression) {
        Expression e = new ExpressionBuilder(expression)
                .variables("x")
                .build()
                .setVariable("x", x);

        double result = e.evaluate();
        return getRoundedValue(result);
    }

    public String Validate(double xr) {
        if (Math.abs(eval(xr, fx)) < eps / 100)
            return "valid";
        return "invalid";
    }

    public double relativeError(double xOld, double xr) {
        return (Math.abs((xr - xOld) / xr)) * 100;
    }

    private double getRoundedValue(double x) {
        return BigDecimal.valueOf(x).setScale(significantfigures, RoundingMode.HALF_UP).doubleValue();
    }
}
