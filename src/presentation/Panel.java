package presentation;

import model.Board;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Board board;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < board.getAmountOfRows(); i++) {
            for (int j = 0; j < board.getAmountOfCols(); j++) {

                if (!board.getCell(i, j).getAvailable())
                    g.fillRect(j * 10, i * 10, 10, 10);
                else if (board.getCell(i, j).getGoal()) {
                    g.setColor(Color.RED);
                    g.fillRect(j * 10, i * 10, 10, 10);
                    g.setColor(Color.BLACK);
                } else
                    g.drawRect(j * 10, i * 10, 10, 10);
                //System.out.print(i + " "+j+"; ");
            }
            //System.out.println();
        }

    }

    public void addBoard(Board board) {
        this.board = board;
    }

}
