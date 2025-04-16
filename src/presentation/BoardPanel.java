package presentation;

import model.ca.Board;
import utils.Constants;
import utils.DrawCell;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends ViewModelPanel {
    private Board board;
    private DrawCell drawCell = new DrawCell();
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //System.out.println("paint");
        //board.printBoard();
        if(this.getViewModel().checkBoard()){
            for (int i = 0; i < board.getAmountOfRows(); i++) {
                for (int j = 0; j < board.getAmountOfCols(); j++) {
                    drawCell.draw(g,board.getCell(i,j).getState(), j* Constants.SIZE_OF_CELL, i* Constants.SIZE_OF_CELL);
//                    switch (board.getCell(i,j).getState()){
//                        case EMPTY:
//                            g.drawRect(j * 10, i * 10, 10, 10);
//                            break;
//                        case EXIT:
//                            g.setColor(Color.RED);
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            g.setColor(Color.BLACK);
//                            break;
//                        case ENTRY:
//                            g.setColor(Color.BLUE);
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            g.setColor(Color.BLACK);
//                            break;
//                        case PEDESTRIAN:
//                            g.setColor(Color.GREEN);
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            g.setColor(Color.BLACK);
//                            break;
//                        case OBSTRUCTION:
//                            g.fillRect(j * 10, i * 10, 10, 10);
//                            break;
//
//                    }


                    //  System.out.print(i + " "+j+"; ");
                }
                // System.out.println();
            }
        }


    }

    public BoardPanel() {

    }


    public void addBoard(Board board) {
        this.board = board;
    }

}
