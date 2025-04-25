package model;
import model.ca.MapPoint;
import observer.IObservable;
import observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class Model implements IObservable {
    private List<IObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserversAboutSize(int row, int column) {

        observers.forEach(observer -> observer.updateSize(row, column));
    }

    @Override
    public void notifyObserversAboutConflict(int row, int column) {
        observers.forEach(observer -> observer.updateConflict(row, column));
    }

    @Override
    public void notifyObserversAboutNewPedestrianOnBoard() {
        observers.forEach(IObserver::updateAddPedestrian);
    }

    @Override
    public void notifyObserversAboutRemovePedestrianOffBoard() {
        observers.forEach(IObserver::updateRemovePedestrian);
    }

    @Override
    public void notifyObserversAboutPedestrianWay(ArrayList<MapPoint> way) {
        observers.forEach(observer -> observer.addWayPedestrian(way));
    }
}
