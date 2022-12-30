package org.example.Functions.Linear;

import org.example.Gui.OutputFrame;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Vector;

public class LU implements Solve{
    private final long time;
    private final int epsilon;
    private final double[][] A;
    private double[] b;
    private final String form;
    private double[] result;
    private int n;
    private double[][] L , U;
    private final OutputFrame outputFrame;
    private Vector < Character > vector = new Vector<>();

    public LU(double[][] A , double[] b , int epsilon , String form , OutputFrame outputFrame , Vector < Character > vector) {
        this.A = A;
        this.b = b;
        this.epsilon = epsilon;
        this.form = form;
        this.n = b.length;
        this.L = new double[n][n];
        this.U = new double[n][n];
        this.outputFrame = outputFrame;
        this.vector = vector;
        for (int i = 0 ; i < n ; ++i){
            System.arraycopy(this.A[i], 0, this.U[i], 0, n);
        }
        // to solve a system using LU decomposition, there are three different forms
        // the user is free to choose which form but should take into consideration that not every system
        // can be solved using Crout or Cholesky
        long startTime = System.nanoTime();
        if(form.equals("Downlittle Form")){
            outputFrame.printSteps("Solving the system using " + this.form + " LU Decomposition\n\n");
            this.doolittle();
            outputFrame.printSteps("Let Ux = y\nWe need to get the matrix y\nSo we will solve the system Ly = b, then solve Ux = y\n\n");
            backwardSubstitution();
            outputFrame.printSteps("The final form of the result:\n\n");
            printResult();
        }else if(form.equals("Crout Form")){
            // we should check first that every element in the main diagonal in L matrix is not equals zero
            if (this.crout()){
                outputFrame.printSteps("Solving the system using " + this.form + " LU Decomposition\n\n");
                outputFrame.printSteps("After Calculating Lower & Upper Triangular Matrices:\n\n");
                printL();
                printU();
                outputFrame.printSteps("Let Ux = y\nWe need to get the matrix y\nSo we will solve the system Ly = b, then solve Ux = y\n\n");
                backwardSubstitution();
                outputFrame.printSteps("The final form of the result:\n\n");
                printResult();
            }
            else outputFrame.printSteps("Solving the system using Crout's Method is invalid\n");
        }else{
            cholesky();
        }
        time = System.nanoTime() - startTime;
    }

    private void printL(){
        outputFrame.printSteps("Lower Triangular Matrix: \n\n");
        for (int i = 0 ; i < n ; ++i){
            for (int j = 0 ; j < n ; ++j){
                outputFrame.printSteps(L[i][j] + " ");
            }
            outputFrame.printSteps("\n");
        }
        outputFrame.printSteps("\n");
    }

    private void printU(){
        outputFrame.printSteps("Upper Triangular Matrix: \n\n");
        for (int i = 0 ; i < n ; ++i){
            for (int j = 0 ; j < n ; ++j){
                outputFrame.printSteps(U[i][j] + " ");
            }
            outputFrame.printSteps("\n");
        }
        outputFrame.printSteps("\n");
    }

    private void printCurrent(double[][] M){
        for (int i = 0 ; i < n ; ++i){
            for (int j = 0 ; j < n ; ++j){
                outputFrame.printSteps(M[i][j] + " ");
            }
            outputFrame.printSteps("| " + b[i] + "\n");
        }
        outputFrame.printSteps("\n");
    }

    private void printResult(){
        for (int i = 0 ; i < n ; ++i){
            outputFrame.printSteps(vector.get(i) + " = " + result[i] + "\n");
        }
    }

    private void doolittle(){
        // Doolittle form is very similar as Gauss elimination
        // the main diagonal in L matrix is ones
        for (int i = 0 ; i < n ; ++i) L[i][i] = 1;
        for (int i = 0 ; i < n - 1 ; ++i){
            for (int j = i + 1 ; j < n ; ++j){
                double scalar = getRoundedValue(U[j][i] / U[i][i]);
                // the other elements of L matrix equal to the scalar
                L[j][i] = scalar;
                for (int k = 0 ; k < n ; ++k){
                    // do the same process as Gauss
                    U[j][k] = getRoundedValue(U[j][k] - getRoundedValue(scalar * U[i][k]));
                }
            }
        }
        outputFrame.printSteps("After performing forward Elimination:\n\n");
        printL();
        printU();
    }

    // solve Ux = y
    private void backwardSubstitution(){
        // a result array to save to solution of the system if it exists
        double[] result = new double[n];
        this.b = forwardSubstitution();
        outputFrame.printSteps("After forward substitution for Ly = b:\n\n");
        for (int i = 0 ; i < n ; ++i){
            outputFrame.printSteps("y" + (i + 1) + " = " + b[i] + "\n");
        }
        outputFrame.printSteps("\n");
        result[n - 1] = getRoundedValue(b[n - 1] / U[n - 1][n - 1]);
        outputFrame.printSteps("Now it's time to do backward substitution\n\n");
        outputFrame.printSteps(vector.get(n - 1) + " = " + b[n - 1] + " / " + A[n - 1][n - 1] + " = " + result[n - 1] + "\n");
        for (int i = n - 2 ; i >= 0 ; --i){
            double sum = 0;
            for (int j = i + 1 ; j < n ; ++j){
                sum = getRoundedValue(sum + getRoundedValue(U[i][j] * result[j]));
            }
            result[i] = getRoundedValue(getRoundedValue(b[i] - sum) / U[i][i]);
            outputFrame.printSteps(vector.get(i) + " = " + "( " + b[i] + " - " + sum + " )" + " / " + A[i][i] + " = " + result[i] + "\n");
        }
        outputFrame.printSteps("\n");
        setResult(result);
    }

