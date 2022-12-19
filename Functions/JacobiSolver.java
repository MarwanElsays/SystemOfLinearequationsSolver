package Functions;

public class JacobiSolver {

    public double[] solve(double[][] coefficients, double[] constants, double[] initial, int numberOfInterations) {
        double[] tempIterationHolder = new double[initial.length];
        // The tempIterationHolder array is used to hold the values of the resulting
        // variables in an iteration

        int[] orderOfRows = MatrixConfigurator.convertToDiagonalDominant(coefficients);
        // Finding the order of rows that makes the matrix diagonally dominant

        // the initial guesses array is used to hold the resulting values for the
        // unknowns during the process
        for (int i = 0; i < numberOfInterations; i++) {
            System.out.printf("Iteration %d:\n", i + 1);
            for (int j = 0; j < initial.length; j++) {
                System.out.printf("X%d = (%f", j + 1, constants[orderOfRows[j]]);
                tempIterationHolder[j] = 0;
                // adding the coefficient multiplied by the guesses together other than the one
                // being calculated this iteration.
                for (int k = 0; k < initial.length; k++) {
                    if (j != k) {
                        System.out.printf(" - %fX%d", coefficients[orderOfRows[j]][k], k + 1);
                        tempIterationHolder[j] += initial[k] * coefficients[orderOfRows[j]][k];
                    }
                }
                // after finding the sum, it is then subtracted from the right hand side then
                // divided by the
                // coefficient of the unknown.
                tempIterationHolder[j] = (constants[orderOfRows[j]] - tempIterationHolder[j])
                        / coefficients[orderOfRows[j]][j];
                System.out.printf(") / %f\n", coefficients[orderOfRows[j]][j]);
            }
            // after each iteration the results are saved into the initial array to be used
            // in the next iteration.
            initial = tempIterationHolder.clone();
            for (int j = 0; j < initial.length; j++)
                System.out.printf("New X%d = %f\n", j + 1, initial[j]);
        }
        return initial;
    }

    public double[] solve(double[][] coefficients, double[] constants, double[] initial, double absoluteRelativeError) {

        if (absoluteRelativeError < 0)
            absoluteRelativeError = -absoluteRelativeError;

        double[] tempIterationHolder = new double[initial.length];
        // The tempIterationHolder array is used to hold the values of the resulting
        // variables in an iteration

        int[] orderOfRows = MatrixConfigurator.convertToDiagonalDominant(coefficients);
        // Finding the order of rows that makes the matrix diagonally dominant

        double maxError = absoluteRelativeError;
        int iteration = 1;

        // the initial guesses array is used to hold the resulting values for the
        // unknowns during the process
        while (maxError >= absoluteRelativeError) {
            maxError = 0;
            System.out.printf("Iteration %d:\n", iteration);
            for (int j = 0; j < initial.length; j++) {
                System.out.printf("X%d = (%f", j + 1, constants[orderOfRows[j]]);
                tempIterationHolder[j] = 0;
                // adding the coefficient multiplied by the guesses together other than the one
                // being calculated this iteration.
                for (int k = 0; k < initial.length; k++) {
                    if (j != k) {
                        System.out.printf(" - %f(%f)", coefficients[orderOfRows[j]][k], initial[k]);
                        tempIterationHolder[j] += initial[k] * coefficients[orderOfRows[j]][k];
                    }
                }
                // after finding the sum, it is then subtracted from the right hand side then
                // divided by the
                // coefficient of the unknown.
                tempIterationHolder[j] = (constants[orderOfRows[j]] - tempIterationHolder[j])
                        / coefficients[orderOfRows[j]][j];
                System.out.printf(") / %f\n", coefficients[orderOfRows[j]][j]);
            }
            // after each iteration the results are saved into the initial array to be used
            // in the next iteration.
            for (int j = 0; j < initial.length; j++) {
                double currentRelativeError = Math.abs((tempIterationHolder[j] - initial[j]) / tempIterationHolder[j]);
                if (currentRelativeError > maxError)
                    maxError = currentRelativeError;
                System.out.printf("New X%d = %f\t Absolute Relative Error = %f\n", j + 1, tempIterationHolder[j],
                        currentRelativeError);
            }
            initial = tempIterationHolder.clone();
            iteration++;
        }
        return initial;
    }

}
