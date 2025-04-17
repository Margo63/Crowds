package presentation.input;

import presentation.ViewModelPanel;
import utils.Constants;
import utils.ReadFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;

public class InputFilePanel extends ViewModelPanel {

    public InputFilePanel() {
        JLabel label = new JLabel("chooose file: ");
        this.add(label);
        JButton buttonChooseFile = new JButton("Choose File");
        this.add(buttonChooseFile);
        buttonChooseFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
                ReadFile readFile = new ReadFile();
                String path = chooser.getSelectedFile().getAbsolutePath();
                ArrayList<ArrayList<Integer>> result = readFile.readFile(path);
                if(!result.isEmpty()){
                    label.setText("chooose file: " + path);
                    System.out.println("load int");
                    this.getViewModel().loadBoardFromInteger(result);
                    repaint();
                }
            }
        });




//        this.setLayout(new GridLayout(2, 1));
//
//
//        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        row1.add(label);
//        row1.add(buttonChooseFile);




 //       this.add(row1);
//        this.add(row2);


    }


}
