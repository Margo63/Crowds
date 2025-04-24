package observer;

import model.ca.MapPoint;

import java.util.ArrayList;

public interface IObservable {
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObserversAboutConflict();
    void notifyObserversAboutNewPedestrianOnBoard();
    void notifyObserversAboutRemovePedestrianOffBoard();
    void notifyObserversAboutPedestrianWay(ArrayList<MapPoint> way);
}
