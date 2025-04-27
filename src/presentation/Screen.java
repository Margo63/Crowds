package presentation;

import javax.swing.*;
import java.awt.*;

public class Screen {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cards;
    private ViewModel viewModel;

    public Screen() {
        cardLayout = new CardLayout();
        frame = new JFrame();
        viewModel = new ViewModel();
        this.viewModel.setScreen(this);

        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cards = new JPanel(cardLayout);
        frame.add(cards);
    }

    public void addPanel(ViewModelPanel panel, String name){
        panel.setViewModel(this.viewModel);
        this.cards.add(panel, name);
    }
    public void changePanel(String name){
        cardLayout.show(cards,name);
    }

}
