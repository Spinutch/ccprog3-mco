package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharacterCreationSuccessView extends JFrame {
    private JLabel successLabel;
    private JButton doneButton;

    public CharacterCreationSuccessView() {
        super("Create Charater");
        initComponents();
        setFrame();
    }

    private void initComponents() {
        successLabel = new JLabel("Character created successfully!", SwingConstants.CENTER);
        successLabel.setFont(new Font("Serif", Font.BOLD, 35));

        doneButton = new JButton("Done");
        doneButton.setBackground(new Color(138, 3, 3));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Serif", Font.PLAIN, 16));
    }

    private void setFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,110));
        labelPanel.add(successLabel);
        mainPanel.add(labelPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(doneButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    // button getter
    public JButton getDoneButton() {
        return doneButton;
    }

    // button listener
    public void addDoneButtonListener(ActionListener listener) {
        doneButton.addActionListener(listener);
    }
}
