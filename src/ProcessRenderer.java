import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ProcessRenderer extends JLabel implements ListCellRenderer<Process> {
    public Component getListCellRendererComponent(JList<? extends Process> list, Process value, int index,
            boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            setText(value.toComboBoxString());
        } else {
            setText(" ");
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setOpaque(true);
        return this;
    }
}
