import model.Board;
import presentation.Panel;
import presentation.Screen;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(5);
        board.addPedestrian(1, 3);
        board.addPedestrian(1, 2);
        board.addWall(2, 3);
        //System.out.println(board.getCell(0,5).getAvailable());
        Panel panel = new Panel();
        panel.addBoard(board);
        JButton button = new JButton("Step");
        panel.add(button);
        Screen screen = new Screen(panel);

        button.addActionListener(e -> {
            try {
                board.step();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            panel.repaint();
        });


    }
}