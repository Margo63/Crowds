import model.Board;
import presentation.Panel;
import presentation.Screen;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(5);
        board.setCellNotAvaible(1, 3);
        //System.out.println(board.getCell(0,5).getAvailable());
        Panel panel = new Panel();
        panel.addBoard(board);
        Screen screen = new Screen(panel);

        while (true) {
            board.step();
            panel.repaint();
        }
//        ArrayList<Integer> arr1 = new ArrayList<Integer>();
//        arr1.add(1);
//        arr1.add(2);
//        arr1.add(3);
//        ArrayList<Integer> arr2 = new ArrayList<Integer>();
//        arr2.addAll(arr1);
//        arr2.set(0,5);
//        System.out.println("arr1:");
//        for (int i = 0; i < arr1.size(); i++) {
//            System.out.print(arr1.get(i));
//        }
//        System.out.println();
//        System.out.println("arr2:");
//        for (int i = 0; i < arr2.size(); i++) {
//            System.out.print(arr2.get(i));
//        }
    }
}