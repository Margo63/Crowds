package presentation;

import model.State;
import model.ca.Cell;
import presentation.input.DrawRect;

import java.util.ArrayList;

public class ViewModel {
    private ArrayList<ArrayList<DrawRect>> board;
    private ArrayList<ArrayList<Integer>> zones;

    public void setBoard(ArrayList<ArrayList<DrawRect>> board) {

        this.board = board;
        loadZone();
    }

    public void loadBoardFromInteger(ArrayList<ArrayList<Integer>> board) {



        ArrayList<ArrayList<DrawRect>> tmp = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            tmp.add(new ArrayList<>(board.size()));
            for (int j = 0; j < board.get(i).size(); j++) {
                DrawRect rect = new DrawRect(j, i);
                rect.changeState(State.getFromInt(board.get(i).get(j)));
                tmp.get(i).add(rect);
            }
        }
        this.board = tmp;

        loadZone();
    }

    public ArrayList<ArrayList<DrawRect>> getBoard() {
        return board;
    }

    public ArrayList<ArrayList<Integer>> getZones() {
        return zones;
    }

    public void setZones(ArrayList<ArrayList<Integer>> zones) {
        this.zones = zones;
    }

    public boolean checkBoard() {
        if (board == null) {
            return false;
        }
        return !board.isEmpty();
    }

    private void loadZone(){
        if(this.zones==null) {
            this.zones = new ArrayList<>();
            for(int i = 0; i < board.size(); i++) {
                this.zones.add(new ArrayList<>(board.size()));
                for(int j = 0; j < board.get(i).size(); j++) {
                    this.zones.get(i).add(0);
                }
            }
        }
        System.out.println("load:"+zones);
    }
}
