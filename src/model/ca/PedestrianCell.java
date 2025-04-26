package model.ca;

import data.MapPoint;
import data.State;

import java.util.ArrayList;

import static data.State.EMPTY;

public class PedestrianCell extends Cell {

    private ArrayList<MapPoint> goalList;
    private ArrayList<ArrayList<Integer>> goalMap = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> startMap;
    private ArrayList<MapPoint> way = new ArrayList();
    private int num;
    PedestrianCell(int num){
        this.num = num;
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

        ArrayList<MapPoint> currentWave = new ArrayList<>();
        ArrayList<MapPoint> nextWave = new ArrayList<>();

        currentWave.add(this.goalList.getFirst());

        int height = goalMap.size();
        if (height == 0) return;
        int width = goalMap.getFirst().size();
        int waveIndex = 1;
        //printMap();

        while (!currentWave.isEmpty()) {
            for (MapPoint p : currentWave) {
                int currentX = p.row();
                int currentY = p.column();

                if (currentX - 1 >= 0 && State.getFromInt(goalMap.get(currentX - 1).get(currentY)) == EMPTY) {
                    nextWave.add(new MapPoint(currentX - 1, currentY));
                    goalMap.get(currentX - 1).set(currentY, waveIndex);
                }

                if (currentX + 1 < height && State.getFromInt(goalMap.get(currentX + 1).get(currentY)) == EMPTY) {
                    //System.out.println("down: row= "+cur_x+" column= "+(cur_y+1));
                    nextWave.add(new MapPoint(currentX + 1, currentY));
                    goalMap.get(currentX + 1).set(currentY, waveIndex);
                }
                if (currentY - 1 >= 0 && State.getFromInt(goalMap.get(currentX).get(currentY - 1)) == EMPTY) {
                    nextWave.add(new MapPoint(currentX, currentY - 1));
                    goalMap.get(currentX).set(currentY - 1, waveIndex);
                }
                if (currentY + 1 < width && State.getFromInt(goalMap.get(currentX).get(currentY + 1)) == EMPTY) {

                    nextWave.add(new MapPoint(currentX, currentY + 1));
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
        MapPoint currentGoal = goalList.getFirst();
        goalMap.get(currentGoal.row()).set(currentGoal.column(), 0);
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

    public ArrayList<MapPoint> getWay(){
        return way;
    }

    @Override
    public boolean isGoalAchieved(int row, int col) {
        way.add(new MapPoint(row, col));
        MapPoint currentGoal = goalList.getFirst();
        if (row == currentGoal.row() && col == currentGoal.column()) {


            goalList.removeFirst();
            if(!goalList.isEmpty()) {
                loadGoalMap();
            }

//            for (Pair<Integer, Integer> goal : goalList) {
//                System.out.println("goal row: " + goal.getFirst() + " column: " + goal.getSecond());
//            }
            return goalList.isEmpty();
        }
        return false;
    }

    public void setGoalList(ArrayList<MapPoint> goalList) {
        this.goalList = new ArrayList<>(goalList);
    }

    public void setExitGoal(int rowGoal, int colGoal) {
        this.goalList.addLast(new MapPoint(rowGoal, colGoal));
//        for (Pair<Integer,Integer> goal : goalList){
//            System.out.println("goal row: "+goal.getFirst() +" column: "+ goal.getSecond());
//        }
    }
    public void goToExit(){
        if(!goalList.isEmpty()){
            MapPoint exit = goalList.getLast();
            goalList.clear();
            goalList.add(exit);
        }
    }
}
