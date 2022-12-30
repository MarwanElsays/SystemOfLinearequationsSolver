package org.example.Functions.Linear;


import org.example.Gui.OutputFrame;

public class GaussSeidalSolver {

    private OutputFrame outputFrame;

    public GaussSeidalSolver(OutputFrame outputFrame) {
        this.outputFrame = outputFrame;
    }

    public double[] solve(double[][] coefficients, double[] constants, double[] initial, int numberOfInterations,
            double absoluteRelativeError) {

        if (absoluteRelativeError < 0)
            absoluteRelativeError = -absoluteRelativeError;

        int[] orderOfRows = MatrixConfigurator.convertToDiagonalDominant(coefficients); // Finding the order of rows
                                                                                        // that makes the matrix
                                                                                        // diagonally dominant

        String step = "";

        double maxError = absoluteRelativeError;
        int iteration = 1;
        // the initial guesses array is used to hold the resulting values for the
        // unknowns during the process
        while (maxError >= absoluteRelativeError) {
            maxError = 0;
            step = "";
            step += String.format("Iteration %d:\n", iteration);
            for (int j = 0; j < initial.length; j++) {
                step += String.format("X%d = (%f", j + 1, constants[orderOfRows[j]]);
                double tempValue = initial[j];
                initial[j] = 0;
                // adding the coefficient multiplied by the guesses together other than the one
                // being calculated this iteration.
                for (int k = 0; k < initial.length; k++) {
                    if (j != k) {
                        step += String.format(" - %f(%f)", coefficients[orderOfRows[j]][k], initial[k]);
                        initial[j] += initial[k] * coefficients[orderOfRows[j]][k];
                    }
                }
                // after finding the sum, it is then subtracted from the right hand side then
                // divided by the
                // coefficient of the unknown.
                initial[j] = (constants[orderOfRows[j]] - initial[j]) / coefficients[orderOfRows[j]][j];
                double tempError = Math.abs((initial[j] - tempValue) / initial[j]);
                if (tempError > maxError)
                    maxError = tempError;
                step += String.format(") / %f\nNew X%d = %f\n", coefficients[orderOfRows[j]][j], j + 1, initial[j]);
            }
            step += "\n";
            outputFrame.printSteps(step);
            iteration++;
            if (iteration > numberOfInterations) {
                outputFrame.printSteps("Solution may not converge\n");
                return initial;
            }
        }
        return initial;
    }

}
