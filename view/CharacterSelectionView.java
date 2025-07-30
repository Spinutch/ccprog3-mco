package view;

import model.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CharacterSelectionView extends JFrame {
    private JPanel characterButtonPanel;
    private JPanel mainPanel;
    private CharacterSelectionListener listener;
    private JLabel title;
    private JButton back;

    public CharacterSelectionView() {
        super("Select Your Character");
        initComponents();
        setFrame();
    }

    public void initComponents() {
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

    public void setFrame() {
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
    
    public void showCharacterList(List<Character> characters) {
        characterButtonPanel.removeAll();

        for (Character c : characters) {
            JButton charButton = new JButton(c.getName() + " (" + c.getCharacterClass() + ")");
            charButton.setFont(new Font("Serif", Font.PLAIN, 14));
            charButton.setFocusPainted(false);
            charButton.setBackground(new Color(138, 3, 3));
            charButton.setForeground(Color.WHITE);

            charButton.addActionListener(e -> {
                    if (listener != null) {
                        listener.onCharacterSelected(c);
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

    public void setCharacterSelectionListener(CharacterSelectionListener listener) {
        this.listener = listener;
    }

    public interface CharacterSelectionListener {
        void onCharacterSelected(Character selectedCharacter);
    }
}
