package presentation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    // size of board
    private int sizeRows = 10;
    private int sizeCols = 10;
    // board to draw
    private ArrayList<ArrayList<Integer>> board = new ArrayList<>();

    // painting board accroding to size
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < sizeRows; i++) {
            for (int j = 0; j < sizeCols; j++) {
                //TODO
                //draw according array
                g.drawRect(j * 10, i * 10, 10, 10);
            }
        }
    }

    public DrawPanel() {
        JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(sizeRows, 0, 100, 1));
        rowsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    sizeRows = (int) rowsSpinner.getValue();
                    //TODO
                    //resize array
                    repaint();
                }catch (Exception err){
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(rowsSpinner);

        JSpinner colsSpinner = new JSpinner(new SpinnerNumberModel(sizeCols, 0, 100, 1));
        colsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    sizeCols = (int) colsSpinner.getValue();
                    //TODO
                    //resize array
                    repaint();
                }catch (Exception err){
                    System.out.println(err.getMessage());
                }

            }
        });
        this.add(colsSpinner);

        JButton emptyButton = new JButton("empty");

    }
}
