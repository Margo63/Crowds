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
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cards = new JPanel(cardLayout);
        frame.add(cards);
    }

    public void addPanel(ViewModelPanel panel, String name){
        this.viewModel.setScreen(this);
        panel.setViewModel(this.viewModel);
        this.cards.add(panel, name);
    }
    public void changePanel(String name){
        cardLayout.show(cards,name);
    }

}
