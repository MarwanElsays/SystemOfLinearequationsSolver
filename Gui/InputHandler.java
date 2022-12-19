package Gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Color;
import Functions.GaussElimination;
import Functions.GaussJordan;
import Functions.GaussSeidalSolver;
import Functions.JacobiSolver;

public class InputHandler {

    private MainFrame frame;
    private int noOfEquations;
    private double[][] A;
    private double[] B;

    InputHandler(MainFrame frame) {
        this.frame = frame;
    }

    public void ChangeColor(Color c){
        frame.getButton().setBackground(c);
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
            solveNonIterative();
        else if (frame.getMethod().equals("Jacobi-Iteration") || frame.getMethod().equals("Gauss-Seidel"))
            solveIterative();
        else if (frame.getMethod().equals("LU Decomposition"))
            solveLU();
    }
    
    public int getNoOfEquations() {
        return noOfEquations;
    }

    private double[] cofficientsExtractor(String equation, int index,HashSet<Character> var) {
        equation = equation.replaceAll(" ", "");
        double [] Coefficients = new double[noOfEquations];
        for(int i=0;i<noOfEquations;i++){
            Coefficients[i] = 0;
        }        
        
        String [] subEquations = splitEquations(equation,index);
    
        for (String eq : subEquations) {
            double coeff = 0;
            int j=0;
            int indexofvar = 0;

            for(j=eq.length()-1;j>=0;j--){
                if(Character.isAlphabetic(eq.charAt(j))){
                    Iterator <Character> it = var.iterator();
 
                    while(it.hasNext()){
                        if(it.next().equals(eq.charAt(j))){
                            break;
                        }
                        indexofvar++;
                    }
                    break;
                }
            }

            String subeq = eq.substring(0, j);
            if(var.size() == 1){
                indexofvar = Integer.parseInt(eq.substring(j+1))-1;
            }
    
            if (subeq.equals(""))
                coeff = 1;
            else if (subeq.equals("-"))
                coeff = -1;
            else
                coeff = Double.parseDouble(eq.substring(0, j));
    
            Coefficients[indexofvar] = coeff;
        }
    
        return Coefficients;
    }
    
    private void extractCoefficients() {
        String[] Equations = frame.getEquations();
        HashSet<Character> Variables = extractVariables(Equations);
        System.out.println(Variables);

        noOfEquations = Equations.length;
        A = new double[noOfEquations][noOfEquations];
        B = new double[noOfEquations];
        for (int i = 0; i < noOfEquations; i++) {
            A[i] = cofficientsExtractor(Equations[i], i,Variables);
        }
    }

    private HashSet<Character> extractVariables(String [] equations){
        HashSet<Character> variables = new HashSet<>();
        for(int i=0;i<equations.length;i++){
            for(int j=0;j<equations[i].length();j++){
                if(Character.isAlphabetic(equations[i].charAt(j))){
                    variables.add(equations[i].charAt(j));
                }
            }
            if(variables.size() == noOfEquations) break;
        }
        return variables;
    }

    private String[] splitEquations(String equation,int index){        
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

        String[] x = new String[subEquations.size()];
        for (int j = 0; j < subEquations.size(); j++) {
            x[j] = subEquations.elementAt(j);
        }

        return x;
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
                outputframe.setOutput(gj.getResult());
                frame.setTime(String.valueOf(gj.getTime()));
                break;
            }
        }
    }
    
    private void solveIterative() {
        int noOfIterations = frame.getNoOfIterations();
        double AbsRelativeError = frame.getAbsRelativeErrorField();
        double[] intialGuess = new double[noOfEquations];
    
        String[] intialGuessesArray = frame.getInitialGuess();
        for (int k = 0; k < intialGuessesArray.length; k++) {
            intialGuess[k] = Double.parseDouble(intialGuessesArray[k]);
        }
    
        switch (frame.getMethod()) {
            case "Gauss-Seidel": {
                OutputFrame outputframe = new OutputFrame();
                GaussSeidalSolver gauss_seidel = new GaussSeidalSolver(outputframe);
                //gauss_seidel.solve(A, B, intialGuess, AbsRelativeError);
                outputframe.setOutput(gauss_seidel.solve(A, B, intialGuess, noOfIterations));
                break;
            }
            case "Jacobi-Iteration": {
                OutputFrame outputframe = new OutputFrame();
                JacobiSolver jacobi = new JacobiSolver();
                outputframe.setOutput(jacobi.solve(A, B, intialGuess, noOfIterations));
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
