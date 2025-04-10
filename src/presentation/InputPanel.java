package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.function.Function;

public class InputPanel extends JPanel {

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
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getAbsolutePath());

            }
        });
    }



}
