package presentation;

import utils.Constants;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ViewModelPanel extends JPanel {
    private ViewModel viewModel;
    private JButton nextButton;
    private JButton previousButton;

    public ViewModelPanel() {
        super();
        nextButton = new JButton(Constants.NEXT);
        previousButton = new JButton(Constants.PREVIOUS);
        this.add(previousButton);
        this.add(nextButton);

        nextButton.addActionListener(e -> {
            viewModel.nextPage();

        });
        previousButton.addActionListener(e -> {
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
        nextButton.setVisible(viewModel.checkNextPage());
        previousButton.setVisible(viewModel.checkPreviousPage());
        repaint();
    }

    public void panelShown() {

    }
    public void panelHidden() {

    }
}
