package Functions;

import Gui.OutputFrame;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Vector;

public class GaussElimination implements Solve {
    private final long time;
    private final int epsilon;
    private final double[][] A;
    private final double[] b;
    private double[] result;
    private int n;
    private final OutputFrame outputFrame;
    private final Vector < Character > vector;
    public GaussElimination(double[][] A , double[] b , int epsilon , OutputFrame outputFrame, Vector < Character > vector){
        this.A = A;
        this.b = b;
        this.epsilon = epsilon;
        this.n = b.length;
        this.outputFrame = outputFrame;
        this.vector = vector;
        long startTime = System.nanoTime();
        // There are two main operations to get the result
        // First, do forward elimination
        // Second, do backward substitution
        outputFrame.printSteps("First step to solve the system is doing forward elimination\n\n");
        forwardElimination();
        int ranka = RankA(this.A);
        int rankaug = RankAugmented(ranka);
        if(rankaug > ranka){
            outputFrame.setText("No Solution");
        }
        else if(rankaug == ranka && ranka == A.length){
            outputFrame.printSteps("Now it's time to do backward substitution\n\n");
            backwardSubstitution();
            outputFrame.printSteps("The final form of the result:\n\n");
            printResult();
        }
        else{
            outputFrame.printSteps("there is infinite number of solutions\n\n");
            backwardSubstitution();
            outputFrame.printSteps("Let All the free Varibales equal 1\nSo one of the Solutions:\n\n");
            printResult();
        }
       
        this.time = System.nanoTime() - startTime;
    }

    public int RankA(double [][]Y){
        int rank = Y.length;
        for(int i=Y.length-1;i>=0;i--){
            boolean isZero = true;
            for(int j=0;j<Y.length;j++){
                if(Y[i][j] != 0){
                    isZero = false;
                }
            }
            if(isZero)rank--;
        }
        return rank;
    }
  
    public int RankAugmented(int Ranka){
        int i = b.length-1;
        int rank = b.length;
        while(b[i] == 0){
            rank--;
            i--;
        }
        if(Ranka > rank)rank = Ranka;

        return rank;
    }

    private void printCurrent(){
        for (int i = 0 ; i < n ; ++i){
            for (int j = 0 ; j < n ; ++j){
                outputFrame.printSteps(A[i][j] + " ");
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
    
    private void forwardElimination(){
        outputFrame.printSteps("Before start: \n\n");
        printCurrent();
        outputFrame.printSteps("As the system is " + n + "x" + n + ", we need to drive elimination operation " + (n - 1) + " times.\n\n");
        for (int i = 0 ; i < n - 1 ; ++i){
            // Partial Pivoting
            outputFrame.printSteps((i + 1) + "-th iteration: \n\n");
            outputFrame.printSteps("The system before doing partial pivoting:\n\n");
            printCurrent();
            double max = Math.abs(A[i][i]);
            int maxIndex = i;
            for (int j = i + 1 ; j < n ; ++j){
                if (Math.abs(A[j][i]) > max){
                    max = Math.abs(A[j][i]);
                    maxIndex = j;
                }
            }
            // Do the swap operation to make the maximum element in the current column as a pivot
            swapOperation(i , maxIndex);
            outputFrame.printSteps("The system after doing partial pivoting:\n\n");
            printCurrent();
            // Do the elimination operation by making all the elements below the i-th element to be zero
            for (int j = i + 1 ; j < n ; ++j){
                if(A[i][i] == 0)continue;
                double scalar = A[j][i] / A[i][i];
                for (int k = 0 ; k < n ; ++k){
                    A[j][k] = getRoundedValue(A[j][k] - scalar * A[i][k]);
                }
                b[j] = getRoundedValue(b[j] - scalar * b[i]);
            }
        }
        outputFrame.printSteps("After finishing the elimination process, this is the final form of the system before doing the backward substitution process:\n\n");
        printCurrent();
    }

    private void backwardSubstitution(){
        // a result array to save to solution of the system
        double[] result = new double[n];
        if(A[n - 1][n - 1] == 0){
            result[n - 1] = 1;
            outputFrame.printSteps(vector.get(n - 1) + " = " + "1\n");
        }
        else
        {
            result[n - 1] = getRoundedValue(b[n - 1] / A[n - 1][n - 1]);
            outputFrame.printSteps(vector.get(n - 1) + " = " + b[n - 1] + " / " + A[n - 1][n - 1] + " = " + result[n - 1] + "\n");
        }
        for (int i = n - 2 ; i >= 0 ; --i){
            double sum = 0;
            for (int j = i + 1 ; j < n ; ++j){
                sum = getRoundedValue(sum + A[i][j] * result[j]);
            }
            double a;
            if(A[i][i] == 0)a = 1;
            else a = A[i][i];

            if((b[i] - sum) == 0 ){
                if(A[i][i] == 0)result[i] = 1;
                else result[i] = 0;
                outputFrame.printSteps(vector.get(i) + " = " + result[i] +"\n");
            }               
            else{
                result[i] = getRoundedValue((b[i] - sum) / a);
                outputFrame.printSteps(vector.get(i) + " = " + "( " + b[i] + " - " + sum + " )" + " / " + a + " = " + result[i] + "\n");
            }

        }
        outputFrame.printSteps("\n");
        setResult(result);
    }

    private void swapOperation(int i , int maxIndex){
        // put the maximum pivot in the i-th row
        double[] tmp = A[i];
        A[i] = A[maxIndex];
        A[maxIndex] = tmp;
        // do the same operation to array "b" also
        double tmp2 = b[i];
        b[i] = b[maxIndex];
        b[maxIndex] = tmp2;
    }

    // This method is used to round our calculations
    private double getRoundedValue(double x){
        if(Double.isNaN(x)){
            System.out.println("hi");
            return 0;
        }
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

    public Vector<Character> getVector() {
        return vector;
    }
}
