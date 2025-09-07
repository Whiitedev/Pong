package pong;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.*;

public class Frame {
   Frame() {
       JFrame frame = new JFrame("Pong");
       Pong pong = new Pong();
       frame.add(pong);
       frame.setSize(800, 500);
       frame.setResizable(false);
       frame.setLocationRelativeTo(null);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Image retValue = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagen/LOGO1.png"));
       frame.setIconImage(retValue);
       frame.setVisible(true);
   }
}

