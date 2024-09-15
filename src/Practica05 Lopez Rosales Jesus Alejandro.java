import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class RelojAnalogico extends JFrame {

    public RelojAnalogico() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setResizable(false);
        setTitle("Reloj Analógico");
    }

    public void paint(Graphics g) {
        super.paint(g);

        BufferedImage imageBackground = null;
        try {
            imageBackground = ImageIO.read(new File("src/img/fondo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imageBackground != null) {
            g.drawImage(imageBackground, 0, 0, getWidth(), getHeight(), this);
        }

        Graphics2D g2 = (Graphics2D) g;


        int centerX = 400;
        int centerY = 200;
        int radius = 100;

        GradientPaint gradient = new GradientPaint(0, 0, Color.ORANGE, 800, 600, Color.WHITE);
        g2.setPaint(gradient);
        g2.fillOval(centerX - radius - 25, centerY - radius - 25, 2 * radius + 40, 2 * radius + 40);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int x = (int) (centerX + radius * Math.cos(angle)) - 10;
            int y = (int) (centerY + radius * Math.sin(angle)) + 5;
            g2.drawString(Integer.toString(i), x, y);
        }
    }

    public static void main(String[] args) {
        RelojAnalogico ventana = new RelojAnalogico();
        ventana.setVisible(true);
    }
}
