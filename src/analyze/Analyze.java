package analyze;

import model.ca.Board;
import model.ca.MapPoint;
import observer.IObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyze implements IObserver {
    private int conflict = 0;
    private ArrayList<ArrayList<Integer>> zones;
    private final Map<Integer,Zone> zoneData = new HashMap<>();
    private int amountOfEnteredPedestrian = 0;
    private int amountOfPedestrianOnBoard = 0;
    private int amountOfExitedPedestrian = 0;
    private List<ArrayList<MapPoint>> pedestrianWays = new ArrayList<>();
    public Analyze() {

    }


    public void analyze_board(Board board) {


        for (Integer key: zoneData.keySet()) {
            zoneData.get(key).amountOfPedestrian = 0;
        }
        for(int i = 0 ; i < board.getAmountOfRows() ; i++) {
            for (int j = 0 ; j < board.getAmountOfCols() ; j++) {
                if(board.getCell(i,j).getIsPedestrian()){
                    int zoneIndex = zones.get(i).get(j);
                    zoneData.get(zoneIndex).amountOfPedestrian++;

                }
            }
        }
        for (Integer key: zoneData.keySet()) {
            //System.out.println(zoneData.get(key).densityOfPedestrian+"/"+zoneData.get(key).size+"="+zoneData.get(key).densityOfPedestrian/(double)zoneData.get(key).size);
            zoneData.get(key).densityOfPedestrian = zoneData.get(key).amountOfPedestrian/(double)zoneData.get(key).size;
        }


    }

    public void loadZones(ArrayList<ArrayList<Integer>> zones){
        this.zones = zones;
        for(int i = 0 ; i < zones.size() ; i++) {
            for(int j = 0 ; j < zones.get(i).size() ; j++) {
                int zoneIndex = zones.get(i).get(j);
                if(!zoneData.containsKey(zoneIndex)){
                    zoneData.put(zones.get(i).get(j),new Zone());
                }
                else {
                    zoneData.get(zones.get(i).get(j)).size++;
                }
            }
        }
        zoneData.remove(-1);
    }

    public int getConflict() {
        return conflict;
    }
    public Map<Integer,Zone> getZoneData() {
        return zoneData;
    }

    public void report(){
        System.out.println("amount of pedestrian = "+amountOfPedestrianOnBoard
                            + " amount of exited pedestrian = "+amountOfExitedPedestrian
                            + " amount of entered pedestrian = "+amountOfEnteredPedestrian
                            + "ways = " + pedestrianWays
        );
        analizeWay();
    }

    private void analizeWay(){
        double [][]matrix = new double[pedestrianWays.size()][pedestrianWays.size()];
        for (int i = 0; i < pedestrianWays.size(); i++) {
            for (int j = i+1; j < pedestrianWays.size(); j++) {
                double distance = FrechetDistance.frechetDistance(
                        pedestrianWays.get(i),
                        pedestrianWays.get(j)
                );
                matrix[i][j] = distance;
                matrix[j][i] = distance;
            }
        }
        for (int i = 0; i < pedestrianWays.size(); i++) {
            for (int j = 0; j < pedestrianWays.size(); j++) {
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println();
        }

    }


    @Override
    public void updateConflict() {
        conflict++;
    }

    @Override
    public void updateAddPedestrian() {
        amountOfEnteredPedestrian++;
        amountOfPedestrianOnBoard++;
    }

    @Override
    public void updateRemovePedestrian() {
        amountOfExitedPedestrian++;
        amountOfPedestrianOnBoard--;
    }

    @Override
    public void addWayPedestrian(ArrayList<MapPoint> way) {
        pedestrianWays.add(way);
    }
}
