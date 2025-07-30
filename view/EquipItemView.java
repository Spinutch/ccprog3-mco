package view;

import model.MagicItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EquipItemView extends JFrame {
    private JCheckBox[] itemCheckBoxes;
    private JButton confirmButton;
    private JPanel itemPanel;

    public EquipItemView(List<MagicItem> items) {
        super("Equip Magic Item");
        initComponents(items);
        setFrame();
    }

    private void initComponents(List<MagicItem> items) {
        itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(items.size(), 1, 5, 5));

        itemCheckBoxes = new JCheckBox[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemCheckBoxes[i] = new JCheckBox(items.get(i).getName() + " - " + items.get(i).getDescription());
            itemPanel.add(itemCheckBoxes[i]);
        }

        confirmButton = new JButton("Confirm");

        this.setLayout(new BorderLayout());
        this.add(itemPanel, BorderLayout.CENTER);
        this.add(confirmButton, BorderLayout.SOUTH);
    }

    private void setFrame() {
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JCheckBox[] getItemCheckBoxes() {
        return itemCheckBoxes;
    }
}
