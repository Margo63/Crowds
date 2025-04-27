package model.ca;

import data.MapPoint;
import kotlin.Pair;
import model.Model;
import data.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static data.State.EMPTY;
import static data.State.EXIT;

public class Board extends Model {

    private ArrayList<ArrayList<Integer>> startMap;
    private ArrayList<ArrayList<Cell>> board;
    private ArrayList<ArrayList<Cell>> tmpBoard = new ArrayList<>();
    private int count = 1;
    //cell from_x, from_y to_x to_y
    private Map<MapPoint, ArrayList<Pair<Cell, MapPoint>>> pedestriansWishList = new HashMap<>();



    public Board() {
        super();
        board = new ArrayList<>();
    }

    public void loadMap(ArrayList<ArrayList<Integer>> startMap) {
        this.startMap = startMap;
        //board = new Cell[size][size];
        int startAmountRows = startMap.size();
        if (startAmountRows == 0) return;
        int startAmountCols = startMap.getFirst().size();

        //init map from input
        for (int i = 0; i < startAmountRows; i++) {
            board.add(new ArrayList<Cell>());
            tmpBoard.add(new ArrayList<Cell>());
            for (int j = 0; j < startAmountCols; j++) {
                Cell cell = new Cell();
                cell.setState(State.getFromInt(startMap.get(i).get(j)));
                board.get(i).add(cell);
                tmpBoard.get(i).add(cell);
            }
        }

        notifyObserversAboutSize(board.size(), board.getFirst().size());

        //add frame
        board.addFirst(new ArrayList<Cell>());
        tmpBoard.addFirst(new ArrayList<Cell>());
        this.startMap.addFirst(new ArrayList<Integer>());

        board.addLast(new ArrayList<Cell>());
        tmpBoard.addLast(new ArrayList<Cell>());
        this.startMap.addLast(new ArrayList<Integer>());

        for (int j = 0; j < startAmountCols; j++) {
            Cell cell = new Cell();
            cell.setState(State.OBSTRUCTION);
            board.getLast().add(cell);
            tmpBoard.getLast().add(cell);
            this.startMap.getLast().add(State.OBSTRUCTION.getValue());

            board.getFirst().add(cell);
            tmpBoard.getFirst().add(cell);
            this.startMap.getFirst().add(State.OBSTRUCTION.getValue());
        }

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            Cell cell = new Cell();
            cell.setState(State.OBSTRUCTION);
            board.get(i).addFirst(cell);
            tmpBoard.get(i).addFirst(cell);
            this.startMap.get(i).addFirst(State.OBSTRUCTION.getValue());

            board.get(i).addLast(cell);
            tmpBoard.get(i).addLast(cell);
            this.startMap.get(i).addLast(State.OBSTRUCTION.getValue());
        }

//        for (int i = 0; i < this.startMap.size(); i++) {
//            for (int j = 0; j < this.startMap.get(i).size(); j++) {
//                System.out.print(this.startMap.get(i).get(j)+"\t");
//            }
//            System.out.println();
//        }
//        printBoard();
    }

    public boolean addPedestrian(Point entry, ArrayList<MapPoint> way, Point exit) {
        MapPoint mapPointEntry = new MapPoint(entry.y + 1, entry.x + 1);
        MapPoint mapPointExit = new MapPoint(exit.y + 1, exit.x + 1);

        PedestrianCell pedestrianCell = new PedestrianCell(count);
        if(count%2==0) pedestrianCell.setAgressor(true);


        int addRow, addColumn;
        //check up
        if (mapPointEntry.row() - 1 >= 0 && board.get(mapPointEntry.row() - 1).get(mapPointEntry.column()).getAvailable()) {
            addRow = mapPointEntry.row() - 1;
            addColumn = mapPointEntry.column();
        }
        //check down
        else if (mapPointEntry.row() + 1 < board.size() && board.get(mapPointEntry.row() + 1).get(mapPointEntry.column()).getAvailable()) {
            addRow = mapPointEntry.row() + 1;
            addColumn = mapPointEntry.column();
        }

        //check left
        else if (mapPointEntry.column() - 1 >= 0 && board.get(mapPointEntry.row()).get(mapPointEntry.column() - 1).getAvailable()) {
            addRow = mapPointEntry.row();
            addColumn = mapPointEntry.column() - 1;
        }

        //check right
        else if (mapPointEntry.column() + 1 < board.getFirst().size() && board.get(mapPointEntry.row()).get(mapPointEntry.column() + 1).getAvailable()) {
            addRow = mapPointEntry.row();
            addColumn = mapPointEntry.column() + 1;
        } else {
            return false;
        }
        pedestrianCell.initGoalMap(startMap);
        pedestrianCell.setGoalList(way);
        pedestrianCell.setExitGoal(mapPointExit.row(), mapPointExit.column());
        pedestrianCell.loadGoalMap();


        board.get(addRow).set(addColumn, pedestrianCell);
        tmpBoard.get(addRow).set(addColumn, pedestrianCell);
        notifyObserversAboutNewPedestrianOnBoard();
        count++;
        return true;
    }

    private void removePedestrian(int row, int col) {
        notifyObserversAboutRemovePedestrianOffBoard();
        notifyObserversAboutPedestrianWay(((PedestrianCell) board.get(row).get(col)).getWay());
        // get path that pedestrian walk
        board.get(row).set(col, new Cell(EXIT));
        tmpBoard.get(row).set(col, new Cell(EXIT));
    }


    public void cleanCell(int row, int col) {
        board.get(row).get(col).setState(EMPTY);
        tmpBoard.get(row).get(col).setState(EMPTY);
    }

    // load wish list of pedestrian steps
    public void pedestrianStep(int row, int col) {
        //printBoard();
        //System.out.println(row + " " + col);
        PedestrianCell tmpPedestrian = (PedestrianCell) tmpBoard.get(row).get(col);
        Cell up = tmpBoard.get(row - 1).get(col);
        Cell down = tmpBoard.get(row + 1).get(col);
        Cell left = tmpBoard.get(row).get(col - 1);
        Cell right = tmpBoard.get(row).get(col + 1);

        int up_value = tmpPedestrian.getProximityToGoal(row - 1, col);
        int down_value = tmpPedestrian.getProximityToGoal(row + 1, col);
        int left_value = tmpPedestrian.getProximityToGoal(row, col - 1);
        int right_value = tmpPedestrian.getProximityToGoal(row, col + 1);

        ArrayList<Integer> arr = new ArrayList<>();

        if (up.getAvailable()) {
            arr.add(up_value);
        }

        if (down.getAvailable()) {
            arr.add(down_value);
        }
        if (left.getAvailable()) {
            arr.add(left_value);
        }
        if (right.getAvailable()) {
            arr.add(right_value);
        }


        ArrayList<MapPoint> list_to_go = new ArrayList<>();

        //TODO set min as current pedestrian proximity to exit???
        int min = 100000000;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) >= 0 && min > arr.get(i)) min = arr.get(i);
        }

        //check not to go back
        if (min > tmpPedestrian.getProximityToGoal(row, col)) return;

        if (up.getAvailable() && up_value == min)
            list_to_go.add(new MapPoint(row - 1, col));

        if (down.getAvailable() && down_value == min)
            list_to_go.add(new MapPoint(row + 1, col));

        if (left.getAvailable() && left_value == min)
            list_to_go.add(new MapPoint(row, col - 1));

        if (right.getAvailable() && right_value == min)
            list_to_go.add(new MapPoint(row, col + 1));


        if (list_to_go.isEmpty()) return;

        int next = (int) ((Math.random() * 100) % list_to_go.size());
        int next_row = list_to_go.get(next).row();
        int next_col = list_to_go.get(next).column();

        //System.out.println(next_row + " " + next_col + " " +up_value + " " + down_value + " " + left_value + " " + right_value);

        //пешеход и куда он хочет пойти
        Pair<Cell, MapPoint> pedestrian_wish =
                new Pair<>(tmpPedestrian, new MapPoint(row, col));


        MapPoint key_next = new MapPoint(next_row, next_col);
        if (pedestriansWishList.containsKey(key_next)) {
            ArrayList tmp = new ArrayList();
            tmp.addAll(pedestriansWishList.get(key_next));
            tmp.add(pedestrian_wish);
            pedestriansWishList.replace(key_next, tmp);
        } else {
            ArrayList tmp = new ArrayList();
            tmp.add(pedestrian_wish);
            pedestriansWishList.put(key_next, tmp);
        }

        //wish_list.add(pedestrian_wish);

