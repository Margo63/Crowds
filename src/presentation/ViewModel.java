package presentation;

import model.State;
import presentation.models.InputCell;
import presentation.models.PedestrianInput;
import utils.Constants;

import java.util.ArrayList;

public class ViewModel {
    private ArrayList<ArrayList<InputCell>> board;
    private ArrayList<ArrayList<Integer>> zones;
    private String[] pages = {Constants.INPUT_FILE, Constants.INPUT_DRAW, Constants.INPUT_ZONE,Constants.INPUT_PEDESTRIAN, Constants.BOARD};
    private int ind_page = 0;
    private Screen screen;
    private ArrayList<PedestrianInput> pedestrians;

    public void setBoard(ArrayList<ArrayList<InputCell>> board) {

        this.board = board;
        loadZone();
    }

    public void loadBoardFromInteger(ArrayList<ArrayList<Integer>> board) {

        ArrayList<ArrayList<InputCell>> tmp = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            tmp.add(new ArrayList<>(board.size()));
            for (int j = 0; j < board.get(i).size(); j++) {
                InputCell rect = new InputCell(j, i);
                rect.changeState(State.getFromInt(board.get(i).get(j)));
                tmp.get(i).add(rect);
            }
        }
        this.board = tmp;

        loadZone();
    }

    public ArrayList<ArrayList<InputCell>> getBoard() {
        return board;
    }

    public ArrayList<ArrayList<Integer>> getBoardInteger() {
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            tmp.add(new ArrayList<>(board.size()));
            for (int j = 0; j < board.get(i).size(); j++) {
                //System.out.print(board.get(i).get(j).getState()+" :"+ board.get(i).get(j).getState().getValue() +"; ");
                tmp.get(i).add(board.get(i).get(j).getState().getValue());
            }
            //System.out.println();
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

    public ArrayList<PedestrianInput> getPedestrians() {
        return pedestrians;
    }

    public void setPedestrians(ArrayList<PedestrianInput> pedestrians) {
        this.pedestrians = pedestrians;
    }

    //init zones on panel
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
