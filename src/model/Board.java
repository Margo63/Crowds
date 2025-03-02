package model;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<Cell>> board;
    private ArrayList<ArrayList<Cell>> tmp_board = new ArrayList<>();

    public Board(int size) {
        //board = new Cell[size][size];
        board = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<Cell>(size));
            tmp_board.add(new ArrayList<Cell>(size));
            for (int j = 0; j < size; j++) {
                board.get(i).add(new Cell());
                tmp_board.get(i).add(new Cell());
            }
        }

    }

    public void setCellNotAvaible(int row, int col) {
        //System.out.println(row + " " + col);
        board.get(row).get(col).setAvailable(false);

    }

    public void setCellAvaible(int row, int col) {
        //System.out.println(row + " " + col);
        board.get(row).get(col).setAvailable(true);
    }

    public void setCellGoal(int row, int col) {
        //System.out.println(row + " " + col);
        board.get(row).get(col).setGoal(true);
    }

    public int getAmountOfRows() {
        return board.size();
    }

    public int getAmountOfCols() {
        return board.getFirst().size();
    }

    public Cell getCell(int row, int col) {
        return board.get(row).get(col);
    }

    public void step() throws InterruptedException {
        Thread.sleep(1000);
        for (int i = 1; i < this.getAmountOfRows() - 1; i++) {
            for (int j = 1; j < this.getAmountOfCols() - 1; j++) {
                //System.out.print(this.getCell(i,j).getAvailable()+" ");
                if (!this.getCell(i, j).getAvailable()) {
                    System.out.println("not available");
                    if (this.getCell(i + 1, j).getAvailable()) {
                        tmp_board.get(i+1).get(j).setAvailable(false);
                        tmp_board.get(i).get(j).setAvailable(true);
                        //this.setCellNotAvaible(i + 1, j);
                        //this.setCellAvaible(i, j);
                        System.out.println("change tmp:"+tmp_board.get(i+1).get(j).getAvailable());
                        System.out.println("change tmp:"+tmp_board.get(i).get(j).getAvailable());
                        System.out.println("change:"+board.get(i+1).get(j).getAvailable());
                        System.out.println("change:"+board.get(i).get(j).getAvailable());
                    }
                }
            }
            //System.out.println();
        }
        System.out.println("step");
        //printBoard();
        //System.out.println(board);
        //System.out.println(tmp_board);
        updateBoard();
    }
    void updateBoard(){
        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                board.get(i).get(j).setAvailable(tmp_board.get(i).get(j).getAvailable());
            }

        }
    }
    void printBoard() {

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                if(this.getCell(i,j).getAvailable())
                    System.out.print(1+" ");
                else System.out.print(0+" ");
            }
            System.out.println();
        }
        System.out.println("tmp");

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                if(tmp_board.get(i).get(j).getAvailable())
                    System.out.print(1+" ");
                else System.out.print(0+" ");
            }
            System.out.println();
        }
    }


}
