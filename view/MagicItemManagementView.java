package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MagicItemManagementView extends JFrame {
    private JButton viewInventoryButton;
    private JButton equipItemButton;
    private JButton unequipItemButton;
    private JButton backButton;

    public MagicItemManagementView() {
        super("Magic Item Management");
        initComponents();
        setFrame();

    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("MAGIC ITEM MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        viewInventoryButton = new JButton("View Inventory");
        equipItemButton = new JButton("Equip Item");
        unequipItemButton = new JButton("Unequip Item");
        backButton = new JButton("Back to Character Editing");

        buttonPanel.add(viewInventoryButton);
        buttonPanel.add(equipItemButton);
        buttonPanel.add(unequipItemButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.CENTER);


    }

    private void setFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);

    }

    // Getters for buttons
    public JButton getViewInventoryButton() {
        return viewInventoryButton;
    }

    public JButton getEquipItemButton() {
        return equipItemButton;
    }

    public JButton getUnequipItemButton() {
        return unequipItemButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    // Add action listeners
    public void setViewInventoryListener(ActionListener listener) {
        viewInventoryButton.addActionListener(listener);
    }

    public void setEquipItemListener(ActionListener listener) {
        equipItemButton.addActionListener(listener);
    }

    public void setUnequipItemListener(ActionListener listener) {
        unequipItemButton.addActionListener(listener);
    }

    public void setBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
