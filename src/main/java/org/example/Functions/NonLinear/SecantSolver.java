package org.example.Functions.NonLinear;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.example.Gui.OutputFrame;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SecantSolver {

    OutputFrame outputFrame;

    private Expression ex;
    private String Steps = "";
    private long time = 0;
    private int precision;
  

    public SecantSolver(int precision){
        this.precision = precision;
    }

    //expressionParser takes the string as input and readies the ex for evaluation
    private void expressionParser(String expression){
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(expression);
        expressionBuilder.variable("x");
        ex = expressionBuilder.build();
    }

    //eval evaluates the expression that was parsed with input in the parameter as 'x' in the expression
    public double eval(double input){
        ex.setVariable("x", input);
        return getRoundedValue(ex.evaluate());
    }
    // //if no max iterations is given then the method is overloaded to set it to default value of 50
    // public double solve(String function, double firstPoint, double secondPoint, double absoluteRelativeError){
    //     return this.solve(function, firstPoint, secondPoint, absoluteRelativeError, 50);
    // }

    // //if no absolute relative error is given then the method is overloaded to set it to default value of 0.00001
    // public double solve(String function, double firstPoint, double secondPoint, int maxIteration){
    //     return this.solve(function, firstPoint, secondPoint, 0.00001, maxIteration);
    // }

    // //if neither absolute relative error nor max iterations were given then the method is overloaded to set their default values to 0.00001 and 50 respectively
    // public double solve(String function, double firstPoint, double secondPoint){
    //     return this.solve(function, firstPoint, secondPoint, 0.00001, 50);
    // }

    public double solve(String function, double firstPoint, double secondPoint, double absoluteRelativeError, int maxIteration){
        //at the start of the method the expression is parsed to make the method eval ready for operation
        this.expressionParser(function);
        time = System.nanoTime();
        Steps+="Xi+1 = Xi - ( " + function.replaceAll("x", "(Xi)") + " ) * ( Xi-1 - Xi )) / (( " + function.replaceAll("x", "(Xi-1)") +" ) - " + function.replaceAll("x", "(Xi)") + " ))";

        //if absolute relative error was somehow given negative then it is set to its absolute value
        absoluteRelativeError = Math.abs(absoluteRelativeError);

        double newPoint = 0;
        double tempError = 0;

        for(int i = 0; i < maxIteration; i++){
            //newPoint evaluate secant formula using firstPoint as Xi and secondPoint as Xi-1
            newPoint = getRoundedValue(firstPoint - ((eval(firstPoint) * (secondPoint - firstPoint)) / (eval(secondPoint) - eval(firstPoint))));

            //the absolute relative error of the new value of x is evaluated
            tempError = Math.abs((newPoint - firstPoint) / newPoint) * 100;
            Steps+="\n\nIteration: " + i + "\tXi-1: " + secondPoint + "\tf(Xi-1): " + eval(secondPoint) + "\tXi: " + firstPoint + "\tf(Xi):" + eval(firstPoint) + "\tXi+1: " + newPoint + "\tεa%: " + tempError;

            //Xi-1 = Xi
            secondPoint = firstPoint;
            if(tempError < absoluteRelativeError)
                break;

            //Xi = Xi+1
            firstPoint = newPoint;
        }
        time = System.nanoTime() - time;
        return newPoint;
    }
    
    private double getRoundedValue(double value){
        return BigDecimal.valueOf(value).setScale(this.precision , RoundingMode.HALF_UP).doubleValue();
    }

    public String getSteps() {
        return Steps;
    }

    public String getTime() {
        return String.valueOf(time);
    }

}
