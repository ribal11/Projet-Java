import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ProjectRenderer extends JLabel implements ListCellRenderer<Projet> {
    public Component getListCellRendererComponent(JList<? extends Projet> list, Projet value, int index,
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
