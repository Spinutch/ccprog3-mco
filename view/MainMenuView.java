package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView extends JFrame {
    // Top labels
    private JLabel title;
    private JLabel subtitle;
    private JLabel prompt;

    // Buttons
    private JButton view;
    private JButton create;
    private JButton edit;
    private JButton delete;
    private JButton choose;

    public MainMenuView(int player) {
        super("Fatal Fantasy");
        initComponents();
        setCurrentPlayer(player);
        setFrame();
    }

    public void setCurrentPlayer(int player) {
        prompt.setText("Player " + player + ", manage your characters...");
    }

    private void initComponents() {
        // labels
        title = new JLabel("FATAL FANTASY : TACTICS", SwingConstants.CENTER);
        subtitle = new JLabel("Welcome to", SwingConstants.CENTER);
        prompt = new JLabel("", SwingConstants.CENTER);

        title.setFont(new Font("Serif", Font.BOLD, 32));
        subtitle.setFont(new Font("Serif", Font.PLAIN, 20));
        prompt.setFont(new Font("Serif", Font.PLAIN, 18));

        // buttons
        view = new JButton("View Characters");
        create = new JButton("Create Characters");
        edit = new JButton("Edit Character");
        delete = new JButton("Delete Character");
        choose = new JButton("Choose Character");

        // button margin
        view.setMargin(new Insets(20, 20, 20, 20));
        create.setMargin(new Insets(20, 20, 20, 20));
        edit.setMargin(new Insets(20, 20, 20, 20));
        delete.setMargin(new Insets(20, 20, 20, 20));
        choose.setMargin(new Insets(20, 20, 20, 20));

        //button color
        view.setBackground(new Color(138,3,3));
        create.setBackground(new Color(138,3,3));
        edit.setBackground(new Color(138,3,3));
        delete.setBackground(new Color(138,3,3));
        choose.setBackground(new Color(138,3,3));
        view.setForeground(Color.WHITE);
        create.setForeground(Color.WHITE);
        edit.setForeground(Color.WHITE);
        delete.setForeground(Color.WHITE);
        choose.setForeground(Color.WHITE);

        //button font
        view.setFont(new Font("Serif", Font.BOLD, 14));
        create.setFont(new Font("Serif", Font.BOLD, 14));
        edit.setFont(new Font("Serif", Font.BOLD, 14));
        delete.setFont(new Font("Serif", Font.BOLD, 14));
        choose.setFont(new Font("Serif", Font.BOLD, 14));
    }

    private void setFrame() {
        // Main panel with BorderLayout
        setLayout(new BorderLayout());

        // North: Title and subtitle
        JPanel titlePanel = new JPanel(new GridLayout(3, 1));
        titlePanel.add(subtitle);
        titlePanel.add(title);
        titlePanel.add(prompt);
        add(titlePanel, BorderLayout.NORTH);

        // Center: 5 menu buttons arranged in flow
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        buttonPanel.add(view);
        buttonPanel.add(create);
        buttonPanel.add(edit);
        buttonPanel.add(delete);
        buttonPanel.add(choose);
        add(buttonPanel, BorderLayout.CENTER);

        // Frame settings
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center
        setResizable(false);
        setVisible(true);
    }

    // Getters for buttons
    public JButton getViewButton() {
        return view;
    }   
    public JButton getCreateButton() {
        return create;
    }
    public JButton getEditButton() {
        return edit;
    }
    public JButton getDeleteButton() {
        return delete;
    }
    public JButton getChooseButton() {
        return choose;
    }   
    
    // Button action listeners
    public void addViewListener(ActionListener listener) {
        view.addActionListener(listener);
    }

    public void addCreateListener(ActionListener listener) {
        create.addActionListener(listener);
    }
    
    public void addEditListener(ActionListener listener) {
        edit.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        delete.addActionListener(listener);
    }

    public void addChooseListener(ActionListener listener) {
        choose.addActionListener(listener);
    }

    
}