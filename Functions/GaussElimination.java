package Functions;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussElimination implements Solve{
    private final long time;
    private final int epsilon;
    private final double[][] A;
    private final double[] b;
    private double[] result;

    public GaussElimination(double[][] A , double[] b , int epsilon){
        this.A = A;
        this.b = b;
        this.epsilon = epsilon;
        long startTime = System.nanoTime();
        forwardElimination();
        backwardSubstitution();
        this.time = System.nanoTime() - startTime;
    }

    private void forwardElimination(){
        int n = b.length;
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

    private void backwardSubstitution(){
        int n = b.length;
        double[] result = new double[n];
        result[n - 1] = getRoundedValue(b[n - 1] / A[n - 1][n - 1]);
        for (int i = n - 2 ; i >= 0 ; --i){
            double sum = 0;
            for (int j = i + 1 ; j < n ; ++j){
                sum += getRoundedValue(A[i][j] * result[j]);
            }
            result[i] = getRoundedValue((b[i] - sum) / A[i][i]);
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
}
