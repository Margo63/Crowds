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

        ArrayList<Pair<Integer, Integer>> currentWave = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> nextWave = new ArrayList<>();

        currentWave.add(this.goalList.getFirst());

        int height = goalMap.size();
        if (height == 0) return;
        int width = goalMap.getFirst().size();
        int waveIndex = 1;
        //printMap();

        while (!currentWave.isEmpty()) {
            for (Pair<Integer, Integer> p : currentWave) {
                int currentX = p.getFirst();
                int currentY = p.getSecond();

                if (currentX - 1 >= 0 && State.getFromInt(goalMap.get(currentX - 1).get(currentY)) == EMPTY) {
                    nextWave.add(new Pair<>(currentX - 1, currentY));
                    goalMap.get(currentX - 1).set(currentY, waveIndex);
                }

                if (currentX + 1 < height && State.getFromInt(goalMap.get(currentX + 1).get(currentY)) == EMPTY) {
                    //System.out.println("down: x= "+cur_x+" y= "+(cur_y+1));
                    nextWave.add(new Pair<>(currentX + 1, currentY));
                    goalMap.get(currentX + 1).set(currentY, waveIndex);
                }
                if (currentY - 1 >= 0 && State.getFromInt(goalMap.get(currentX).get(currentY - 1)) == EMPTY) {
                    nextWave.add(new Pair<>(currentX, currentY - 1));
                    goalMap.get(currentX).set(currentY - 1, waveIndex);
                }
                if (currentY + 1 < width && State.getFromInt(goalMap.get(currentX).get(currentY + 1)) == EMPTY) {

                    nextWave.add(new Pair<>(currentX, currentY + 1));
                    goalMap.get(currentX).set(currentY + 1, waveIndex);
                }

            }

            currentWave = new ArrayList<>(nextWave);
//            if(current_wave.contains(new Pair(pedestrianX,pedestrianY))){
//                isGoalReached = true;
//            }

            nextWave.clear();
            waveIndex++;

            //printMap();
        }
        Pair<Integer, Integer> currentGoal = goalList.getFirst();
        goalMap.get(currentGoal.getFirst()).set(currentGoal.getSecond(), 0);
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
    public boolean achievedGoal(int row, int col) {
        Pair<Integer, Integer> currentGoal = goalList.getFirst();
        if (row == currentGoal.getFirst() && col == currentGoal.getSecond()) {


            goalList.removeFirst();
            if(!goalList.isEmpty()) {
                loadGoalMap();
            }

//            for (Pair<Integer, Integer> goal : goalList) {
//                System.out.println("goal x: " + goal.getFirst() + " y: " + goal.getSecond());
//            }
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
    public void goToExit(){
        if(!goalList.isEmpty()){
            Pair<Integer, Integer> exit = goalList.getLast();
            goalList.clear();
            goalList.add(exit);
        }
    }
}
