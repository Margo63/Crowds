package presentation;

import javax.swing.*;

public class InputPanel extends JPanel {

    public InputPanel(){
        JButton button = new JButton("Next");
        JButton buttonChooseFile = new JButton("Choose File");
        this.add(button);
        button.addActionListener(e -> {
            System.out.println("next button clicked");
        });
    }



}
