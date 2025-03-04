package model;

import kotlin.Pair;

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

    public void addPedestrian(int row, int col) {
        board.get(row).set(col, new PedestrianCell());
        tmp_board.get(row).set(col, new PedestrianCell());

    }

    public void addWall(int row, int col) {
        board.get(row).get(col).setState(State.OBSTRUCTION);
        tmp_board.get(row).get(col).setState(State.OBSTRUCTION);
    }
//    public void movePedestrian(int row, int col) {
//        //System.out.println(row + " " + col);
//        //tmp_board.get(row).get(col).setState(State.PEDESTRIAN);
//        tmp_board.get(row).set(col, new PedestrianCell());
//    }
//
//    public void removePedestrian(int row, int col) {
//        //System.out.println(row + " " + col);
//        tmp_board.get(row).set(col, new PedestrianCell());
//    }

    public void pedestrianStep(int row, int col) {
        //printBoard();
        //System.out.println(row + " " + col);
        PedestrianCell tmp_pedestrian = (PedestrianCell) tmp_board.get(row).get(col);


        int up_value = tmp_pedestrian.getProximityToExit(row - 1, col);
        int down_value = tmp_pedestrian.getProximityToExit(row + 1, col);
        int left_value = tmp_pedestrian.getProximityToExit(row, col - 1);
        int right_value = tmp_pedestrian.getProximityToExit(row, col + 1);

        int[] arr = {up_value, down_value, left_value, right_value};

        ArrayList<Pair<Integer, Integer>> min_list = new ArrayList<>();
        int min = 100000000;
        for (int i = 0; i < 4; i++) {
            if (arr[i] >= 0 && min > arr[i]) min = arr[i];
        }

        if (up_value == min) min_list.add(new Pair<>(row - 1, col));
        if (down_value == min) min_list.add(new Pair<>(row + 1, col));
        if (left_value == min) min_list.add(new Pair<>(row, col - 1));
        if (right_value == min) min_list.add(new Pair<>(row, col + 1));

        int next = (int) (Math.random() % min_list.size());
        int next_row = min_list.get(next).getFirst();
        int next_col = min_list.get(next).getSecond();

        //System.out.println(next_row + " " + next_col);

        Cell newCell = tmp_board.get(next_row).get(next_col);
        tmp_board.get(next_row).set(next_col, tmp_pedestrian);
        tmp_board.get(row).set(col, newCell);

        //printBoard();

    }

    public void setCellGoal(int row, int col) {
        //System.out.println(row + " " + col);
        board.get(row).get(col).setGoal();
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
        //Thread.sleep(1000);
        for (int i = 1; i < this.getAmountOfRows() - 1; i++) {
            for (int j = 1; j < this.getAmountOfCols() - 1; j++) {
                //System.out.print(this.getCell(i,j).getAvailable()+" ");
                if (this.getCell(i, j).getIsPedestrian()) {
                    pedestrianStep(i, j);
                }
            }
            //System.out.println();
        }
        //System.out.println("step");

        //System.out.println(board);
        //System.out.println(tmp_board);
        updateBoard();
        //printBoard();
    }

    void updateBoard() {
        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                board.get(i).set(j,tmp_board.get(i).get(j));
            }

        }
    }

    public void printBoard() {

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                System.out.print(this.getCell(i, j).printCell() + " ");
            }
            System.out.println();
        }
        System.out.println("tmp");

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {

                System.out.print(tmp_board.get(i).get(j).printCell() + " ");

            }
            System.out.println();
        }
    }


}
