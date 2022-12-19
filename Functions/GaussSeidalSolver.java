package Functions;

import Gui.OutputFrame;

public class GaussSeidalSolver {

    OutputFrame outputFrame;

    public GaussSeidalSolver(OutputFrame outputFrame) {
        this.outputFrame = outputFrame;
    }

    private void printStartOfProcedure(double[][] coefficients, double[] constants, int[] orderOfRows) {
        System.out.println("Converting matrix into a diagonally dominant form.......");
        System.out.println("After conversion:");
        for (int j = 0; j < coefficients.length; j++) {

            for (int k = 0; k < coefficients[0].length; k++) {
                if (k == coefficients[0].length - 1)
                    System.out.printf("%fX%d = %f\n", coefficients[orderOfRows[j]][k], k + 1, constants[orderOfRows[j]]);
                else
                    System.out.printf("%fX%d + ", coefficients[orderOfRows[j]][k], k + 1);
            }
        }
    }

    public double[] solve(double[][] coefficients, double[] constants, double[] initial, int numberOfInterations){
        int[] orderOfRows = MatrixConfigurator.convertToDiagonalDominant(coefficients); //Finding the order of rows that makes the matrix diagonally dominant

        this.printStartOfProcedure(coefficients, constants, orderOfRows);

        //the initial guesses array is used to hold the resulting values for the unknowns during the process
        for(int i = 0; i < numberOfInterations; i++){
            System.out.printf("Iteration %d:\n", i+1);
            for(int j = 0; j < initial.length; j++){
                System.out.printf("X%d = (%f",j+1, constants[orderOfRows[j]]);
                initial[j] = 0;
                //adding the coefficient multiplied by the guesses together other than the one being calculated this iteration.
                for (int k = 0; k < initial.length; k++){
                    if(j != k) {
                        System.out.printf(" - %fX%d", coefficients[orderOfRows[j]][k], k + 1);
                        initial[j] += initial[k] * coefficients[orderOfRows[j]][k];
                    }
                }
                //after finding the sum, it is then subtracted from the right hand side then divided by the
                //coefficient of the unknown.
                initial[j] = (constants[orderOfRows[j]] - initial[j]) / coefficients[orderOfRows[j]][j];
                System.out.printf(") / %f\nNew X%d = %f\n", coefficients[orderOfRows[j]][j], j+1, initial[j]);
                outputFrame.printSteps("Hello!");
            }
        }
        return initial;
    }


    public double[] solve(double[][] coefficients, double[] constants, double[] initial, double absoluteRelativeError){

        if(absoluteRelativeError < 0)
            absoluteRelativeError = -absoluteRelativeError;

        int[] orderOfRows = MatrixConfigurator.convertToDiagonalDominant(coefficients); //Finding the order of rows that makes the matrix diagonally dominant

        this.printStartOfProcedure(coefficients, constants, orderOfRows);

        double maxError = absoluteRelativeError;
        int iteration = 1;
        //the initial guesses array is used to hold the resulting values for the unknowns during the process
        while (maxError >= absoluteRelativeError){
            maxError = 0;
            System.out.printf("Iteration %d:\n", iteration);
            for(int j = 0; j < initial.length; j++){
                System.out.printf("X%d = (%f",j+1, constants[orderOfRows[j]]);
                double tempValue = initial[j];
                initial[j] = 0;
                //adding the coefficient multiplied by the guesses together other than the one being calculated this iteration.
                for (int k = 0; k < initial.length; k++){
                    if(j != k) {
                        System.out.printf(" - %fX%d", coefficients[orderOfRows[j]][k], k + 1);
                        initial[j] += initial[k] * coefficients[orderOfRows[j]][k];
                    }
                }
                //after finding the sum, it is then subtracted from the right hand side then divided by the
                //coefficient of the unknown.
                initial[j] = (constants[orderOfRows[j]] - initial[j]) / coefficients[orderOfRows[j]][j];
                double tempError = Math.abs((initial[j] - tempValue) / initial[j]);
                if(tempError > maxError)
                    maxError = tempError;
                System.out.printf(") / %f\nNew X%d = %f\t Absolute Relative Error = %f\n", coefficients[orderOfRows[j]][j], j+1, initial[j], tempError);
            }
            iteration++;
        }
        return initial;
    }

}
