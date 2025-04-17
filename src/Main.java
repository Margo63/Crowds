import analyze.Analyze;
import model.ca.Board;
import presentation.*;
import presentation.input.InputDrawPanel;
import presentation.input.InputFilePanel;
import presentation.input.pedestrian.InputPedestrianPanel;
import presentation.input.InputZonePanel;
import utils.Constants;
import utils.ReadFile;

import javax.swing.*;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Screen screen = new Screen();

        ArrayList<ArrayList<Integer>> tmp = new ReadFile().readFile("map.txt");

        Board board = new Board(tmp);
        Analyze analyze = new Analyze();
        board.addPedestrian(2,3);


        //System.out.println(board.getCell(0,5).getAvailable());

        BoardPanel boardPanel = new BoardPanel();
        //boardPanel.addBoard(board);
        JButton button = new JButton("Step");
        JLabel label = new JLabel();
        boardPanel.add(button);
        boardPanel.add(label);

        button.addActionListener(e -> {
            try {
                board.step();
                analyze.analyze_board(board);
                label.setText("количество конфликтов: " + analyze.getConflict());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            boardPanel.repaint();
        });

        InputFilePanel inputPanel = new InputFilePanel();
        InputDrawPanel drawPanel = new InputDrawPanel();
        InputZonePanel zonePanel = new InputZonePanel();
        InputPedestrianPanel pedestrianPanel = new InputPedestrianPanel();

        screen.addPanel(inputPanel, Constants.INPUT_FILE);
        screen.addPanel(boardPanel,Constants.BOARD);
        screen.addPanel(drawPanel,Constants.INPUT_DRAW);
        screen.addPanel(zonePanel,Constants.INPUT_ZONE);
        screen.addPanel(pedestrianPanel,Constants.INPUT_PEDESTRIAN);

        screen.changePanel(Constants.INPUT_FILE);
    }

    private static Object buttonAction(Object o) {
        System.out.println("button Action");
        return null;
    }



}