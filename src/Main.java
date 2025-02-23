import model.Board;
import presentation.Panel;
import presentation.Screen;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(10);
        board.setCellNotAvaible(1, 5);
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