//         Cell newCell = tmp_board.get(next_row).get(next_col);
//        tmp_board.get(next_row).set(next_col, tmp_pedestrian);
//        tmp_board.get(row).set(col, newCell);

        //printBoard();

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
        pedestriansWishList.clear();
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
        pedestriansWishList.forEach((key, value) -> {
            int next_row = key.row();
            int next_col = key.column();

            //no conflict
            if (value.size() == 1) {
                int row = value.get(0).getSecond().row();
                int col = value.get(0).getSecond().column();
                Cell newCell = new Cell();//tmp_board.get(next_row).get(next_col);
                tmpBoard.get(next_row).set(next_col, value.get(0).getFirst());
                tmpBoard.get(row).set(col, newCell);
                //System.out.println("swap");
            } else {
                //System.out.println("conflict");


                int next = (int) ((Math.random() * 100) % value.size());
                int row = value.get(next).getSecond().row();
                int col = value.get(next).getSecond().column();
                Cell newCell = tmpBoard.get(next_row).get(next_col);
                tmpBoard.get(next_row).set(next_col, value.get(next).getFirst());
                tmpBoard.get(row).set(col, newCell);
                notifyObserversAboutConflict(row, col);
            }

        });

        //System.out.println("step");

        //System.out.println(board);
        //System.out.println(tmp_board);
        updateBoard();
        //printBoard();
    }

    public void setPanicMode(){
        ArrayList<MapPoint> exits = new ArrayList<>();
        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                if (this.getCell(i, j).getState() == EXIT) {
                    exits.add(new MapPoint(i,j));
                }
            }
        }

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                if (this.getCell(i, j).getIsPedestrian()) {
                    ((PedestrianCell)this.getCell(i, j)).loadPanicGoalMap(exits);
                }
            }
        }

    }

    private void updateBoard() {
        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                board.get(i).set(j, tmpBoard.get(i).get(j));
                if (board.get(i).get(j).isExitAchieved(i, j)) {
                    this.removePedestrian(i, j);
                }
            }

        }
    }

    public ArrayList<ArrayList<Integer>> getBoardOfIntegers() {
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<>();
        for (int i = 1; i < this.getAmountOfRows() - 1; i++) {
            tmp.add(new ArrayList<>());
            for (int j = 1; j < this.getAmountOfCols() - 1; j++) {
                Cell cell = this.getCell(i, j);
                tmp.get(i - 1).add(cell.getState().getValue());
            }
        }
        return tmp;
    }

    private void printBoard() {

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {
                System.out.print(this.getCell(i, j).getState() + " ");
            }
            System.out.println();
        }
        System.out.println("tmp");

        for (int i = 0; i < this.getAmountOfRows(); i++) {
            for (int j = 0; j < this.getAmountOfCols(); j++) {

                System.out.print(tmpBoard.get(i).get(j).getState() + " ");

            }
            System.out.println();
        }
    }

}
