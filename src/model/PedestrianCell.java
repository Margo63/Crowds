package model;

import java.util.ArrayList;

public class PedestrianCell extends Cell {

    private int rowGoal=10, colGoal=5;
    private ArrayList<ArrayList<Integer>> goalMap;

    @Override
    public boolean getIsPedestrian() {
        return true;
    }
    @Override
    public State getState() {
        return State.PEDESTRIAN;
    }

    @Override
    public boolean getAvailable() {
        return false;
    }

    public int getProximityToExit(int x, int y) {
        return goalMap.get(x).get(y);
    }


    public void loadGoalMap(Board board){

    }

    @Override
    public String printCell(){
        return "5";
    }
    @Override
    public boolean achievedGoal(int row, int col) {
        return row == rowGoal && col == colGoal;
    }
}
