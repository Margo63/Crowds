package model;

public enum State {
    ENTRY(1), EXIT(2), PEDESTRIAN(3), OBSTRUCTION(-1), EMPTY(0);

    private final int id;

    State(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

    public static State getFromInt(int value) {
        return switch (value) {
            case -1 -> OBSTRUCTION;
            case 0 -> EMPTY;
            case 1 -> ENTRY;
            case 2 -> EXIT;
            case 3 -> PEDESTRIAN;
            default -> EMPTY;
        };
    }
}
