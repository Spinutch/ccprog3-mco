package view;

import model.Ability;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AbilitySelectionView extends JFrame {
    private JPanel mainPanel;
    private JPanel abilityPanel;
    private List<JCheckBox> abilityCheckboxes;
    private JButton confirmButton;
    private JButton backButton;
    private List<Ability> currentAbilities;

    public AbilitySelectionView(String characterClass) {
        super("Select Abilities - " + characterClass);
        this.abilityCheckboxes = new ArrayList<>();
        initComponents();
        setFrame();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel("Select Abilities", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Ability selection panel
        abilityPanel = new JPanel();
        abilityPanel.setLayout(new BoxLayout(abilityPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(abilityPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        confirmButton = new JButton("Confirm Selection");
        confirmButton.setBackground(new Color(138, 3, 3));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Serif", Font.PLAIN, 14));

        backButton = new JButton("Back");
        backButton.setBackground(new Color(138, 3, 3));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Serif", Font.PLAIN, 14));

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setFrame() {
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setCurrentAbilities(List<Ability> abilities) {
        this.currentAbilities = abilities;
        updateAbilityCheckboxes();
    }

    private void updateAbilityCheckboxes() {
        abilityPanel.removeAll();
        abilityCheckboxes.clear();

        for (Ability ability : currentAbilities) {
            JCheckBox checkbox = new JCheckBox(ability.getName());
            checkbox.setFont(new Font("Serif", Font.PLAIN, 14));
            abilityCheckboxes.add(checkbox);
            abilityPanel.add(checkbox);
        }

        abilityPanel.revalidate();
        abilityPanel.repaint();
    }

    public List<Ability> getSelectedAbilities() {
        List<Ability> selectedAbilities = new ArrayList<>();
        for (int i = 0; i < abilityCheckboxes.size(); i++) {
            if (abilityCheckboxes.get(i).isSelected()) {
                selectedAbilities.add(currentAbilities.get(i));
            }
        }
        return selectedAbilities;
    }

    public void addConfirmButtonListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
