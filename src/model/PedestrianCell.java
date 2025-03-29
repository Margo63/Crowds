package model;

import kotlin.Pair;

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

    public void initGoalMap(ArrayList<ArrayList<Integer>> goalMap) {
        this.goalMap = goalMap;
    }

    public void loadGoalMap(int pedestrianX, int pedestrianY) {
        ArrayList<Pair<Integer,Integer>> current_wave = new ArrayList<>();
        ArrayList<Pair<Integer,Integer>> next_wave = new ArrayList<>();
        Boolean isGoalReached = false;
        current_wave.add(new Pair<>(rowGoal,colGoal));
        goalMap.get(rowGoal).set(colGoal,0);
        int height = goalMap.size();
        if(height == 0) return;
        int width = goalMap.getFirst().size();
        int num_wave = 1;

        while (!isGoalReached) {
            for (Pair<Integer,Integer> p : current_wave) {
                int cur_x = p.getFirst();
                int cur_y = p.getSecond();
                if(cur_x - 1 >= 0 && State.getFromInt(goalMap.get(cur_x-1).get(cur_y)) == State.EMPTY){
                    next_wave.add(new Pair<>(cur_x-1,cur_y));
                    goalMap.get(cur_x-1).set(cur_y,num_wave);
                }
                if(cur_x+1<height && State.getFromInt(goalMap.get(cur_x-1).get(cur_y)) == State.EMPTY){
                    next_wave.add(new Pair<>(cur_x+1,cur_y));
                    goalMap.get(cur_x+1).set(cur_y,num_wave);
                }

            }
            num_wave++;
        }
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
