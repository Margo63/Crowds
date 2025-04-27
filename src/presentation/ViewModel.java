package presentation;

import data.State;
import presentation.models.InputCell;
import presentation.models.PedestrianInput;
import utils.ConstantUtil;

import java.util.ArrayList;

public class ViewModel {
    private ArrayList<ArrayList<InputCell>> board;
    private ArrayList<ArrayList<Integer>> zones;
    private final String[] pages = {ConstantUtil.INPUT_FILE, ConstantUtil.INPUT_DRAW, ConstantUtil.INPUT_ZONE, ConstantUtil.INPUT_PEDESTRIAN, ConstantUtil.BOARD};
    private int pageIndex = 0;
    private Screen screen;
    private ArrayList<PedestrianInput> pedestrianInputs;

    ViewModel(){
        board = new ArrayList<>();
        zones = new ArrayList<>();
    }

    public void setBoard(ArrayList<ArrayList<InputCell>> board) {
        if(board == null) return;
        this.board = board;
        loadZone();
    }

    public void setBoardFromInteger(ArrayList<ArrayList<Integer>> board) {
        if(board == null) return;
        ArrayList<ArrayList<InputCell>> tmp = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            tmp.add(new ArrayList<>(board.size()));
            for (int j = 0; j < board.get(i).size(); j++) {
                InputCell rect = new InputCell(j, i);
                rect.setState(State.getFromInt(board.get(i).get(j)));
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
    public void setScreen(Screen screen) {
        this.screen = screen;
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

    public ArrayList<PedestrianInput> getPedestrianInputs() {
        return pedestrianInputs;
    }

    public void setPedestrianInputs(ArrayList<PedestrianInput> pedestrianInputs) {
        this.pedestrianInputs = pedestrianInputs;
    }

    //init zones on panel
    private void loadZone() {
        if (this.zones == null || this.zones.size() != board.size()) {
            this.zones = new ArrayList<>();
            for (int i = 0; i < board.size(); i++) {
                this.zones.add(new ArrayList<>(board.size()));
                for (int j = 0; j < board.get(i).size(); j++) {
                    if (board.get(i).get(j).getState() == State.EMPTY)
                        this.zones.get(i).add(0);
                    else
                        this.zones.get(i).add(-1);
                }
            }
        }
        //System.out.println("load:"+zones);
    }

    public void loadNextPage() {
        if (pageIndex < pages.length - 1) {
            pageIndex++;
            screen.changePanel(pages[pageIndex]);
            //System.out.println("clicked on next: " + ind_page);
        }
    }

    public void loadPreviousPage() {
        if (pageIndex > 0) {
            pageIndex--;
            screen.changePanel(pages[pageIndex]);
        }
    }

    public boolean checkNextPage() {
        return !(pageIndex == pages.length - 1);
    }

    public boolean checkPreviousPage() {
        return !(pageIndex == 0 || pageIndex == pages.length - 1);
    }
}
