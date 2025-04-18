package presentation.input.pedestrian;

import javax.swing.*;
import java.awt.*;

public class ExitRender extends JLabel implements ListCellRenderer<Point>  {

    @Override
    public Component getListCellRendererComponent(JList<? extends Point> list, Point value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            setText("выход столбец: " + value.x + ", ряд: " + value.y);
        } else {
            setText("");
        }
        return this;
    }
}
