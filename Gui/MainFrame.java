package Gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private ComboBox methodComboBox, LUComboBox;
    private TextArea equationsArea;
    private Button calcButton;
    private TextField precisionTxtField, TimeField, initialGuessField, noOfIterationsField, absRelativeErrorField;
    private Panel LUPanel, IterativePanel;
    private MainFrame frame = this;

    MainFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Eliminations");
        this.setSize(700, 700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(0xDDDDDD));

        Panel containerPanel = new Panel(10, 10, 665, 100, true);

        /* Choose Method Panel */
        Panel methodPanel = new Panel(210, 80, true);
        Label chooseMethodLabel = new Label("Choose Method");
        String Methods[] = { "Gauss", "Gauss-Jordan", "LU Decomposition", "Gauss-Seidel", "Jacobi-Iteration" };
        methodComboBox = new ComboBox(Methods);
        methodComboBox.addActionListener(methodListener);
        methodPanel.getPanel().add(chooseMethodLabel.getLabel());
        methodPanel.getPanel().add(methodComboBox.getComboBox());

        /*****************************************************/

        /* Precision Panel */

        Panel precisionPanel = new Panel(200, 80, true);
        Label precisionLabel = new Label("Precision");
        precisionTxtField = new TextField(true);
        precisionPanel.getPanel().add(precisionLabel.getLabel());
        precisionPanel.getPanel().add(precisionTxtField.getField());

        /****************************************** */

        /* Time Panel */

        Panel timePanel = new Panel(200, 80, true);
        Label timeLabel = new Label("Time");
        TimeField = new TextField(false);
        timePanel.getPanel().add(timeLabel.getLabel());
        timePanel.getPanel().add(TimeField.getField());
        /*************************************************** */

        // containerPanel.addComponents({methodPanel, precisionLabel, timePanel});
        containerPanel.getPanel().add(methodPanel.getPanel());
        containerPanel.getPanel().add(precisionPanel.getPanel());
        containerPanel.getPanel().add(timePanel.getPanel());

        /***** Lu Panel*************** */
        LUPanel = new Panel(10, 120, 665, 100, false);
        String LUForms[] = { "Downlittle Form", "Crout Form", "Cholesky Form" };
        ComboBox LUComboBox = new ComboBox(LUForms);
        // LUComboBox.addActionListener(this);
        // ////////////////////////////////////////////////////////////////////////////////////////////////////////
        LUPanel.getPanel().add(LUComboBox.getComboBox());

        /************** Iterative panel *********************/
        IterativePanel = new Panel(10, 120, 660, 100, false);
        Panel initialguessPanel = new Panel(210, 80, true);
        Label initialguessLabel = new Label("Initial Guess");
        // initialguessLabel.setPreferredSize(new Dimension(100, 40));
        // /////////////////////////////////////////////////////////////////////////////////
        initialGuessField = new TextField(true);
        initialguessPanel.getPanel().add(initialguessLabel.getLabel());
        initialguessPanel.getPanel().add(initialGuessField.getField());

        Panel noOfIterationsPanel = new Panel(210, 80, true);
        Label noOfIterationsLabel = new Label("No. Of Iterations");
        // noOfIterationsLabel.setPreferredSize(new Dimension(100, 40));
        // ////////////////////////////////////////////////////////////////////////////////////
        noOfIterationsField = new TextField(true);
        noOfIterationsPanel.getPanel().add(noOfIterationsLabel.getLabel());
        noOfIterationsPanel.getPanel().add(noOfIterationsField.getField());

        Panel absRelativeErrorPanel = new Panel(210, 80, true);

        Label absRelativeErrorLabel = new Label("Relative Error");
        // absRelativeErrorLabel.setPreferredSize(new Dimension(100, 40));
        // //////////////////////////////////////////////////////////////////////////////
        absRelativeErrorField = new TextField(true);
        // absRelativeErrorField.setPreferredSize(new Dimension(100, 40));
        // ////////////////////////////////////////////////////////////////////////////////

        absRelativeErrorPanel.getPanel().add(absRelativeErrorLabel.getLabel());
        absRelativeErrorPanel.getPanel().add(absRelativeErrorField.getField());

        IterativePanel.getPanel().add(initialguessPanel.getPanel());
        IterativePanel.getPanel().add(noOfIterationsPanel.getPanel());
        IterativePanel.getPanel().add(absRelativeErrorPanel.getPanel());

        /************************************************************/

        equationsArea = new TextArea();

        JScrollPane EqautionsPane = new JScrollPane(equationsArea.getArea());
        EqautionsPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        EqautionsPane.setBackground(new Color(0xFFFFFF));
        EqautionsPane.setBounds(300, 250, 360, 350);

        Panel InputFormatPanel = new Panel(30, 350, 250, 250, true);

        Label inFormatLabel = new Label("Equations must be in this form");
        InputFormatPanel.getPanel().add(inFormatLabel.getLabel());

        Label directinglabel = new Label("Input the Equations:");
        directinglabel.getLabel().setHorizontalAlignment(JLabel.CENTER);
        directinglabel.getLabel().setBounds(30, 250, 250, 80);
        // ////////////////////////////////////////////////////////////////

        calcButton = new Button(300, 600, 150, 50);
        calcButton.addActionListener(buttonListener);
        // calcButton.addActionListener(this);/////////////////////////////////////////////////////////////////////////////

        this.add(IterativePanel.getPanel());
        this.add(LUPanel.getPanel());
        this.add(containerPanel.getPanel());
        this.add(directinglabel.getLabel());
        this.add(InputFormatPanel.getPanel());
        this.add(EqautionsPane);
        this.add(calcButton.getButton());
        this.setVisible(true);
    }

    String[] getEquations() {
        return equationsArea.getArea().getText().split("\\n");
    }

    public String getMethod() {
        return methodComboBox.getItem();
    }

    public String[] getInitialGuess() {
        return initialGuessField.getField().getText().split(",");
    }

    public int getNoOfIterations() {
        return Integer.parseInt(noOfIterationsField.getField().getText());
    }

    public double getAbsRelativeErrorField() {
        return Double.parseDouble(absRelativeErrorField.getField().getText());
    }

    public int getPrecision() {
        return Integer.parseInt(precisionTxtField.getField().getText());
    }

    public void setTime(String time) {
        TimeField.getField().setText(time);
    }

    public String getLUForm() {
        return LUComboBox.getItem();
    }

    public Panel getLUPanel() {
        return LUPanel;
    }
    
    public Panel getIterativePanel() {
        return IterativePanel;
    }

    ActionListener buttonListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            new InputHandler(frame).solve();
        }
    };

    ActionListener methodListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            new InputHandler(frame).setMethod();
        }
    };

    // public void actionPerformed(ActionEvent e) {

        // if (e.getSource() == calcButton) {

        //     String[] Equations = equationsArea.getArea().getText().split("\\n");
        //     double A[][] = new double[Equations.length][Equations.length];
        //     double B[] = new double[Equations.length];
        //     int i = 0;
        //     for (String equation : Equations) {
        //         A[i] = cofficientsExtractor(equation, B, i);
        //         i++;
        //     }

        //     double[] intialguess = new double[Equations.length];
        //     int noOfIterations;
        //     double AbsRealtiveError;
        //     if (method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration")) {
        //         String[] intialGuessesArray = initialGuessField.getField().getText().split(",");
        //         for (int k = 0; k < intialGuessesArray.length; k++) {
        //             intialguess[k] = Double.parseDouble(intialGuessesArray[k]);
        //         }
        //         noOfIterations = Integer.parseInt(noOfIterationsField.getField().getText());
        //         AbsRealtiveError = Double.parseDouble(absRelativeErrorField.getField().getText());
        //     }

        //     switch (method) {
        //         case "Gauss": {
        //             OutputFrame outputframe = new OutputFrame();
        //             GaussElimination g = new GaussElimination(A, B,
        //                     Integer.parseInt(precisionTxtField.getField().getText()));
        //             System.out.println(Arrays.toString(g.getResult()));
        //             outputframe.setResult(g.getResult());
        //             outputframe.setOutputAreaText();
        //             TimeField.getField().setText(String.valueOf(g.getTime()));
        //             break;
        //         }

        //         case "Jordan": {
        //             OutputFrame outputframe = new OutputFrame();
        //             GaussJordan gj = new GaussJordan(A, B, Integer.parseInt(precisionTxtField.getField().getText()));
        //             System.out.println(Arrays.toString(gj.getResult()));
        //             outputframe.setResult(gj.getResult());
        //             outputframe.setOutputAreaText();
        //             TimeField.getField().setText(String.valueOf(gj.getTime()));
        //             break;
        //         }

        //         case "LU Decomposition": {
        //             switch (LUForm) {
        //                 case "Downlittle Form": {
        //                     OutputFrame outputframe = new OutputFrame();
        //                     break;
        //                 }

        //                 case "Crout Form": {
        //                     OutputFrame outputframe = new OutputFrame();
        //                     break;
        //                 }

        //                 case "Cholesky Form": {
        //                     OutputFrame outputframe = new OutputFrame();
        //                     break;
        //                 }
        //             }
        //             break;
        //         }
        //         case "Gauss-Seidel": {
        //             OutputFrame outputframe = new OutputFrame();
        //             break;
        //         }
        //         case "Jacobi-Iteration": {
        //             OutputFrame outputframe = new OutputFrame();
        //             break;
        //         }
        //     }
        // }

        // if (e.getSource() == methodComboBox.getComboBox()) {
        //     method = methodComboBox.getComboBox().getSelectedItem().toString();

        //     if (method.equals("LU Decomposition"))
        //         LUPanel.getPanel().setVisible(true);
        //     else
        //         LUPanel.getPanel().setVisible(false);

        //     if (method.equals("Gauss-Seidel") || method.equals("Jacobi-Iteration"))
        //         IterativePanel.getPanel().setVisible(true);
        //     else
        //         IterativePanel.getPanel().setVisible(false);
        // }

        // if (e.getSource() == LUComboBox) {
        //     LUForm = LUComboBox.getComboBox().getSelectedItem().toString();
        // }

    // }
}
