package presentation;

import model.Board;

import javax.swing.*;

public class Screen {

    public Screen(Panel panel) {
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(1000,1000);
        frame.setVisible(true);

        //frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
