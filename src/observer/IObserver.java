package observer;

import data.MapPoint;

import java.util.ArrayList;

public interface IObserver {

    void updateConflict(int row, int col);
    void updateSize(int row, int col);
    void updateAddPedestrian();
    void updateRemovePedestrian();
    void addWayPedestrian(ArrayList<MapPoint> way);
}
