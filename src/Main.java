import analyze.Analyze;
import model.ca.Board;
import presentation.*;
import utils.ReadFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        boardPanel.addBoard(board);
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

        InputPanel inputPanel = new InputPanel();
        DrawPanel drawPanel = new DrawPanel();

        screen.addPanel(inputPanel, "input");
        screen.addPanel(boardPanel,"board");
        screen.addPanel(drawPanel,"draw");

        screen.changePanel("input");
    }

    private static Object buttonAction(Object o) {
        System.out.println("button Action");
        return null;
    }



}