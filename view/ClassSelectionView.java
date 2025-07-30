package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClassSelectionView extends JFrame {
    
    private JLabel title;
    private JButton warriorButton;
    private JButton mageButton;
    private JButton rogueButton;

    public ClassSelectionView(){
        super("Create Character");
        initComponents();
        setFrame();
    }

    
    private void initComponents() {
        title = new JLabel("Choose a Class:", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));

        warriorButton = new JButton("Warrior");
        warriorButton.setBackground(new Color(138, 3, 3));  
        warriorButton.setForeground(Color.WHITE);
        warriorButton.setFont(new Font("Serif", Font.PLAIN, 24));
        warriorButton.setMargin(new Insets(30, 30, 30, 30));

        mageButton = new JButton("Mage");
        mageButton.setBackground(new Color(138, 3, 3));
        mageButton.setForeground(Color.WHITE);
        mageButton.setFont(new Font("Serif", Font.PLAIN, 24));
        mageButton.setMargin(new Insets(30, 30, 30, 30));

        rogueButton = new JButton("Rogue");
        rogueButton.setBackground(new Color(138, 3, 3));
        rogueButton.setForeground(Color.WHITE);
        rogueButton.setFont(new Font("Serif", Font.PLAIN, 24));
        rogueButton.setMargin(new Insets(30, 30, 30, 30));

    }

    public void setFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2,1));

        JPanel topButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));

        topButtonsPanel.add(warriorButton);
        topButtonsPanel.add(mageButton);

        buttonPanel.add(topButtonsPanel);

        JPanel bottomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomButtonsPanel.add(rogueButton);
        buttonPanel.add(bottomButtonsPanel);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // button getters
    public JButton getWarriorButton() {
        return warriorButton;
    }   

    public JButton getMageButton() {
        return mageButton;
    }

    public JButton getRogueButton() {
        return rogueButton;
    }

    //button listeners
    public void addWarriorButtonListener(ActionListener listener) {
        warriorButton.addActionListener(listener);
    }

    public void addMageButtonListener(ActionListener listener) {
        mageButton.addActionListener(listener);
    }

    public void addRogueButtonListener(ActionListener listener) {
        rogueButton.addActionListener(listener);
    }


    
}
