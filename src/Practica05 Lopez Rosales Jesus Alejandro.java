import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

class RelojAnalogico extends JFrame implements Runnable {

    private int hours, minutes, seconds;
    private BufferedImage imageBackground;

    public RelojAnalogico() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setResizable(false);
        setTitle("Reloj Analógico");

        try {
            imageBackground = ImageIO.read(new File("src/img/fondo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Iniciar el hilo
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void paint(Graphics g) {
        // Crear una imagen para el doble búfer
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        // Suavizar el renderizado
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Dibujar el fondo
        if (imageBackground != null) {
            g2.drawImage(imageBackground, 0, 0, getWidth(), getHeight(), this);
        }

        // Coordenadas del centro del reloj
        int centerX = 400;
        int centerY = 200;
        int radius = 100;

        // Dibujar el círculo del reloj
        GradientPaint gradient = new GradientPaint(0, 0, Color.ORANGE, 800, 600, Color.WHITE);
        g2.setPaint(gradient);
        g2.fillOval(centerX - radius - 25, centerY - radius - 25, 2 * radius + 40, 2 * radius + 40);

        // Dibujar los números del reloj
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int x = (int) (centerX + radius * Math.cos(angle)) - 10;
            int y = (int) (centerY + radius * Math.sin(angle)) + 5;
            g2.drawString(Integer.toString(i), x, y);
        }

        // Dibujar las manecillas con estilos
        drawHand(g2, centerX, centerY, radius - 40, (hours % 12) * 30 + minutes / 2 - 90, 8, Color.DARK_GRAY);  // Horas
        drawHand(g2, centerX, centerY, radius - 30, minutes * 6 - 90, 5, Color.BLACK);  // Minutos
        drawHand(g2, centerX, centerY, radius - 20, seconds * 6 - 90, 2, Color.RED);  // Segundos

        // Dibujar el buffer en la pantalla
        g.drawImage(buffer, 0, 0, this);
        g2.dispose();  // Liberar recursos gráficos
    }

    // Método para dibujar una manecilla
    private void drawHand(Graphics2D g2, int centerX, int centerY, int length, double angle, int width, Color color) {
        double radianAngle = Math.toRadians(angle);
        int x = (int) (centerX + length * Math.cos(radianAngle));
        int y = (int) (centerY + length * Math.sin(radianAngle));

        g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));  // Grosor redondeado
        g2.setColor(color);
        g2.drawLine(centerX, centerY, x, y);
    }

    // Implementación del hilo para actualizar las manecillas del reloj
    @Override
    public void run() {
        while (true) {
            // Obtener la hora actual
            Calendar now = Calendar.getInstance();
            hours = now.get(Calendar.HOUR_OF_DAY);
            minutes = now.get(Calendar.MINUTE);
            seconds = now.get(Calendar.SECOND);

            // Repintar la ventana con las manecillas actualizadas
            repaint();

            try {
                // Esperar un segundo antes de actualizar
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        RelojAnalogico ventana = new RelojAnalogico();
        ventana.setVisible(true);
    }
}
