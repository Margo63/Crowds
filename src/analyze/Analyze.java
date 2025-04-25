package analyze;

import model.ca.Board;
import model.ca.MapPoint;
import observer.IObserver;
import utils.Constants;
import utils.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
        Files.createFile(Constants.TBL_ZONE_AMOUNT);
        Files.createFile(Constants.TBL_ZONE_DENSITY);
        Files.createFile(Constants.TBL_WAY);
        Files.writeToFile(Constants.TBL_WAY,"ways\n");
    }


    public void analyze_board(Board board)  {


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



        StringBuilder stringBuilderAmount = new StringBuilder();
        StringBuilder stringBuilderDensity = new StringBuilder();
        String div = "";
        for (Integer key: zoneData.keySet()) {
            //System.out.println(zoneData.get(key).densityOfPedestrian+"/"+zoneData.get(key).size+"="+zoneData.get(key).densityOfPedestrian/(double)zoneData.get(key).size);
            stringBuilderAmount.append(div);
            stringBuilderAmount.append(zoneData.get(key).amountOfPedestrian);
            stringBuilderDensity.append(div);
            stringBuilderDensity.append(zoneData.get(key).densityOfPedestrian);
            div = ",";
        }
        stringBuilderAmount.append('\n');
        stringBuilderDensity.append('\n');
        Files.writeToFile(Constants.TBL_ZONE_AMOUNT, stringBuilderAmount.toString());
        Files.writeToFile(Constants.TBL_ZONE_DENSITY, stringBuilderDensity.toString());



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

        StringBuilder stringBuilder = new StringBuilder();
        String div = "";
        for (Integer key: zoneData.keySet()) {
            //System.out.println(zoneData.get(key).densityOfPedestrian+"/"+zoneData.get(key).size+"="+zoneData.get(key).densityOfPedestrian/(double)zoneData.get(key).size);
            stringBuilder.append(div);
            stringBuilder.append(key);
            div = ",";
        }
        stringBuilder.append('\n');
        Files.writeToFile(Constants.TBL_ZONE_AMOUNT, stringBuilder.toString());
        Files.writeToFile(Constants.TBL_ZONE_DENSITY, stringBuilder.toString());

    }

    public int getConflict() {
        return conflict;
    }
    public Map<Integer,Zone> getZoneData() {
        return zoneData;
    }

    public void report() {
        System.out.println("amount of pedestrian = "+amountOfPedestrianOnBoard
                            + " amount of exited pedestrian = "+amountOfExitedPedestrian
                            + " amount of entered pedestrian = "+amountOfEnteredPedestrian
                            + "ways = " + pedestrianWays
        );
        for (ArrayList<MapPoint> way: pedestrianWays) {
            Files.writeToFile(Constants.TBL_WAY, way.toString()+"\n");
        }
//        try {
//            ProcessBuilder pb = new ProcessBuilder("python", "test.py");
//            Process process = pb.start();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            int exitCode = process.waitFor();
//            System.out.println("Python script finished with exit code " + exitCode);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
//        for (int i = 0; i < pedestrianWays.size(); i++) {
//            for (int j = 0; j < pedestrianWays.size(); j++) {
//                System.out.print(matrix[i][j]+"\t");
//            }
//            System.out.println();
//        }

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
