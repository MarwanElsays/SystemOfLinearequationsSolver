package org.example.Functions.Linear;


import org.example.Gui.OutputFrame;

public class JacobiSolver {

    private OutputFrame outputFrame;
    public JacobiSolver(OutputFrame outputFrame){
        this.outputFrame = outputFrame;
    }

    public double[] solve(double[][] coefficients, double[] constants, double[] initial, int numberOfInterations,  double absoluteRelativeError) {

        if(absoluteRelativeError < 0)
            absoluteRelativeError = -absoluteRelativeError;

        double[] tempIterationHolder = new double[initial.length];
        //The tempIterationHolder array is used to hold the values of the resulting variables in an iteration

        int[] orderOfRows = MatrixConfigurator.convertToDiagonalDominant(coefficients);
        //Finding the order of rows that makes the matrix diagonally dominant

        double maxError = absoluteRelativeError;
        int iteration = 1;

        String step = "";

        //the initial guesses array is used to hold the resulting values for the unknowns during the process
        while (maxError >= absoluteRelativeError)
        {
            step = "";
            maxError = 0;
            step += String.format("Iteration %d:\n", iteration);
            for(int j = 0; j < initial.length; j++){
                step += String.format("X%d = (%f",j+1, constants[orderOfRows[j]]);
                tempIterationHolder[j] = 0;
                //adding the coefficient multiplied by the guesses together other than the one being calculated this iteration.
                for (int k = 0; k < initial.length; k++){
                    if(j != k) {
                        step += String.format(" - %f(%f)", coefficients[orderOfRows[j]][k], initial[k]);
                        tempIterationHolder[j] += initial[k] * coefficients[orderOfRows[j]][k];
                    }
                }
                //after finding the sum, it is then subtracted from the right hand side then divided by the
                //coefficient of the unknown.
                tempIterationHolder[j] = (constants[orderOfRows[j]] - tempIterationHolder[j]) / coefficients[orderOfRows[j]][j];
                step += String.format(") / %f\n", coefficients[orderOfRows[j]][j]);
            }
            step += "\n";
            //after each iteration the results are saved into the initial array to be used in the next iteration.
            for(int j = 0; j < initial.length; j++) {
                double currentRelativeError = Math.abs((tempIterationHolder[j] - initial[j]) / tempIterationHolder[j]);
                if(currentRelativeError > maxError)
                    maxError = currentRelativeError;
                step += String.format("New X%d = %f\t Absolute Relative Error = %f\n", j + 1, tempIterationHolder[j], currentRelativeError);
            }
            step += "\n";
            outputFrame.printSteps(step);
            initial = tempIterationHolder.clone();
            iteration++;
            if(iteration > numberOfInterations) {
                outputFrame.printSteps("Solution may not converge\n");
                return initial;
            }
        }
        return initial;
    }

}
