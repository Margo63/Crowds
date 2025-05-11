package presentation.input;

import presentation.ViewModelPanel;
import utils.ConstantUtil;
import utils.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;

public class InputFilePanel extends ViewModelPanel {

    public InputFilePanel() {


        JLabel label = new JLabel(ConstantUtil.CHOSEN_FILE);
        this.add(label);

        JButton buttonChooseFile = new JButton(ConstantUtil.CHOOSE_FILE_BUTTON);
        this.add(buttonChooseFile);

        buttonChooseFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                ArrayList<ArrayList<Integer>> result = FileUtils.readFile(path);
                if(result!=null && !result.isEmpty()){
                    label.setText(ConstantUtil.CHOSEN_FILE + path);
                    this.getViewModel().setBoardFromInteger(result);
                    repaint();
                }
            }
        });

    }


}