    // Solve Ly = b
    private double[] forwardSubstitution(){
        outputFrame.printSteps("Before forward substitution for Ly = b:\n\n");
        printCurrent(L);
        double[] ret = new double[n];
        ret[0] = getRoundedValue(b[0] / L[0][0]);
        for (int i = 1 ; i < n ; ++i){
            double sum = 0;
            for (int j = 0 ; j < i ; ++j){
                sum = getRoundedValue(sum + getRoundedValue(L[i][j] * ret[j]));
            }
            ret[i] = getRoundedValue(getRoundedValue(b[i] - sum) / L[i][i]);
        }
        return ret;
    }
    private boolean crout(){
        // using doolittle we will get L & U
        for (int i = 0 ; i < n ; ++i){
            U[i][i] = 1;
        }
        for (int i = 0 ; i < n ; ++i){
            for (int j = i ; j < n ; ++j){
                double sum = 0;
                for (int k = 0 ; k < i ; ++k){
                    sum = getRoundedValue(sum + getRoundedValue(L[j][k] * U[k][i]));
                }
                L[j][i] = getRoundedValue(A[j][i] - sum);
            }
            for (int j = i ; j < n ; ++j){
                double sum = 0;
                for (int k = 0 ; k < i ; ++k){
                    sum = getRoundedValue(sum + getRoundedValue(L[i][k] * U[k][j]));
                }
                if (L[i][i] == 0){
                    return false;
                }
                U[i][j] = getRoundedValue(getRoundedValue(A[i][j] - sum) / L[i][i]);
            }
        }
        return true;
    }

    private boolean isSymmetric(){
        for (int i = 0 ; i < n ; ++i){
            for (int j = 0 ; j < n ; ++j){
                if(A[i][j] != A[j][i]) return false;
            }
        }
        return true;
    }

    private double[][] transpose(double[][] X){
        double[][] ret = new double[n][n];
        for (int i = 0 ; i < n ; ++i){
            for (int j = 0 ; j < n ; ++j){
                ret[i][j] = X[j][i];
            }
        }
        return ret;
    }

    // This method will be called iff the coefficient matrix is symmetric and every element in the main diagonal in L matrix is not equals zero
    private void cholesky(){
        // we should check first that the coefficient matrix is symmetric and every element in the main diagonal in L matrix is not equals zero
        outputFrame.printSteps("Solving the system using " + this.form + " LU Decomposition\n\n");
        if(isSymmetric()){
            if (crout()){
                int i , j , k;
                for (j = 0 ; j < n ; j++){
                    double sum = 0;
                    for (k = 0 ; k < j ; k++){
                        sum = getRoundedValue(sum + getRoundedValue(L[j][k] * L[j][k]));
                    }
                    L[j][j] = getRoundedValue(Math.sqrt(A[j][j] - sum));
                    for (i = j + 1 ; i < n ; i++){
                        sum = 0;
                        for (k = 0 ; k < j ; k++){
                            sum = getRoundedValue(sum + getRoundedValue(L[i][k] * L[j][k]));
                        }
                        L[i][j] = getRoundedValue(1.0 / L[j][j] * (A[i][j] - sum));
                    }
                }
                U = transpose(L);
                outputFrame.printSteps("After Calculating Lower & Upper Triangular Matrices:\n\n");
                printL();
                printU();
                outputFrame.printSteps("Let Ux = y\nWe need to get the matrix y\nSo we will solve the system Ly = b, then solve Ux = y\n\n");
                backwardSubstitution();
                outputFrame.printSteps("The final form of the result:\n\n");
                printResult();
            }
        }else{
            outputFrame.printSteps("Solving the system using Cholesky's Method is invalid\n");
        }
    }

    private double getRoundedValue(double x){
        return BigDecimal.valueOf(x).setScale(epsilon , RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public double[][] getA() {
        return A;
    }

    @Override
    public double[] getB() {
        return b;
    }

    @Override
    public double[] getResult() {
        return result;
    }

    public void setResult(double[] result) {
        this.result = result;
    }

    @Override
    public long getTime() {
        return time;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getForm() {
        return form;
    }

    public double[][] getU() {
        return U;
    }

    public void setU(double[][] u) {
        U = u;
    }

    public double[][] getL() {
        return L;
    }

    public void setL(double[][] l) {
        L = l;
    }

    public Vector<Character> getVector() {
        return vector;
    }
}
