package Gui;

public class Events {

    // @Override
    // public void actionPerformed(ActionEvent e) {

    //     if (e.getSource() == calcButton) {

    //         String[] Equations = EquationsArea.getText().split("\\n");
    //         double A[][] = new double[Equations.length][Equations.length];
    //         double B[] = new double[Equations.length];
    //         int i = 0;
    //         for (String equation : Equations) {
    //             A[i] = cofficientsExtractor(equation, B, i);
    //             i++;
    //         }

    //         double[] intialguess = new double[Equations.length];
    //         int noOfIterations;
    //         double AbsRealtiveError;
    //         if (method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration")) {
    //             String[] intialGuessesArray = initialguessField.getText().split(",");
    //             for (int k = 0; k < intialGuessesArray.length; k++) {
    //                 intialguess[k] = Double.parseDouble(intialGuessesArray[k]);
    //             }
    //             noOfIterations = Integer.parseInt(noOfIterationsField.getText());
    //             AbsRealtiveError = Double.parseDouble(absRelativeErrorField.getText());
    //         }

    //         switch (method) {
    //             case "Gauss":
    //                 GaussElimination g = new GaussElimination(A, B, Integer.parseInt(presicionTxtField.getText()));
    //                 System.out.println(Arrays.toString(g.getResult()));
    //                 OutputFrame outputframe = new OutputFrame();
    //                 outputframe.setResult(g.getResult());
    //                 outputframe.setOutputAreaText();
    //                 TimeField.setText(String.valueOf(g.getTime()));
    //                 break;
    //             case "Jordan":
    //                 GaussJordan gj = new GaussJordan(A, B, Integer.parseInt(presicionTxtField.getText()));
    //                 System.out.println(Arrays.toString(gj.getResult()));
    //                 OutputFrame outputframe1 = new OutputFrame();
    //                 outputframe1.setResult(gj.getResult());
    //                 outputframe1.setOutputAreaText();
    //                 TimeField.setText(String.valueOf(gj.getTime()));
    //                 break;
    //             case "LU Decomposition":
    //                 switch (LUmethod) {
    //                     case "Downlittle Form":
    //                         OutputFrame outputframe2 = new OutputFrame();
    //                         break;

    //                     case "Crout Form":
    //                         OutputFrame outputframe3 = new OutputFrame();
    //                         break;

    //                     case "Cholesky Form":
    //                         OutputFrame outputframe4 = new OutputFrame();
    //                         break;
    //                 }
    //                 break;
    //             case "Gauss-Seidel":
    //                 OutputFrame outputframe5 = new OutputFrame();
    //                 break;
    //             case "Jacobi-Iteration":
    //                 OutputFrame outputframe6 = new OutputFrame();
    //                 break;
    //         }
    //     }

    //     if (e.getSource() == methodComboBox) {
    //         method = methodComboBox.getSelectedItem().toString();

    //         if (method.equals("LU Decomposition"))
    //             LUPanel.setVisible(true);
    //         else
    //             LUPanel.setVisible(false);

    //         if (method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration"))
    //             IterativePanel.setVisible(true);
    //         else
    //             IterativePanel.setVisible(false);
    //     }

    //     if (e.getSource() == LUComboBox) {
    //         LUmethod = LUComboBox.getSelectedItem().toString();
    //     }

    // }
}
