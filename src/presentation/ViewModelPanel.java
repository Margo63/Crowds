package presentation;

import utils.ConstantUtil;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ViewModelPanel extends JPanel {
    private ViewModel viewModel;
    private JButton nextButton;
    private JButton previousButton;

    public ViewModelPanel() {
        super();
        nextButton = new JButton(ConstantUtil.NEXT);
        previousButton = new JButton(ConstantUtil.PREVIOUS);
        this.add(previousButton);
        this.add(nextButton);

        nextButton.addActionListener(e -> {
            viewModel.loadNextPage();

        });
        previousButton.addActionListener(e -> {
            viewModel.loadPreviousPage();
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
    public void showAnalysis(){
        viewModel.showAnalysis();
    }

    public void panelShown() {

    }
    public void panelHidden() {

    }
}
