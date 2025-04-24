package presentation.input;

import presentation.ViewModelPanel;
import utils.Constants;
import utils.Files;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;

public class InputFilePanel extends ViewModelPanel {

    public InputFilePanel() {
        JLabel label = new JLabel(Constants.CHOSEN_FILE);
        this.add(label);
        JButton buttonChooseFile = new JButton(Constants.CHOOSE_FILE_BUTTON);
        this.add(buttonChooseFile);
        buttonChooseFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Files readFile = new Files();
                String path = chooser.getSelectedFile().getAbsolutePath();
                ArrayList<ArrayList<Integer>> result = readFile.readFile(path);
                if(result!=null && !result.isEmpty()){
                    label.setText(Constants.CHOSEN_FILE + path);
                    this.getViewModel().setBoardFromInteger(result);
                    repaint();
                }
            }
        });

    }


}
