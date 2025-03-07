package analyze;

import model.Board;
import model.Cell;

public class Analyze {
    private int conflict = 0;

    public Analyze() {

    }


    public void analyze_board(Board board) {
        board.wish_list.forEach((key, value) -> {
            if (value.size() > 1) {
                conflict++;
            }
        });
    }

    public int getConflict() {
        return conflict;
    }
}
