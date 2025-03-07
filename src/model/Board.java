package model;

import kotlin.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private ArrayList<ArrayList<Cell>> board;
    private ArrayList<ArrayList<Cell>> tmp_board = new ArrayList<>();
    //cell from_x, from_y to_x to_y
    public Map<Pair<Integer, Integer>, ArrayList<Pair<Cell, Pair<Integer, Integer>>>> wish_list = new HashMap<>();
    //private ArrayList<Pair<Cell, Pair<Integer, Integer>>> wish_list = new ArrayList<>();

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

    public void removePedestrian(int row, int col) {
        board.get(row).set(col, new Cell());
        tmp_board.get(row).set(col, new Cell());
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

    public void cleanCell(int row, int col) {
        board.get(row).get(col).setState(State.EMPTY);
        tmp_board.get(row).get(col).setState(State.EMPTY);
    }
    public void pedestrianStep(int row, int col) {
        //printBoard();
        //System.out.println(row + " " + col);
        PedestrianCell tmp_pedestrian = (PedestrianCell) tmp_board.get(row).get(col);
        Cell up = tmp_board.get(row - 1).get(col);
        Cell down = tmp_board.get(row + 1).get(col);
        Cell left = tmp_board.get(row).get(col - 1);
        Cell right = tmp_board.get(row).get(col + 1);

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

        if (up.getAvailable() && //!up.getIsPedestrian() &&
                up_value == min) min_list.add(new Pair<>(row - 1, col));
        if (down.getAvailable() &&//!down.getIsPedestrian() &&
                down_value == min) min_list.add(new Pair<>(row + 1, col));
        if (left.getAvailable() && //!left.getIsPedestrian() &&
                left_value == min) min_list.add(new Pair<>(row, col - 1));
        if (right.getAvailable() &&//!right.getIsPedestrian() &&
                right_value == min) min_list.add(new Pair<>(row, col + 1));

        if(min_list.isEmpty()) return;

        int next = (int) ((Math.random()*100) % min_list.size());
        int next_row = min_list.get(next).getFirst();
        int next_col = min_list.get(next).getSecond();

        //System.out.println(next_row + " " + next_col + " " +up_value + " " + down_value + " " + left_value + " " + right_value);

        Pair<Cell, Pair<Integer, Integer>> pedestrian_wish =
                new Pair<>(tmp_pedestrian, new Pair<>(row, col));

        Pair key_next = new Pair(next_row, next_col);
        if (wish_list.containsKey(key_next)) {
            ArrayList tmp = new ArrayList();
            tmp.addAll(wish_list.get(key_next));
            tmp.add(pedestrian_wish);
            wish_list.replace(key_next, tmp);
        } else {
            ArrayList tmp = new ArrayList();
            tmp.add(pedestrian_wish);
            wish_list.put(key_next, tmp);
        }

        //wish_list.add(pedestrian_wish);

//         Cell newCell = tmp_board.get(next_row).get(next_col);
//        tmp_board.get(next_row).set(next_col, tmp_pedestrian);
//        tmp_board.get(row).set(col, newCell);

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
        wish_list.clear();
        for (int i = 1; i < this.getAmountOfRows() - 1; i++) {
            for (int j = 1; j < this.getAmountOfCols() - 1; j++) {
                //System.out.print(this.getCell(i,j).getAvailable()+" ");
                if (this.getCell(i, j).getIsPedestrian()) {
                    pedestrianStep(i, j);
                }
            }
            //System.out.println();
        }
        //System.out.println(wish_list);
        wish_list.forEach((key, value) -> {
            int next_row = key.getFirst();
            int next_col = key.getSecond();

            if (value.size() == 1) {
                int row = value.get(0).getSecond().getFirst();
                int col = value.get(0).getSecond().getSecond();
                Cell newCell = tmp_board.get(next_row).get(next_col);
                tmp_board.get(next_row).set(next_col, value.get(0).getFirst());
                tmp_board.get(row).set(col, newCell);
                //System.out.println("swap");
            }else{
                System.out.println("conflict");
                int next = (int) ((Math.random()*100) % value.size());
                int row = value.get(next).getSecond().getFirst();
                int col = value.get(next).getSecond().getSecond();
                Cell newCell = tmp_board.get(next_row).get(next_col);
                tmp_board.get(next_row).set(next_col, value.get(next).getFirst());
                tmp_board.get(row).set(col, newCell);
            }

        });

        //System.out.println("step");

        //System.out.println(board);
        //System.out.println(tmp_board);
        updateBoard();
        //printBoard();
    }

    void updateBoard() {
        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                board.get(i).set(j, tmp_board.get(i).get(j));
                if(board.get(i).get(j).achievedGoal(i,j)){
                    this.removePedestrian(i,j);
                }
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
