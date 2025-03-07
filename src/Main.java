import analyze.Analyze;
import model.Board;
import presentation.Panel;
import presentation.Screen;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(11);
        Analyze analyze = new Analyze();

        board.addPedestrian(2, 1);
        board.addPedestrian(2, 3);
        board.addPedestrian(1, 2);

        for (int i = 0; i < 11; i++) {
            board.addWall(0, i);
            board.addWall(10, i);
            board.addWall(i, 0);
            board.addWall(i, 10);
        }
        board.addWall(4, 4);
        board.addWall(4, 5);

        board.addWall(5, 9);
        board.addWall(5, 8);

        board.addWall(8, 2);
        board.addWall(8, 3);
        board.addWall(7, 3);

        board.cleanCell(10,5);

        //System.out.println(board.getCell(0,5).getAvailable());
        Panel panel = new Panel();

        panel.addBoard(board);
        JButton button = new JButton("Step");
        JLabel label = new JLabel();
        panel.add(button);
        panel.add(label);
        Screen screen = new Screen(panel);


        button.addActionListener(e -> {
            try {
                board.step();
                analyze.analyze_board(board);
                label.setText("количество конфликтов: " + analyze.getConflict());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            panel.repaint();
        });


    }
}