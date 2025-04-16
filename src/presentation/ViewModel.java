package presentation;

import java.util.ArrayList;

public class ViewModel {
    private ArrayList<ArrayList<Integer>> board;


    public void setBoard(ArrayList<ArrayList<Integer>> board){
        this.board = board;
    }

    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    public boolean checkBoard(){
        if(board == null){
            return false;
        }
        return !board.isEmpty();
    }
}
