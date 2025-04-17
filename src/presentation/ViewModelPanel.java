package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewModelPanel extends JPanel {
    private ViewModel viewModel;
    JButton next;
    JButton previous;
    public ViewModelPanel() {
        super();
        next = new JButton("next");
        previous = new JButton("previous");
        this.add(previous);
        this.add(next);

        next.addActionListener(e -> {
            viewModel.next();

        });
        previous.addActionListener(e -> {
            viewModel.previous();
        });




    }


    public void addViewModel(ViewModel viewModel) {
        //System.out.println("addViewModel");
        this.viewModel = viewModel;
        loadButtons();
    }

    public ViewModel getViewModel() {
        return viewModel;
    }
    public void loadButtons() {
        next.setVisible(viewModel.checkNextButton());
        previous.setVisible(viewModel.checkPreviousButton());
        repaint();
    }
}
