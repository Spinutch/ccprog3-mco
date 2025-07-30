package view;

import model.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EditCharacterView extends JFrame {
    private JLabel title;
    private JPanel characterButtonPanel;
    private JButton back;
    private JPanel mainPanel;

    private EditCharacterListener editCharacterListener;

    public EditCharacterView() {
        super("Edit Character");
        initComponents();
        setFrame();
    }

    private void initComponents() {
        title = new JLabel("Edit Character", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));

        characterButtonPanel = new JPanel();
        characterButtonPanel.setLayout(new GridLayout(0, 1, 10, 10));

        JScrollPane scrollPane = new JScrollPane(characterButtonPanel);

        back = new JButton("Back");
        back.setBackground(new Color(138, 3, 3));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Serif", Font.PLAIN, 12));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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

        characterButtonPanel.add(label);
        characterButtonPanel.revalidate();
        characterButtonPanel.repaint();
    }

    public void showCharacterList(List<Character> characters) {
        characterButtonPanel.removeAll();
        for (Character character : characters) {
            JButton characterButton = new JButton(character.getName() + " (" + character.getCharacterClass() + ")");
            characterButton.setFont(new Font("Serif", Font.PLAIN, 14));
            characterButton.setBackground(new Color(138, 3, 3));
            characterButton.setForeground(Color.WHITE);
            characterButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "You want to edit " + character.getName() + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION && editCharacterListener != null) {
                    editCharacterListener.onCharacterEdit(character);
                }
            });
            characterButtonPanel.add(characterButton);
        }
        characterButtonPanel.revalidate();
        characterButtonPanel.repaint();
    }

    public void addBackButtonListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    public void addEditCharacterListener(EditCharacterListener listener) {
        this.editCharacterListener = listener;
    }

    public interface EditCharacterListener {
        void onCharacterEdit(Character character);
    }
}
