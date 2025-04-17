package presentation;

import model.State;
import model.ca.Cell;
import presentation.input.DrawRect;
import utils.Constants;

import java.util.ArrayList;

public class ViewModel {
    private ArrayList<ArrayList<DrawRect>> board;
    private ArrayList<ArrayList<Integer>> zones;
    private String[] pages = {Constants.INPUT_FILE, Constants.INPUT_DRAW, Constants.INPUT_ZONE,Constants.INPUT_PEDESTRIAN, Constants.BOARD};
    private int ind_page = 0;
    private Screen screen;

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

    public ArrayList<ArrayList<Integer>> getBoardInteger() {
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            tmp.add(new ArrayList<>(board.size()));
            for (int j = 0; j < board.get(i).size(); j++) {
                tmp.get(i).add(board.get(i).get(j).getState().getValue());
            }
        }
        return tmp;
    }

    //to navigation inside panel
    public void addScreen(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
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

    private void loadZone() {
        if (this.zones == null || this.zones.size() != board.size()) {
            this.zones = new ArrayList<>();
            for (int i = 0; i < board.size(); i++) {
                this.zones.add(new ArrayList<>(board.size()));
                for (int j = 0; j < board.get(i).size(); j++) {
                    this.zones.get(i).add(0);
                }
            }
        }
        //System.out.println("load:"+zones);
    }

    public void next() {
        if (ind_page < pages.length - 1) {
            ind_page++;
            screen.changePanel(pages[ind_page]);
            //System.out.println("clicked on next: " + ind_page);
        }
    }

    public void previous() {
        if (ind_page > 0) {
            ind_page--;
            screen.changePanel(pages[ind_page]);
        }
    }

    public boolean checkNextButton() {
        //System.out.println("check next: " + ind_page);
        if (ind_page == pages.length - 1)
            return false;
        return true;
    }

    public boolean checkPreviousButton() {
        //System.out.println("check prev: " + ind_page);
        if (ind_page == 0 || ind_page == pages.length - 1)
            return false;
        return true;
    }
}
