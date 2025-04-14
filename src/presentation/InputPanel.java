package presentation;

import utils.ReadFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class InputPanel extends ViewModelPanel {

    public InputPanel(){
        JButton buttonChooseFile = new JButton("Choose File");
        this.add(buttonChooseFile);

        buttonChooseFile.addActionListener(e->{
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                //System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
                ReadFile readFile = new ReadFile();
                this.getViewModel().setBoard(readFile.readFile(chooser.getSelectedFile().getAbsolutePath()));
            }
        });

        JButton buttonDraw = new JButton("draw");
        buttonDraw.addActionListener(e-> {
            this.getScreen().changePanel("draw");
        });

        this.add(buttonDraw);
        JButton buttonNext = new JButton("Next");

        this.add(buttonNext);
        buttonNext.addActionListener(e->{
           this.getScreen().changePanel("board");
        });

    }



}
