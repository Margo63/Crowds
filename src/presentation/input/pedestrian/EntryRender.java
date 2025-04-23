package presentation.input.pedestrian;

import presentation.models.PedestrianInput;

import javax.swing.*;
import java.awt.*;

public class EntryRender extends JLabel implements ListCellRenderer<PedestrianInput> {

    @Override
    public Component getListCellRendererComponent(JList<? extends PedestrianInput> list, PedestrianInput value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            setText("вход столбец: " + value.pedestrianEntry.x + ", ряд: " + value.pedestrianEntry.y);
        } else {
            setText("");
        }
        return this;
    }
}
