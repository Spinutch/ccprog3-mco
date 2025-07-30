package view;

import model.Ability;
import model.Character;
import model.MagicItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CharacterListView extends JFrame {
    private JLabel title;
    private JButton back;
    private JPanel mainPanel;
    private JPanel characterButtonPanel;

    private CharacterSelectListener characterSelectListener;

    public CharacterListView() {
        super("View Character");
        initComponents();
        setFrame();
    }

    private void initComponents() {
        title = new JLabel("Character List", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));

        // New panel to hold buttons
        characterButtonPanel = new JPanel();
        characterButtonPanel.setLayout(new GridLayout(0, 1, 10, 10));

        back = new JButton("Back");
        back.setBackground(new Color(138, 3, 3));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Serif", Font.PLAIN, 12));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(characterButtonPanel), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(back);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setFrame() {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void showNoCharacters() {
        characterButtonPanel.removeAll();

        JLabel label = new JLabel("Please create your characters first!", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 27)); 
        label.setHorizontalAlignment(SwingConstants.CENTER); 
        label.setVerticalAlignment(SwingConstants.CENTER);

        characterButtonPanel.setLayout(new BorderLayout()); 
        characterButtonPanel.add(label, BorderLayout.CENTER);

        characterButtonPanel.revalidate();
        characterButtonPanel.repaint();
    }

    public void showCharacterDetails(Character character) {
        // Show a pop-up with the details or you can forward to another view
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(character.getName()).append("\n");
        sb.append("Race: ").append(character.getRace()).append("\n");
        sb.append("Class: ").append(character.getCharacterClass()).append("\n");
        sb.append("HP: ").append(character.getHP()).append("/").append(character.getMaxHP()).append("\n");
        sb.append("EP: ").append(character.getEP()).append("/").append(character.getMaxEP()).append("\n");
        sb.append("Wins: ").append(character.getWinCount()).append("\n\n");
        sb.append("Equipped Item: ").append(character.getEquippedItem() == null ? "None" : character.getEquippedItem()).append("\n");
        sb.append("Inventory (" + character.getInventory().size() + " items):\n");
        if (character.getInventory().isEmpty()) {
            sb.append("  No items\n");
        } else {
            for (MagicItem item : character.getInventory()) {
                sb.append("  - ").append(item.toString()).append("\n");
            }
        }
        sb.append("\nAbilities:\n");
        for (Ability ability : character.getAbilities()) {
            sb.append("  - ").append(ability.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Character Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showCharacterList(List<Character> characters) {
        characterButtonPanel.removeAll();

        for (Character c : characters) {
            JButton charButton = new JButton(c.getName() + " (" + c.getCharacterClass() + ")");
            charButton.setFont(new Font("Serif", Font.PLAIN, 14));
            charButton.setFocusPainted(false);
            charButton.setBackground(new Color(138, 3, 3));
            charButton.setForeground(Color.WHITE);

            charButton.addActionListener(e -> {
                if (characterSelectListener != null) {
                    characterSelectListener.onCharacterSelected(c);
                }
            });

            characterButtonPanel.add(charButton);
        }

        characterButtonPanel.revalidate();
        characterButtonPanel.repaint();
    }

    public void addBackButtonListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    public void addCharacterSelectListener(CharacterSelectListener characterSelectListener) {
        this.characterSelectListener = characterSelectListener;
    }

    public interface CharacterSelectListener {
        void onCharacterSelected(Character character);
    }
}
