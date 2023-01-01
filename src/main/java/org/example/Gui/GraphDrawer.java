package org.example.Gui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class GraphDrawer extends Canvas {

    private int width;
    private int height;
    private Expression[] functions;
    private final Random random = new Random();

    public GraphDrawer(String[] functionString, int width, int height) {
        this.width = width;
        this.height = height;
        functions = new Expression[functionString.length];
        for (int i = 0; i < functionString.length; i++) {
            functions[i] = new ExpressionBuilder(functionString[i]).variable("x").build();
        }
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("hi");
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(4, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs);
        g2d.setColor(Color.black);
        g2d.drawLine(width / 2, 0, width / 2, height);
        g2d.drawLine(0, height / 2, width, height / 2);
        g2d.setFont(new Font("Arial", Font.PLAIN, 15));
        drawXAxis(g2d);
        drawYAxis(g2d);
        for (int n = 0; n < functions.length; n++) { // draw functions
            g2d.setColor(getRandomColor());
            for (int i = 0; i < width; i++) {
                double x = 0.02 * (i - width / 2);
                int y = height / 2 - (int) (50 * f(x, n));
                int yNext = height / 2 - (int) (50 * f(x + 0.02, n));
                g2d.drawLine(i, y, i + 1, yNext);
            }
        }
    }

    private double f(double x, int index) {
        return functions[index].setVariable("x", x).evaluate();
    }

    private void drawXAxis(Graphics2D g2d) {
        for (int i = 0; i < width; i++) {
            if (i % 50 == 0) { // every 50 pixels = one unit
                g2d.drawLine(i, height / 2 - 5, i, height / 2 + 5);
                if (i / 50 != 8) {
                    String index = Integer.toString(-8 + i / 50);
                    g2d.drawString(index, i - 5, height / 2 + 20);
                }
            }
        }
    }

    private void drawYAxis(Graphics2D g2d) {
        for (int i = 0; i < height; i++) {
            if (i % 50 == 0) { // every 50 pixels = one unit
                g2d.drawLine(width / 2 - 5, i, width / 2 + 5, i);
                if (i / 50 != 8) {
                    String index = Integer.toString(8 - i / 50);
                    g2d.drawString(index, width / 2 - 20, i + 5);
                }
            }
        }
    }

    private Color getRandomColor() {
        Color[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.pink };
        return colors[random.nextInt(colors.length)];
    }
}
