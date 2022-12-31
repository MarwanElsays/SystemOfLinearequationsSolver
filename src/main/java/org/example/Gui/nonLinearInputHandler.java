package org.example.Gui;

import java.awt.Color;

import org.example.Functions.NonLinear.FalsePosition;
import org.example.Functions.NonLinear.Newton_Raphson;
import org.example.Functions.NonLinear.fixedPointSolver;

public class nonLinearInputHandler {

    private nonLinearFrame frame;
    private String Expression;
    private double initialGuess; 
    private int precision;
    private double RelativeError;
    private int noOfIterations;

    nonLinearInputHandler(nonLinearFrame frame) {
        this.frame = frame;
        this.Expression = frame.getEquations();
        this.precision = frame.getPrecision();
        this.RelativeError = frame.getAbsRelativeErrorField();
        this.noOfIterations = frame.getNoOfIterations();
        
    }

    public void ChangeColor(Color c){
        frame.getButton().setBackground(c);
    }

    public void setMethod() {
        frame.getIterativePanel().setVisible(true);
    }
    
    public void solve() {
        OutputFrame outputframe = new OutputFrame();
        switch (frame.getMethod()) {
            case "Bisection": {
                frame.setTime(String.valueOf("1"));
                break;
            }
            case "False-Position": {
                FalsePosition falsePosition = new FalsePosition(Expression, precision);
                falsePosition.solve(1, 3, noOfIterations, RelativeError);
                frame.setTime(falsePosition.getTime());
                outputframe.setText("the root = " + falsePosition.getRoot());
                break;
            }
            case "Fixed point": {
                fixedPointSolver fixedPoint = new fixedPointSolver(Expression,initialGuess,RelativeError,noOfIterations,precision);
                frame.setTime(fixedPoint.getTime());
                outputframe.setText("the root = "+fixedPoint.getXr());
                break;
            }
            case "Newton-Raphson": {
                Newton_Raphson newtonRaphson = new Newton_Raphson(Expression,precision);
                newtonRaphson.solve(1, RelativeError, noOfIterations);
                frame.setTime(newtonRaphson.getTime());
                outputframe.setText(newtonRaphson.getSteps());
                break;
            }
            case "Secant Method": {
                frame.setTime(String.valueOf("5"));
                break;
            }
        }
    } 
}
