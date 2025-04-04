package presentation;

import javax.swing.*;
import java.awt.*;

public class Screen {
    private JFrame frame;
    private CardLayout cardLayout;

    public Screen(JPanel panel) {
        frame = new JFrame();
        //frame.add(panel);
        frame.setSize(1000,1000);
        frame.setVisible(true);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




}
