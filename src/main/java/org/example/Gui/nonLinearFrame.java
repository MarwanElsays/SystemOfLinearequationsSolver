package org.example.Gui;

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

public class nonLinearFrame extends JFrame {

    private ComboBox methodComboBox, LUComboBox;
    private TextArea equationsArea;
    private Button calcButton;
    private TextField precisionTxtField, TimeField, initialGuessField, noOfIterationsField, absRelativeErrorField;
    private Panel IterativePanel;
    private nonLinearFrame frame = this;

    nonLinearFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Eliminations");
        this.setSize(800, 700);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(0xDDDDDD));
        this.setLocationRelativeTo(null);

        Panel containerPanel = new Panel(10, 15, 765, 70, true);

        /* Choose Method Panel */
        Panel methodPanel = new Panel(250, 50, true);
        Label chooseMethodLabel = new Label("Method");
        String Methods[] = {"Bisection","False-Position","Fixed point","Newton-Raphson","Secant Method"};
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
        this.add(containerPanel.getPanel());
        this.add(directinglabel.getLabel());
        this.add(InputFormatPanel.getPanel());
        this.add(EqautionsPane);
        this.add(calcButton.getButton());
        this.setVisible(true);
    }

    public String getEquations() {
        return equationsArea.getArea().getText();
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
    
    public Panel getIterativePanel() {
        return IterativePanel;
    }

    public JButton getButton() {
        return calcButton.getButton();
    }

    public double getInitialGuess() {
        String s = initialGuessField.getField().getText().equals("") ? "1" : initialGuessField.getField().getText();
        return Double.parseDouble(s);
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
            new nonLinearInputHandler(frame).solve();
        }
    };

    ActionListener methodListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            new nonLinearInputHandler(frame).setMethod();
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
            new nonLinearInputHandler(frame).ChangeColor(new Color(0xFF8C00));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            new nonLinearInputHandler(frame).ChangeColor(Color.ORANGE);
        }

    };
}
