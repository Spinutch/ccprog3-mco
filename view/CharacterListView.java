// CharacterListView.java
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
    private JTextArea displayArea;
    private JButton back;
    private JPanel mainPanel;

    private CharacterSelectListener characterSelectListener;

    public CharacterListView() {
        super("View Character");
        initComponents();
        setFrame();
    }

    private void initComponents() {
        title = new JLabel("Character List", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);

        back = new JButton("Back");
        back.setBackground(new Color(138, 3, 3));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Serif", Font.PLAIN, 12));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

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
        displayArea.setText("Please create your characters first!");
    }

    public void showCharacterDetails(Character character) {
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
        displayArea.setText(sb.toString());
    }

    public void showCharacterList(List<Character> characters) {
        StringBuilder sb = new StringBuilder();
        sb.append("List of Characters:\n");
        int index = 1;
        for (Character c : characters) {
            sb.append(index++).append(" - ").append(c.getName())
              .append(" (").append(c.getCharacterClass()).append(")\n");
        }
        sb.append("\nClick a character number to view details.");
        displayArea.setText(sb.toString());

        // Optional: Add character selection logic via input dialog (basic simulation)
        String input = JOptionPane.showInputDialog(this, "Enter character number:");
        if (input != null) {
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= characters.size()) {
                    characterSelectListener.onCharacterSelected(characters.get(choice - 1));
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    public void addBackButtonListener(ActionListener l) {
        back.addActionListener(l);
    }

    public void addCharacterSelectListener(CharacterSelectListener listener) {
        this.characterSelectListener = listener;
    }

    public interface CharacterSelectListener {
        void onCharacterSelected(Character character);
    }
}
