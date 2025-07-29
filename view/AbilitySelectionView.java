package view;

import model.Ability;
import model.AllAbilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AbilitySelectionView extends JFrame {
    private JLabel titleLabel;
    private JPanel abilitiesPanel;
    private JButton confirmButton;

    private JCheckBox[] abilityCheckBoxes;

    public AbilitySelectionView(String selectedClass) {
        super("Select Abilities");
        initComponents(selectedClass);
        setFrame();
    }

    private void initComponents(String selectedClass) {
        titleLabel = new JLabel("Choose 3 Abilities for your class: " + selectedClass, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));

        List<Ability> abilities = AllAbilities.getAbilitiesByClass(selectedClass);
        abilityCheckBoxes = new JCheckBox[abilities.size()];

        abilitiesPanel = new JPanel();
        abilitiesPanel.setLayout(new GridLayout(abilities.size(), 1, 5, 5));

        for (int i = 0; i < abilities.size(); i++) {
            Ability ability = abilities.get(i);
            String label = ability.getName() + " - " + ability.getDescription() + " (EP: " + ability.getEpCost() + ")";
            abilityCheckBoxes[i] = new JCheckBox("  " + label);
            abilityCheckBoxes[i].setFont(new Font("Serif", Font.PLAIN, 14));
            abilitiesPanel.add(abilityCheckBoxes[i]);
        }

        confirmButton = new JButton("Confirm");
        confirmButton.setBackground(new Color(138, 3, 3));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Serif", Font.BOLD, 16));
    }

    private void setFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(abilitiesPanel), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(confirmButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void addConfirmButtonListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public List<Ability> getSelectedAbilities(String selectedClass) {
        List<Ability> available = AllAbilities.getAbilitiesByClass(selectedClass);
        java.util.List<Ability> selected = new java.util.ArrayList<>();
        for (int i = 0; i < abilityCheckBoxes.length; i++) {
            if (abilityCheckBoxes[i].isSelected()) {
                selected.add(available.get(i));
            }
        }
        return selected;
    }
}
