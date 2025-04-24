package observer;

import model.ca.MapPoint;

import java.util.ArrayList;

public interface IObserver {

    void updateConflict();
    void updateAddPedestrian();
    void updateRemovePedestrian();
    void addWayPedestrian(ArrayList<MapPoint> way);
}
