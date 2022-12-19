package Functions;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussJordan implements Solve{
    private final long time;
    private final int epsilon;
    private final double[][] A;
    private final double[] b;
    private double[] result;
    private int n;

    public GaussJordan(double[][] A , double[] b , int epsilon) {
        this.A = A;
        this.b = b;
        this.epsilon = epsilon;
        this.n = b.length;
        long startTime = System.nanoTime();
        forwardElimination();
        backwardElimination();
        substitution();
        this.time = System.nanoTime() - startTime;
    }

    private void forwardElimination(){
        for (int i = 0 ; i < n - 1 ; ++i){
            // Partial Pivoting
            double max = Math.abs(A[i][i]);
            int maxIndex = i;
            for (int j = i + 1 ; j < n ; ++j){
                if (Math.abs(A[j][i]) > max){
                    max = Math.abs(A[j][i]);
                    maxIndex = j;
                }
            }
            swapOperation(i , maxIndex);
            for (int j = i + 1 ; j < n ; ++j){
                double scalar = A[j][i] / A[i][i];
                for (int k = 0 ; k < n ; ++k){
                    A[j][k] = getRoundedValue(A[j][k] - scalar * A[i][k]);
                }
                b[j] = getRoundedValue(b[j] - scalar * b[i]);
            }
        }
    }

    private void backwardElimination(){
        for (int i = n - 1 ; i >= 1 ; --i){
            for (int j = i - 1 ; j >= 0 ; --j){
                double scalar = A[j][i] / A[i][i];
                for (int k = 0 ; k < n ; ++k){
                    A[j][k] = getRoundedValue(A[j][k] - scalar * A[i][k]);
                }
                b[j] = getRoundedValue(b[j] - scalar * b[i]);
            }
        }
    }

    private void substitution(){
        double[] result = new double[n];
        for (int i = 0 ; i < n ; ++i){
            result[i] = getRoundedValue(b[i] / A[i][i]);
        }
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

    private double getRoundedValue(double x){
        return BigDecimal.valueOf(x).setScale(epsilon , RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public long getTime() {
        return this.time;
    }

    @Override
    public double[][] getA() {
        return this.A;
    }

    @Override
    public double[] getB() {
        return this.b;
    }

    @Override
    public double[] getResult() {
        return this.result;
    }

    public void setResult(double[] result) {
        this.result = result;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
