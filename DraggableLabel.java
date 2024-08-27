import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DraggableLabel extends JFrame {

    private final JLabel[] labels = new JLabel[Inputs.numberofIndexes];
    private int mouseX, mouseY;
    private JPanel panel;
    private final int[][] lineValues = Main.Array;
    public DraggableLabel() {
        // Create and set up the panel for custom painting
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.RED); // Color of the lines
                g2d.setStroke(new BasicStroke(2)); // Thin stroke for the lines

                // Draw lines with arrows, loops, and values between labels
                for (int i = 0; i < labels.length; i++) {
                    for (int j = 0; j < labels.length; j++) {
                        if (lineValues[i][j] != 0) { // Only draw if the line value is not zero
                            int x1 = labels[i].getX() + labels[i].getWidth() / 2;
                            int y1 = labels[i].getY() + labels[i].getHeight() / 2;
                            int x2 = labels[j].getX() + labels[j].getWidth() / 2;
                            int y2 = labels[j].getY() + labels[j].getHeight() / 2;

                            if (i == j) {
                                // Draw loop (arc) for self-connection
                                drawLoop(g2d, x1, y1, lineValues[i][j]);
                            } else {
                                // Draw curved line between different points
                                drawCurvedLine(g2d, x1, y1, x2, y2, lineValues[i][j]);
                            }
                        }
                    }
                }
            }
        };
        panel.setLayout(null); // Set layout to null for absolute positioning

        // Create and set up multiple labels
        for (int i = 0; i < labels.length; i++) {
            final int index = i; // Final variable to be used in the inner class
            labels[i] = new JLabel(Integer.toString(i + 1));
            labels[i].setBounds(50 * i + 50, 50 * i + 50, 50, 30); // Adjust position for each label
            labels[i].setOpaque(false);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);

            // Add mouse listeners to handle dragging
            labels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Store the initial mouse position relative to the label
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            });

            labels[i].addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    // Calculate the new position
                    int newX = labels[index].getX() + e.getX() - mouseX;
                    int newY = labels[index].getY() + e.getY() - mouseY;
                    // Set the label's new position
                    labels[index].setLocation(newX, newY);
                    panel.repaint(); // Request a repaint to update the lines
                }
            });

            // Add the label to the panel
            panel.add(labels[i]);
        }

        // Set up the frame
        add(panel); // Add the panel to the frame
        setTitle("Draggable JLabel Example with Conditional Line Drawing and Loops");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void drawCurvedLine(Graphics2D g2d, int x1, int y1, int x2, int y2, int value) {
        // Calculate the control point for the quadratic curve
        int ctrlX = (x1 + x2) / 2 + 50; // Adjust the control point horizontally for curvature
        int ctrlY = (y1 + y2) / 2 - 50; // Adjust the control point vertically for curvature

        // Draw the curved line
        QuadCurve2D curve = new QuadCurve2D.Float(x1, y1, ctrlX, ctrlY, x2, y2);
        g2d.draw(curve);

        // Draw the arrow
        drawArrow(g2d, x1, y1, x2, y2);

        // Draw the value near the curve
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(Integer.toString(value), ctrlX, ctrlY);
    }

    private void drawLoop(Graphics2D g2d, int x, int y, int value) {
        // Draw a loop as an arc around the label
        int loopSize = 40; // Size of the loop
        g2d.drawArc(x - loopSize / 2, y - loopSize / 2, loopSize, loopSize, 0, 360);

        // Draw the value near the loop
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(Integer.toString(value), x - loopSize / 2, y - loopSize - 5);
    }

    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        final int arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // Draw the arrowhead as two lines
        AffineTransform originalTransform = g2d.getTransform();
        g2d.translate(x2, y2);
        g2d.rotate(angle - Math.PI / 2);

        // Set a thin stroke for the arrow lines
        g2d.setStroke(new BasicStroke(1));

        // Left side of the arrow
        g2d.drawLine(0, 0, -arrowSize, -arrowSize);

        // Right side of the arrow
        g2d.drawLine(0, 0, arrowSize, -arrowSize);

        // Restore original transformation
        g2d.setTransform(originalTransform);
    }

    static void call() {
        SwingUtilities.invokeLater(DraggableLabel::new);
    }
}
