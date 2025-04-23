package analyze;

import model.ca.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Analyze {
    private int conflict = 0;
    private ArrayList<ArrayList<Integer>> zones;
    private Map<Integer,Integer> amountPedestrianInZone = new HashMap<Integer,Integer>();
    public Analyze() {

    }


    public void analyze_board(Board board) {
        board.pedestriansWishList.forEach((key, value) -> {
            if (value.size() > 1) {
                conflict++;
            }
        });
        for (Integer key: amountPedestrianInZone.keySet()) {
            amountPedestrianInZone.put(key, 0);
        }
        for(int i = 0 ; i < board.getAmountOfRows() ; i++) {
            for (int j = 0 ; j < board.getAmountOfCols() ; j++) {
                if(board.getCell(i,j).getIsPedestrian()){
                    amountPedestrianInZone.put(zones.get(i).get(j),amountPedestrianInZone.get(zones.get(i).get(j))+1);
                }
            }
        }
    }

    public void loadZones(ArrayList<ArrayList<Integer>> zones){
        this.zones = zones;
        for(int i = 0 ; i < zones.size() ; i++) {
            for(int j = 0 ; j < zones.get(i).size() ; j++) {
                if(!amountPedestrianInZone.containsKey(zones.get(i).get(j))){
                    amountPedestrianInZone.put(zones.get(i).get(j),0);
                }
            }
        }
    }

    public int getConflict() {
        return conflict;
    }
    public Map<Integer,Integer> getAmountPedestrianInZone() {
        return amountPedestrianInZone;
    }
}
