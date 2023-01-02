package org.example.Gui;

import java.awt.Color;

import org.example.Functions.NonLinear.Bisection;
import org.example.Functions.NonLinear.FalsePosition;
import org.example.Functions.NonLinear.Newton_Raphson;
import org.example.Functions.NonLinear.SecantSolver;
import org.example.Functions.NonLinear.fixedPointSolver;

public class nonLinearInputHandler {

    private nonLinearFrame frame;
    private String[] Expressions;
    private String[] initialGuess;
    private int precision;
    private double RelativeError;
    private int noOfIterations;

    nonLinearInputHandler(nonLinearFrame frame) {
        this.frame = frame;
        Expressions = frame.getEquations().split("\n");
        precision = frame.getPrecision();
        RelativeError = frame.getAbsRelativeErrorField();
        noOfIterations = frame.getNoOfIterations();
        initialGuess = frame.getInitialGuess().split(",");
    }

    public void ChangeColor(Color c) {
        frame.getButton().setBackground(c);
    }

    public void setMethod() {
        frame.getIterativePanel().setVisible(true);
    }

    public void solve() {
        OutputFrame outputframe = new OutputFrame();
        switch (frame.getMethod()) {
            case "Bisection": {
                Bisection bisection = new Bisection(Expressions[0], precision, Double.parseDouble(initialGuess[0]), Double.parseDouble(initialGuess[1]), RelativeError);
                outputframe.setText(bisection.getSteps());   //replace with steps
                frame.setTime(bisection.getTime());
                break;
            }
            case "False-Position": {
                FalsePosition falsePosition = new FalsePosition(Expressions[0], precision);
                falsePosition.solve(Double.parseDouble(initialGuess[0]), Double.parseDouble(initialGuess[1]), noOfIterations, RelativeError);
                outputframe.setText(falsePosition.getSteps());
                frame.setTime(falsePosition.getTime());
                break;
            }
            case "Fixed point": {
                fixedPointSolver fixedPoint = new fixedPointSolver(Expressions, Double.parseDouble(initialGuess[0]), RelativeError, noOfIterations, precision);
                outputframe.setText(fixedPoint.getSteps());
                frame.setTime(fixedPoint.getTime());
                Expressions[0] = "x";
                break;
            }
            case "Newton-Raphson": {
                Newton_Raphson newtonRaphson = new Newton_Raphson(Expressions[0], precision);
                newtonRaphson.solve(Double.parseDouble(initialGuess[0]), RelativeError, noOfIterations);
                outputframe.setText(newtonRaphson.getSteps());
                frame.setTime(newtonRaphson.getTime());
                break;
            }
            case "Secant Method": {
                SecantSolver secant = new SecantSolver(precision);
                secant.solve(Expressions[0], Double.parseDouble(initialGuess[0]), Double.parseDouble(initialGuess[1]), RelativeError, noOfIterations);
                outputframe.setText(secant.getSteps());
                frame.setTime(secant.getTime());
                break;
            }
        }
        new GraphFrame(Expressions);
    }
}
