import analyze.Analyze;
import model.Board;
import presentation.DrawPanel;
import presentation.InputPanel;
import presentation.BoardPanel;
import presentation.Screen;

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

        ArrayList<ArrayList<Integer>> tmp = readFile("map.txt");

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
        JButton buttonNext = new JButton("draw");
        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.changePanel("draw");
                System.out.println("changed");
            }
        });
        inputPanel.add(buttonNext);

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

    static ArrayList<ArrayList<Integer>> readFile(String filename) {
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ArrayList<Integer> row = new ArrayList<>();
                for (String value : values) {
                    row.add(Integer.parseInt(value.replaceAll(" ","")));
                    //System.out.print(value.trim() + " | ");
                }
                tmp.add(row);
                //System.out.println();
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return tmp;
    }

}