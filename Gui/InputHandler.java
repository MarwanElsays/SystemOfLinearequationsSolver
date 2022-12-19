package Gui;

import java.util.Arrays;
import java.util.Vector;

import Functions.GaussElimination;
import Functions.GaussJordan;

public class InputHandler {

    private MainFrame frame;
    private int noOfEquations;
    private double[][] A;
    private double[] B;

    InputHandler(MainFrame frame) {
        this.frame = frame;
    }

    public void setMethod() {
        String method = frame.getMethod();
        if (method.equals("LU Decomposition"))
            frame.getLUPanel().setVisible(true);
        else
            frame.getLUPanel().setVisible(false);

        if (method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration"))
            frame.getIterativePanel().setVisible(true);
        else
            frame.getIterativePanel().setVisible(false);
    }
    
    public void solve() {
        extractCoefficients();
        if (frame.getMethod().equals("Gauss") || frame.getMethod().equals("Gauss-Jordan"))
            solveIterative();
        else if (frame.getMethod().equals("Jacobi") || frame.getMethod().equals("Gauss-Seidel"))
            solveNonIterative();
        else if (frame.getMethod().equals("LU Decomposition"))
            solveLU();
    }
    
    public int getNoOfEquations() {
        return noOfEquations;
    }

    private double[] cofficientsExtractor(String equation, int index) {
        Vector<Double> Coefficients = new Vector<Double>();
        Vector<String> subEquations = new Vector<String>();
        String subEquation = "";
    
        int i = 0;
        while (equation.charAt(i) != '=') {
            if ((equation.charAt(i) == '-' || equation.charAt(i) == '+') && i != 0) {
                subEquations.add(subEquation);
                subEquation = "";
            }
    
            if (equation.charAt(i) != '+')
                subEquation += equation.charAt(i);
            i++;
        }
    
        subEquations.add(subEquation);
        double b = Double.parseDouble(equation.substring(i + 1, equation.length()));
        B[index] = b;
    
        for (String eq : subEquations) {
            double coeff = 0;
            String subeq = eq.substring(0, eq.indexOf('x'));
    
            if (subeq.equals(""))
                coeff = 1;
            else if (subeq.equals("-"))
                coeff = -1;
            else
                coeff = Double.parseDouble(eq.substring(0, eq.indexOf('x')));
    
            Coefficients.add(coeff);
        }
    
        double[] x = new double[noOfEquations];
        for (int j = 0; j < noOfEquations; j++) {
            x[j] = Coefficients.elementAt(j);
        }
    
        return x;
    }
    
    private void extractCoefficients() {
        String[] Equations = frame.getEquations();
        noOfEquations = Equations.length;
        A = new double[noOfEquations][noOfEquations];
        B = new double[noOfEquations];
        for (int i = 0; i < noOfEquations; i++) {
            A[i] = cofficientsExtractor(Equations[i], i);
        }
    }
    
    private void solveNonIterative() {
        int precision;
        precision = frame.getPrecision();
        switch (frame.getMethod()) {
            case "Gauss": {
                OutputFrame outputframe = new OutputFrame();
                GaussElimination gauss = new GaussElimination(A, B, precision);
                System.out.println(Arrays.toString(gauss.getResult()));
                outputframe.setOutput(gauss.getResult());
                frame.setTime(String.valueOf(gauss.getTime()));
                break;
            }
    
            case "Gauss-Jordan": {
                OutputFrame outputframe = new OutputFrame();
                GaussJordan gj = new GaussJordan(A, B, precision);
                System.out.println(Arrays.toString(gj.getResult()));
                outputframe.setOutput(gj.getResult());
                frame.setTime(String.valueOf(gj.getTime()));
                break;
            }
        }
    }
    
    private void solveIterative() {
        int noOfIterations;
        double AbsRelativeError;
        double[] intialGuess = new double[noOfEquations];
    
        String[] intialGuessesArray = frame.getInitialGuess();
        for (int k = 0; k < intialGuessesArray.length; k++) {
            intialGuess[k] = Double.parseDouble(intialGuessesArray[k]);
        }
        noOfIterations = frame.getNoOfIterations();
        AbsRelativeError = frame.getAbsRelativeErrorField();
    
        switch (frame.getMethod()) {
            case "Gauss-Seidel": {
                OutputFrame outputframe = new OutputFrame();
                break;
            }
            case "Jacobi-Iteration": {
                OutputFrame outputframe = new OutputFrame();
                break;
            }
        }
    }

    private void solveLU() {
        switch (frame.getLUForm()) {
            case "Downlittle Form": {
                OutputFrame outputframe = new OutputFrame();
                break;
            }

            case "Crout Form": {
                OutputFrame outputframe = new OutputFrame();
                break;
            }

            case "Cholesky Form": {
                OutputFrame outputframe = new OutputFrame();
                break;
            }
        }
    }
}
