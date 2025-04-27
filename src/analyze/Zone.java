package analyze;

public class Zone {
    public int amountOfPedestrian;
    public int size;
    public double densityOfPedestrian;
    public Zone() {
        this.amountOfPedestrian = 0;
        this.size = 1;
        this.densityOfPedestrian = 0.0;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "amountOfPedestrian=" + amountOfPedestrian +
                ", size=" + size +
                ", densityOfPedestrian=" + densityOfPedestrian +
                '}';
    }
}
