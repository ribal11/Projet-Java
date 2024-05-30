import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ResourceUsableRenderer extends JLabel implements ListCellRenderer<ResourceUsage> {
	public Component getListCellRendererComponent(JList<? extends ResourceUsage> list, ResourceUsage value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            setText(value.toComboBoxString());
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
