import analyze.Analyze;
import model.Board;
import presentation.Panel;
import presentation.Screen;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<ArrayList<Integer>> tmp = readFile("C:/Users/User/Documents/IntelijIdea/Crowds/src/map.txt");

        Board board = new Board(tmp);
        Analyze analyze = new Analyze();

        //System.out.println(board.getCell(0,5).getAvailable());
        Panel panel = new Panel();

        panel.addBoard(board);
        JButton button = new JButton("Step");
        JLabel label = new JLabel();
        panel.add(button);
        panel.add(label);
        Screen screen = new Screen(panel);
        button.addActionListener(e -> {
            try {
                board.step();
                analyze.analyze_board(board);
                label.setText("количество конфликтов: " + analyze.getConflict());
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            panel.repaint();
        });


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