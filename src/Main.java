import model.Board;
import presentation.Panel;
import presentation.Screen;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(5);
        board.addPedestrian(1, 3);
        board.addWall(2, 3);
        //System.out.println(board.getCell(0,5).getAvailable());
        Panel panel = new Panel();
        panel.addBoard(board);
        Screen screen = new Screen(panel);

        while (true) {
            board.step();
            panel.repaint();
        }

    }
}