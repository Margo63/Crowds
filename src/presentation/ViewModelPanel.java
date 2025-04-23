package presentation;

import utils.Constants;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ViewModelPanel extends JPanel {
    private ViewModel viewModel;
    JButton next;
    JButton previous;

    public ViewModelPanel() {
        super();
        next = new JButton(Constants.NEXT);
        previous = new JButton(Constants.PREVIOUS);
        this.add(previous);
        this.add(next);

        next.addActionListener(e -> {
            viewModel.nextPage();

        });
        previous.addActionListener(e -> {
            viewModel.previousPage();
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadButtons();
                panelShown();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                panelHidden();
            }
        });


    }


    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
        loadButtons();
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    private void loadButtons() {
        next.setVisible(viewModel.checkNextPage());
        previous.setVisible(viewModel.checkPreviousPage());
        repaint();
    }

    public void panelShown() {

    }
    public void panelHidden() {

    }
}
