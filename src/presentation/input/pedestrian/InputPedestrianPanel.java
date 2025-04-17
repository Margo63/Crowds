package presentation.input.pedestrian;

import model.State;
import presentation.ViewModelPanel;
import presentation.input.DrawRect;
import utils.Constants;
import utils.DrawCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class InputPedestrianPanel extends ViewModelPanel {
    // board to draw
    private ArrayList<ArrayList<DrawRect>> board = new ArrayList<>();
    //list of entery
    private ArrayList<PedestrianInput> enteries = new ArrayList<>();

    //
    JComboBox<PedestrianInput> comboBox;
    DrawCell drawCell = new DrawCell();

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //System.out.println(board);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.getFirst().size(); j++) {
                drawCell.draw(g, board.get(i).get(j).getState(), (int) board.get(i).get(j).getX(), (int) board.get(i).get(j).getY());

            }
            //System.out.println();
        }
        g.setColor(Color.RED);
        PedestrianInput pedestrianInput = (PedestrianInput)comboBox.getSelectedItem();
        if(pedestrianInput != null){
            Point selectedPoint = pedestrianInput.pedestrianEntry;
            if (selectedPoint != null){
                g.drawRect(selectedPoint.x*Constants.SIZE_OF_CELL, selectedPoint.y*Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL, Constants.SIZE_OF_CELL);
            }

        }



    }

    public InputPedestrianPanel() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadBoard();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                saveBoard();
            }
        });

        comboBox = new JComboBox();
        comboBox.setRenderer(new InputPedestrianRender());
        this.add(comboBox);

        comboBox.addActionListener(e -> {
            //System.out.println("Выбран: " + comboBox.getSelectedItem());
            repaint();
        });
    }

    private void loadBoard() {
        if (this.getViewModel().checkBoard()) {
            this.board = this.getViewModel().getBoard();
            for (int i = 0; i < board.size(); i++) {
                for (int j = 0; j < board.getFirst().size(); j++) {
                    if (board.get(i).get(j).getState() == State.ENTRY) {
                        Point entry = new Point(j, i);
                        PedestrianInput pedestrianInput = new PedestrianInput();
                        pedestrianInput.pedestrianEntry = entry;
                        enteries.add(pedestrianInput);
                        comboBox.addItem(pedestrianInput);
                    }

                }
            }

            repaint();
        }
    }

    private void saveBoard() {

    }
}
