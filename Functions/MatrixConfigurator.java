package Functions;

public class MatrixConfigurator {

    public static int[] convertToDiagonalDominant(double[][] coefficients){
        double[] sumOfRows = new double[coefficients.length];
        int[] orderOfRows = new int[coefficients.length];
        boolean foundDominantRow = false;

        for (int i = 0; i < coefficients.length; i++){
            sumOfRows[i] = 0;
            for (int j = 0; j < coefficients[0].length; j++){
                sumOfRows[i] += Math.abs(coefficients[i][j]);
            }
            for (int j = 0; j < coefficients[0].length; j++){
                if(Math.abs(coefficients[i][j]) > sumOfRows[i] - Math.abs(coefficients[i][j])){
                    foundDominantRow = true;
                    orderOfRows[j] = i;
                    break;
                }else if (Math.abs(coefficients[i][j]) == sumOfRows[i] - Math.abs(coefficients[i][j])) {
                    orderOfRows[j] = i;
                    break;
                }//cant find an element
            }
        }
        return orderOfRows;
    }

}
