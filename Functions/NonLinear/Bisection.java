package Functions.NonLinear;

import java.util.Vector;

public class Bisection {
    private double xUpper, xLower, xRoot, fUpper, fLower, fRoot, relativeError;
    private double epsilonTolerance;
    private int maxIterations, precision;

    public Bisection(double xUpper, double xLower, int precision) {
        this.xUpper = xUpper;
        this.xLower = xLower;
        this.precision = precision;
        this.maxIterations = 50;
        this.epsilonTolerance = 0.5 * Math.pow(10, (2-precision));
    }

    private double function(double x){
        return Math.pow(x, 3) - 0.165 * Math.pow(x, 2) + 3.993 * 1E-4;
    }

    public double calcRoot(){

        this.xRoot = (this.xUpper + this.xLower) / 2;
        this.fUpper = function(this.xUpper); this.fLower = function(this.xLower); this.fRoot = function(this.xRoot);
        this.relativeError = Double.MAX_VALUE;
        double prevRoot = xRoot;
        this.maxIterations--;

        while(this.maxIterations != 0) {

            if((this.fRoot == 0) || (this.relativeError <= this.epsilonTolerance)){break;}
            if(this.fUpper * this.fRoot > 0){
                this.xUpper = this.xRoot;
                this.fUpper = this.fRoot;
            }
            else if(this.fUpper * this.fRoot < 0){
                this.xLower = this.xRoot;
                this.fLower = this.fRoot;
            }

            this.xRoot = (this.xUpper + this.xLower) / 2;
            this.fRoot = function(this.xRoot);
            this.relativeError = Math.abs ((this.xRoot - prevRoot) / this.xRoot) * 100;
            prevRoot = xRoot;
            this.maxIterations--;
        }
        return this.xRoot;
    }
}
