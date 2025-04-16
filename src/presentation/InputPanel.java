package presentation;

import utils.ReadFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Function;

public class InputPanel extends ViewModelPanel {

    public InputPanel() {
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
                    this.getViewModel().setBoard(result);
                    repaint();
                }
            }
        });


        JButton buttonNext = new JButton("Next");

        this.add(buttonNext);
        buttonNext.addActionListener(e -> {
            this.getScreen().changePanel("draw");
        });

        this.setLayout(new GridLayout(2, 1)); // 2 строки, 1 столбец


        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(label);
        row1.add(buttonChooseFile);

        // Вторая строка: 4 кнопки
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(buttonNext);


        this.add(row1);
        this.add(row2);


    }


}
