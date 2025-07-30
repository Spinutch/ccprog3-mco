package view;

import javax.swing.*;
import java.awt.*;

public class ActualEditCharacterView extends JFrame{

    private JLabel title;
    
    private JButton finishButton;
    private JButton detailsButton;
    private JButton ManageButton;
    private JButton EditAbilitiesButton;

    public ActualEditCharacterView() {
        super("Actual Edit Character");
        initComponents();
        setFrame();
    }

    private void initComponents() {
        title = new JLabel("Edit Chosen Character", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));

        finishButton = new JButton("Finish Editing");
        detailsButton = new JButton("View Details");
        ManageButton = new JButton("Manage Equipment");
        EditAbilitiesButton = new JButton("Edit Abilities");

        finishButton.setMargin(new Insets(20, 20, 20, 20));
        detailsButton.setMargin(new Insets(20, 20, 20, 20));
        ManageButton.setMargin(new Insets(20, 20, 20, 20));
        EditAbilitiesButton.setMargin(new Insets(20, 20, 20, 20));

        finishButton.setBackground(new Color(138, 3, 3));
        detailsButton.setBackground(new Color(138, 3, 3));
        ManageButton.setBackground(new Color(138, 3, 3));
        EditAbilitiesButton.setBackground(new Color(138, 3, 3));
        finishButton.setForeground(Color.WHITE);
        detailsButton.setForeground(Color.WHITE);
        ManageButton.setForeground(Color.WHITE);
        EditAbilitiesButton.setForeground(Color.WHITE);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(finishButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(ManageButton);
        buttonPanel.add(EditAbilitiesButton);

        add(title, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }



    private void setFrame() {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }



    
}
