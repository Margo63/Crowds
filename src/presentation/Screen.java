package presentation;

import javax.swing.*;
import java.awt.*;

public class Screen {
    private JFrame frame;
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards;
    private ViewModel viewModel;

    public Screen() {
        frame = new JFrame();
        viewModel = new ViewModel();
        //frame.add(panel);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cards = new JPanel(cardLayout);
    //    cards.add(panel1,"input");

        frame.add(cards);
    }

    public void addPanel(ViewModelPanel panel, String name){
        panel.addViewModel(this.viewModel);
        panel.addScreen(this);
        this.cards.add(panel, name);
    }
    public void changePanel(String name){
        //System.out.println("name = " + name);
        cardLayout.show(cards,name);
    }

}
