package presentation;

import model.Board;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Board board;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //board.printBoard();
        for (int i = 0; i < board.getAmountOfRows(); i++) {
            for (int j = 0; j < board.getAmountOfCols(); j++) {

                if (board.getCell(i, j).getAvailable())
                    g.drawRect(j * 10, i * 10, 10, 10);
                else if (board.getCell(i, j).getIsCellGoal()) {
                    g.setColor(Color.RED);
                    g.fillRect(j * 10, i * 10, 10, 10);
                    g.setColor(Color.BLACK);
                } else if (board.getCell(i, j).getIsPedestrian()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(j * 10, i * 10, 10, 10);
                    g.setColor(Color.BLACK);
                }else
                    g.fillRect(j * 10, i * 10, 10, 10);
                //System.out.print(i + " "+j+"; ");
            }
            //System.out.println();
        }

    }

    public void addBoard(Board board) {
        this.board = board;
    }

}
