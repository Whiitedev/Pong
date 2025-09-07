package pong;

import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Pong extends JPanel implements ActionListener, KeyListener, ComponentListener {

    private int pelotaX = 300;
    private int pelotaY = 200;
    private int pelotaXVelocidad;
    private int pelotaYVelocidad;
    private static final int VelocidadXAumento = 1;
    private static final int VelocidadYAumento = 1;
    private int raqueta1Y = 180;
    private int raqueta2Y = 180;
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private int Puntaje1 = 0;
    private int Puntaje2 = 0;
    private int PantallaAncho;
    private int PantallaAlto;
    private int bumperX = -1;
    private int bumperVelocidad = 2;
    private Bumper bumper;
    private boolean bumperActive = false;
    private Random random = new Random();

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                repaint();
            }
        }
    }

    public Pong() {
        Timer Temporizador = new Timer(10, this);
        Temporizador.start();
        addKeyListener(this);
        setFocusable(true);
        addComponentListener(this);
        pelotaXVelocidad = 1;
        pelotaYVelocidad = 1;
        bumper = new Bumper(PantallaAncho / 2, PantallaAlto / 2, 10, 80);
    }

    private void resetBall() {
        pelotaX = PantallaAncho / 2;
        pelotaY = PantallaAlto / 2;
        pelotaXVelocidad = 2;
        pelotaYVelocidad = 2;
        pelotaXVelocidad = -pelotaXVelocidad;
        pelotaYVelocidad = -pelotaYVelocidad;
    }

    public void setXDirection(int randomXDirection) {
        pelotaXVelocidad = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        pelotaYVelocidad = randomYDirection;
    }

    public void move() {
        pelotaX += pelotaXVelocidad;
        pelotaY += pelotaYVelocidad;

        if (bumperX != -1) {
            bumperX += bumperVelocidad;
        }
    }

    public void componentResized(ComponentEvent e) {
        PantallaAncho = getWidth();
        PantallaAlto = getHeight();
        resetBall();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, PantallaAncho, PantallaAlto);

        g.setColor(Color.WHITE);
        g.fillRect(5, raqueta1Y, 10, 80);
        g.fillRect(PantallaAncho - 10, raqueta2Y, 10, 80);
        g.fillOval(pelotaX, pelotaY, 10, 10);

        g.setColor(Color.GRAY);
        g.drawLine(PantallaAncho / 2, 0, PantallaAncho / 2, PantallaAlto);

        if (bumperActive) {
            bumper.draw(g);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Jugador 1: " + Puntaje1, 100, 20);
        g.drawString("Jugador 2: " + Puntaje2, PantallaAncho - 200, 20);
    }

    public void FinJuego() {
        if (Puntaje1 == 5 || Puntaje1 > 5) {
            JOptionPane.showMessageDialog(null, "HA GANADO EL JUGADOR 1");
            System.exit(0);
        }

        if (Puntaje2 == 5 || Puntaje2 > 5) {
            JOptionPane.showMessageDialog(null, "HA GANADO EL JUGADOR 2");
            System.exit(0);
        }

        if (Puntaje1 == 3 || Puntaje2 == 3) {
            bumperX = PantallaAncho / 2;
        }

        if (Puntaje1 == 3 || Puntaje1 > 3) {
            bumper = new Bumper(PantallaAncho / 2, PantallaAlto / 2, 10, 80);
            bumperActive = true;
        }

        if (Puntaje2 == 3 || Puntaje2 > 3) {
            bumper = new Bumper(PantallaAncho / 2, PantallaAlto / 2, 10, 80);
            bumperActive = true;
        }
    }

    public void actionPerformed(ActionEvent e) {
        Audio audio = new Audio();
        AudioClip rebote_1 = audio.getAudio("/recursos/rebote_pelota1.wav");
        AudioClip rebote_2 = audio.getAudio("/recursos/rebote_pelota2.wav");
        if (bumperActive) {
            bumper.move(pelotaY - bumper.getY());
        }
        if (bumperX != -1) {
            if (pelotaX >= bumperX && pelotaX <= bumperX + 10 && pelotaY >= PantallaAlto / 2 - 10 && pelotaY <= PantallaAlto / 2 + 10) {
                pelotaXVelocidad = -pelotaXVelocidad;
            }
        }

        if (bumperActive && pelotaX >= bumper.getX() && pelotaX <= bumper.getX() + bumper.getWidth() && pelotaY >= bumper.getY() && pelotaY <= bumper.getY() + bumper.getHeight()) {
            pelotaXVelocidad = -pelotaXVelocidad;
        }

        if (pelotaX <= 13 && pelotaY >= raqueta1Y && pelotaY <= raqueta1Y + 80) {
            pelotaXVelocidad = -pelotaXVelocidad;
            pelotaXVelocidad += VelocidadXAumento;
            pelotaYVelocidad += VelocidadYAumento;
            rebote_1.play();
        }

        if (pelotaX >= PantallaAncho - 15 && pelotaY >= raqueta2Y && pelotaY <= raqueta2Y + 80) {
            pelotaXVelocidad = -pelotaXVelocidad;
            pelotaXVelocidad -= VelocidadXAumento;
            pelotaYVelocidad += VelocidadYAumento;
            rebote_1.play();

        }

        if (pelotaY <= 0 || pelotaY >= PantallaAlto - 10) {
            pelotaYVelocidad = -pelotaYVelocidad;
            rebote_2.play();
        }

        if (pelotaX <= 0) {
            Puntaje2++;
            resetBall();
            repaint();
            FinJuego();

        } else if (pelotaX >= PantallaAncho) {
            Puntaje1++;
            resetBall();
            repaint();
            FinJuego();
        }

        if (wPressed && raqueta1Y > 0) {
            raqueta1Y -= 6;
        }

        if (sPressed && raqueta1Y < PantallaAlto - 80) {
            raqueta1Y += 6;
        }

        if (upPressed && raqueta2Y > 0) {
            raqueta2Y -= 6;
        }

        if (downPressed && raqueta2Y < PantallaAlto - 80) {
            raqueta2Y += 6;
        }
        move();
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            wPressed = true;
        }

        if (key == KeyEvent.VK_S) {
            sPressed = true;
        }

        if (key == KeyEvent.VK_UP) {
            upPressed = true;
        }

        if (key == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            wPressed = false;
        }

        if (key == KeyEvent.VK_S) {
            sPressed = false;
        }

        if (key == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (key == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}
