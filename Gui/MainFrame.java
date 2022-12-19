package Gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

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
        this.setSize(800, 700);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(0xDDDDDD));

        Panel containerPanel = new Panel(10, 15, 765, 70, true);

        /* Choose Method Panel */
        Panel methodPanel = new Panel(250, 50, true);
        Label chooseMethodLabel = new Label("Method");
        String Methods[] = { "Gauss", "Gauss-Jordan", "LU Decomposition", "Gauss-Seidel", "Jacobi-Iteration" };
        methodComboBox = new ComboBox(Methods);
        methodComboBox.addActionListener(methodListener);
        methodPanel.getPanel().add(chooseMethodLabel.getLabel());
        methodPanel.getPanel().add(methodComboBox.getComboBox());

        /* Precision Panel */
        Panel precisionPanel = new Panel(250, 50, true);
        Label precisionLabel = new Label("Precision");
        precisionTxtField = new TextField(true);
        precisionPanel.getPanel().add(precisionLabel.getLabel());
        precisionPanel.getPanel().add(precisionTxtField.getField());

        /* Time Panel */
        Panel timePanel = new Panel(250, 50, true);
        Label timeLabel = new Label("Time");
        TimeField = new TextField(false);
        timePanel.getPanel().add(timeLabel.getLabel());
        timePanel.getPanel().add(TimeField.getField());

        Component[] c1 = {methodPanel.getPanel(), precisionPanel.getPanel(), timePanel.getPanel()};
        containerPanel.ADDComponents(c1);

        /***** Lu Panel*************** */
        LUPanel = new Panel(10, 110, 765, 100, false);
        LUPanel.setlayoutNull();
        String LUForms[] = { "Downlittle Form", "Crout Form", "Cholesky Form" };
        Label LULabel = new Label("Choose Form: ",20,35,160,40);
        ComboBox LUComboBox = new ComboBox(LUForms,250,35,160,40);
        LUPanel.getPanel().add(LULabel.getLabel());
        LUPanel.getPanel().add(LUComboBox.getComboBox());

        /************** Iterative panel *********************/
        IterativePanel = new Panel(10, 120, 765, 100, false);
        Panel initialguessPanel = new Panel(250, 80, true);
        Label initialguessLabel = new Label("Initial Guess");

        initialGuessField = new TextField(true);
        initialguessPanel.getPanel().add(initialguessLabel.getLabel());
        initialguessPanel.getPanel().add(initialGuessField.getField());

        Panel noOfIterationsPanel = new Panel(250, 80, true);
        Label noOfIterationsLabel = new Label("No. Of Iterations");
        noOfIterationsField = new TextField(true);
        noOfIterationsPanel.getPanel().add(noOfIterationsLabel.getLabel());
        noOfIterationsPanel.getPanel().add(noOfIterationsField.getField());

        Panel absRelativeErrorPanel = new Panel(250, 80, true);

        Label absRelativeErrorLabel = new Label("Relative Error");

        absRelativeErrorField = new TextField(true);


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
        EqautionsPane.setBounds(350, 250, 410, 350);

        Panel InputFormatPanel = new Panel(30, 350, 250, 250, true);

        Label inFormatLabel = new Label("Equations must be in this form");
        TextArea inputForm = new TextArea("a11x1+a12x2+...+a1nxn=b1\na21x1+a22x2+...+a2nxn=b2\n.\n.\n.\n.\nan1x1+an2x2+...+annxn=bn");
        
        InputFormatPanel.getPanel().add(inFormatLabel.getLabel());
        InputFormatPanel.getPanel().add(inputForm.getArea());

        Label directinglabel = new Label("Input the Equations:");
        directinglabel.getLabel().setHorizontalAlignment(JLabel.CENTER);
        directinglabel.getLabel().setBounds(30, 250, 250, 80);
        // ////////////////////////////////////////////////////////////////

        calcButton = new Button(350, 600, 150, 50);
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
}
