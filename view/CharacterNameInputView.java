package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharacterNameInputView extends JFrame {
    private JLabel title;
    private JLabel prompt;
    private JTextField characterNameField;
    private JButton nextButton;

    public CharacterNameInputView() {   
        super("Create Character");
        initComponents();
        setFrame();
    }


     private void initComponents() {
        title = new JLabel("Character Creation", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));

        prompt = new JLabel("Please enter your character's name:", SwingConstants.CENTER);
        prompt.setFont(new Font("Serif", Font.PLAIN, 24));

        characterNameField = new JTextField(20);
        characterNameField.setFont(new Font("Monospaced", Font.PLAIN, 14));


        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(138,3,3));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Serif", Font.BOLD, 14));


    }

    private void setFrame() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel promptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        promptPanel.add(title);
        mainPanel.add(promptPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2,1));
        inputPanel.add(prompt);

        JPanel characterNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        characterNamePanel.add(characterNameField);
        inputPanel.add(characterNamePanel);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);


        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public String getCharacterName() {
        return characterNameField.getText().trim();
    }

    public void addNextButtonListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }
}
