package Gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
        this.getContentPane().setBackground(new Color(0xDDDDDD));
        this.setLocationRelativeTo(null);

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
        containerPanel.AddComponents(c1);

        /***** Lu Panel*************** */
        LUPanel = new Panel(10, 110, 765, 100, false);
        LUPanel.setlayoutNull();
        String LUForms[] = { "Downlittle Form", "Crout Form", "Cholesky Form" };
        Label LULabel = new Label("Choose Form: ",20,35,160,40);
        LUComboBox = new ComboBox(LUForms,250,35,160,40);
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

        Label directinglabel = new Label("Input the Equations:");
        directinglabel.getLabel().setHorizontalAlignment(JLabel.CENTER);
        directinglabel.getLabel().setBounds(30, 250, 250, 80);
        // ////////////////////////////////////////////////////////////////

        calcButton = new Button(350, 600, 150, 50);
        calcButton.addActionListener(buttonListener);
        calcButton.addMouseListener(buttonMouseListener);
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

    public JButton getButton() {
        return calcButton.getButton();
    }

    public String[] getInitialGuess() {
        String s = initialGuessField.getField().getText().equals("") ? "1,1,1" : initialGuessField.getField().getText();
        return s.split(",");
    }

    public int getNoOfIterations() {
        String s = noOfIterationsField.getField().getText().equals("") ? "20" : noOfIterationsField.getField().getText();
        return Integer.parseInt(s);
    }

    public double getAbsRelativeErrorField() {
        String s = absRelativeErrorField.getField().getText().equals("") ? "0.001" : absRelativeErrorField.getField().getText();
        return Double.parseDouble(s);
    }

    public int getPrecision() {
        String s = precisionTxtField.getField().getText().equals("") ? "5" : precisionTxtField.getField().getText();
        return Integer.parseInt(s);
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

    MouseListener buttonMouseListener = new MouseListener(){

        @Override
        public void mouseClicked(MouseEvent e) { 
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) { 
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            new InputHandler(frame).ChangeColor(new Color(0xFF8C00));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            new InputHandler(frame).ChangeColor(Color.ORANGE);
        }

    };
}
