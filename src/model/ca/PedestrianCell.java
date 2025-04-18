package model.ca;

import kotlin.Pair;
import model.State;

import java.util.ArrayList;

import static model.State.EMPTY;

public class PedestrianCell extends Cell {

    private ArrayList<Pair<Integer, Integer>> goalList;
    private ArrayList<ArrayList<Integer>> goalMap = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> startMap;

    PedestrianCell(){}
    PedestrianCell(State state) {
        super(state);
    }

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

    public void initGoalMap(ArrayList<ArrayList<Integer>> startMap) {
        this.startMap = new ArrayList<>(startMap);
        //this.goalMap = new ArrayList<>(goalMap);
    }

    public void loadGoalMap() {
        if (goalMap != null) goalMap.clear();
        for (int i = 0; i < startMap.size(); i++) {
            goalMap.add(new ArrayList<>(startMap.size()));
            for (int j = 0; j < startMap.getFirst().size(); j++) {
                this.goalMap.get(i).add(startMap.get(i).get(j));
            }
        }

        ArrayList<Pair<Integer, Integer>> current_wave = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> next_wave = new ArrayList<>();

        current_wave.add(this.goalList.getFirst());

        int height = goalMap.size();
        if (height == 0) return;
        int width = goalMap.getFirst().size();
        int num_wave = 1;
        //printMap();

        while (current_wave.size() != 0) {
            for (Pair<Integer, Integer> p : current_wave) {
                int cur_x = p.getFirst();
                int cur_y = p.getSecond();

                if (cur_x - 1 >= 0 && State.getFromInt(goalMap.get(cur_x - 1).get(cur_y)) == EMPTY) {
                    next_wave.add(new Pair<>(cur_x - 1, cur_y));
                    goalMap.get(cur_x - 1).set(cur_y, num_wave);
                }

                if (cur_x + 1 < height && State.getFromInt(goalMap.get(cur_x + 1).get(cur_y)) == EMPTY) {
                    //System.out.println("down: x= "+cur_x+" y= "+(cur_y+1));
                    next_wave.add(new Pair<>(cur_x + 1, cur_y));
                    goalMap.get(cur_x + 1).set(cur_y, num_wave);
                }
                if (cur_y - 1 >= 0 && State.getFromInt(goalMap.get(cur_x).get(cur_y - 1)) == EMPTY) {
                    next_wave.add(new Pair<>(cur_x, cur_y - 1));
                    goalMap.get(cur_x).set(cur_y - 1, num_wave);
                }
                if (cur_y + 1 < width && State.getFromInt(goalMap.get(cur_x).get(cur_y + 1)) == EMPTY) {

                    next_wave.add(new Pair<>(cur_x, cur_y + 1));
                    goalMap.get(cur_x).set(cur_y + 1, num_wave);
                }

            }

            current_wave = new ArrayList<>(next_wave);
//            if(current_wave.contains(new Pair(pedestrianX,pedestrianY))){
//                isGoalReached = true;
//            }

            next_wave.clear();
            num_wave++;

            //printMap();
        }
        Pair<Integer, Integer> current_goal = goalList.getFirst();
        goalMap.get(current_goal.getFirst()).set(current_goal.getSecond(), 0);
        //printMap();
    }

    public void printMap() {
        for (int i = 0; i < this.goalMap.size(); i++) {
            for (int j = 0; j < this.goalMap.getFirst().size(); j++) {
                System.out.print(this.goalMap.get(i).get(j) + "\t");
            }
            System.out.println();
        }
        System.out.println("////////////////////////////////////////////////////");
    }

    @Override
    public String printCell() {
        return "5";
    }

    @Override
    public boolean achievedGoal(int row, int col) {
        Pair<Integer, Integer> current_goal = goalList.getFirst();
        if (row == current_goal.getFirst() && col == current_goal.getSecond()) {


            goalList.removeFirst();
            if(!goalList.isEmpty()) {
                loadGoalMap();
            }

            for (Pair<Integer, Integer> goal : goalList) {
                System.out.println("goal x: " + goal.getFirst() + " y: " + goal.getSecond());
            }
            return goalList.isEmpty();
        }
        return false;
    }

    public void setGoalList(ArrayList<Pair<Integer, Integer>> goalList) {
        this.goalList = new ArrayList<>(goalList);
    }

    public void setExitGoal(int rowGoal, int colGoal) {
        this.goalList.addLast(new Pair<>(rowGoal, colGoal));
//        for (Pair<Integer,Integer> goal : goalList){
//            System.out.println("goal x: "+goal.getFirst() +" y: "+ goal.getSecond());
//        }
    }
}
