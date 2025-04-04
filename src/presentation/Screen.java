package presentation;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen {
    private JFrame frame;
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards;
    public Screen() {
        frame = new JFrame();
        //frame.add(panel);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





        cards = new JPanel(cardLayout);
    //    cards.add(panel1,"input");

        frame.add(cards);
    }

    public void addPanel(JPanel panel, String name){
        this.cards.add(panel, name);
    }

    public void changePanel(String name){
        cardLayout.show(cards,name);
    }



}
