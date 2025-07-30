package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ActualEditCharacterView extends JFrame {

    private JLabel title;
    
    private JButton finishButton;
    private JButton detailsButton;
    private JButton ManageButton;
    private JButton EditAbilitiesButton;

    public ActualEditCharacterView() {
        super("Edit Character");
        initComponents();
        setFrame();
    }

    private void initComponents() {
        title = new JLabel("Edit Chosen Character", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));

        finishButton = new JButton("Finish Editing"); // Changed text
        detailsButton = new JButton("View Details");
        ManageButton = new JButton("Manage Equipment");
        EditAbilitiesButton = new JButton("Edit Abilities");

        finishButton.setBackground(new Color(138, 3, 3));
        finishButton.setForeground(Color.WHITE);
        finishButton.setFont(new Font("Serif", Font.PLAIN, 12));
        detailsButton.setMargin(new Insets(20, 20, 20, 20));
        ManageButton.setMargin(new Insets(20, 20, 20, 20));
        EditAbilitiesButton.setMargin(new Insets(20, 20, 20, 20));

        detailsButton.setBackground(new Color(138, 3, 3));
        ManageButton.setBackground(new Color(138, 3, 3));
        EditAbilitiesButton.setBackground(new Color(138, 3, 3));
        detailsButton.setForeground(Color.WHITE);
        ManageButton.setForeground(Color.WHITE);
        EditAbilitiesButton.setForeground(Color.WHITE);
        detailsButton.setFont(new Font("Serif", Font.BOLD, 16));
        ManageButton.setFont(new Font("Serif", Font.BOLD, 16));
        EditAbilitiesButton.setFont(new Font("Serif", Font.BOLD, 16));
        detailsButton.setMargin(new Insets(20, 20, 20, 20));
        ManageButton.setMargin(new Insets(20, 20, 20, 20));
        EditAbilitiesButton.setMargin(new Insets(20, 20, 20, 20));
    }

    private void setFrame() {

        setLayout(new BorderLayout());

        //North panel for title
        JPanel titlePanel = new JPanel();
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        
        buttonPanel.add(EditAbilitiesButton);
        buttonPanel.add(ManageButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(finishButton);

        add(buttonPanel, BorderLayout.CENTER);
        
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void setEditAbilitiesListener(ActionListener listener) {
        EditAbilitiesButton.addActionListener(listener);
    }

    public void setManageMagicItemsListener(ActionListener listener) {
        ManageButton.addActionListener(listener);
    }

    public void setViewDetailsListener(ActionListener listener) {
        detailsButton.addActionListener(listener);
    }

    public void setBackListener(ActionListener listener) {
        finishButton.addActionListener(listener);
    }

    public JButton getFinishButton() {
        return finishButton;
    }

    public JButton getDetailsButton() {
        return detailsButton;
    }

    public JButton getManageButton() {
        return ManageButton;
    }

    public JButton getEditAbilitiesButton() {
        return EditAbilitiesButton;
    }    
}
