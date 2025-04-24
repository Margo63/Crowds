package model.ca;

public record MapPoint(int row, int column) {
    @Override
    public String toString() {
        return "row: " + row + ", column: " + column+";";
    }
}
