package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadButtons();
            }
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
    private void loadButtons() {
        next.setVisible(viewModel.checkNextButton());
        previous.setVisible(viewModel.checkPreviousButton());
        repaint();
    }
}
