package org.example.Functions.Linear;

import java.util.Stack;

public class MatrixConfigurator {

    public static int[] convertToDiagonalDominant(double[][] coefficients){
        double[] sumOfRows = new double[coefficients.length];
        int[] orderOfRows = new int[coefficients.length];
        Stack<Integer> nonDominantRows = new Stack<Integer>();
        //nonDominantRows stack is used to hold the rows that don't have a diagonally dominant element.

        for (int i = 0; i < coefficients.length; i++){
            sumOfRows[i] = 0;
            //For each row add its elements
            for (int j = 0; j < coefficients[0].length; j++){
                sumOfRows[i] += Math.abs(coefficients[i][j]);
            }
            //after adding the element check which element is larger than the sum minus itself
            //essentially checking if an element is larger than the sum of the rest
            for (int j = 0; j < coefficients[0].length; j++){
                if (Math.abs(coefficients[i][j]) >= sumOfRows[i] - Math.abs(coefficients[i][j]) && orderOfRows[j] == 0) {
                    //add 1 to the row number when putting it in the orderOfRows array to check later whether
                    //there was a diagonally dominant element in the row by checking if the position in orderOfRows is 0
                    orderOfRows[j] = i+1;
                    break;
                }
                //if no element was found that was larger than the sum of the row then add the row number to the stack
                else if(j == coefficients[0].length - 1)
                    nonDominantRows.push(i);
            }
        }
        //for each empty index in the orderOfRows pop the top of the stack into it
        for (int i = 0; i < orderOfRows.length; i++){
            if(!nonDominantRows.empty() && orderOfRows[i] == 0)
                orderOfRows[i] = nonDominantRows.pop();
            else if (orderOfRows[i] != 0)
                orderOfRows[i] -= 1;
        }
        return orderOfRows;
    }

}
