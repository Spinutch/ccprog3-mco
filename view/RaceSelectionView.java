package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RaceSelectionView extends JFrame {
    
    private JLabel title;
    
    private JLabel human;
    private JLabel elf;
    private JLabel dwarf;
    private JLabel gnome;

    private JTextArea humanDescription;
    private JTextArea elfDescription;
    private JTextArea dwarfDescription;
    private JTextArea gnomeDescription;

    private JButton humanButton;
    private JButton elfButton;  
    private JButton dwarfButton;
    private JButton gnomeButton;

   
    public RaceSelectionView() {
        super("Create Character");
        initComponents();
        setFrame();
    } 

    private void initComponents() {
        title = new JLabel("Select a Race:", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 23));

        human = new JLabel("Human", SwingConstants.LEFT);
        human.setFont(new Font("Serif", Font.PLAIN, 20));
        elf = new JLabel("Elf", SwingConstants.LEFT);   
        elf.setFont(new Font("Serif", Font.PLAIN, 20));
        dwarf = new JLabel("Dwarf", SwingConstants.LEFT);
        dwarf.setFont(new Font("Serif", Font.PLAIN, 20));
        gnome = new JLabel("Gnome", SwingConstants.LEFT);
        gnome.setFont(new Font("Serif", Font.PLAIN, 20));

        
        humanDescription = createWrappedTextArea("Adaptable and resilient, humans possess a balanced set of attributes. +15 to max HP, +5 to max EP");
        elfDescription = createWrappedTextArea("Graceful and naturally attuned to arcane energies, elves excel in precise actions and magical prowess. +15 to max EP");
        dwarfDescription = createWrappedTextArea("Stocky and tough, dwarves are known for their incredible endurance and steadfastness. +30 to max HP");
        gnomeDescription = createWrappedTextArea("Clever and resourceful, gnomes have a knack for finding hidden opportunities or leveraging unusual tricks. +1 additional ability slot");

        humanButton = new JButton("Human");
        humanButton.setBackground(new Color(138, 3, 3));
        humanButton.setForeground(Color.WHITE);
        humanButton.setFont(new Font("Serif", Font.PLAIN, 16));
        humanButton.setToolTipText("Select Human race");

        elfButton = new JButton("Elf");
        elfButton.setBackground(new Color(138, 3, 3));
        elfButton.setForeground(Color.WHITE);
        elfButton.setFont(new Font("Serif", Font.PLAIN, 16));
        elfButton.setToolTipText("Select Elf race");

        dwarfButton = new JButton("Dwarf");
        dwarfButton.setBackground(new Color(138, 3, 3));
        dwarfButton.setForeground(Color.WHITE);
        dwarfButton.setFont(new Font("Serif", Font.PLAIN, 16));
        dwarfButton.setToolTipText("Select Dwarf race");

        gnomeButton = new JButton("Gnome");
        gnomeButton.setBackground(new Color(138, 3, 3));
        gnomeButton.setForeground(Color.WHITE);
        gnomeButton.setFont(new Font("Serif", Font.PLAIN, 16));
        gnomeButton.setToolTipText("Select Gnome race");

    }

    private void setFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel humanPanel = createRacePanel("Human", humanDescription);
        JPanel elfPanel = createRacePanel("Elf", elfDescription);
        JPanel dwarfPanel = createRacePanel("Dwarf", dwarfDescription); 
        JPanel gnomePanel = createRacePanel("Gnome", gnomeDescription);


        JPanel racePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        racePanel.add(humanPanel);
        racePanel.add(elfPanel);
        racePanel.add(dwarfPanel);
        racePanel.add(gnomePanel);
        mainPanel.add(racePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(humanButton);
        buttonPanel.add(elfButton);
        buttonPanel.add(dwarfButton);
        buttonPanel.add(gnomeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        

        add(mainPanel);

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


    }

        private JTextArea createWrappedTextArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setOpaque(false);
        area.setEditable(false);
        area.setFocusable(false);
        area.setFont(new Font("Serif", Font.PLAIN, 15));
        return area;
    }
    
        private JPanel createRacePanel(String title, JTextArea description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        panel.add(Box.createVerticalStrut(5));
        panel.add(description);
        panel.add(Box.createVerticalStrut(5));

        return panel;
    }


    // button getters
    public JButton getHumanButton() {
        return humanButton;
    }
    public JButton getElfButton() {
        return elfButton;
    }
    public JButton getDwarfButton() {
        return dwarfButton;
    }
    public JButton getGnomeButton() {
        return gnomeButton;
    }

    // button listeners
    public void addHumanButtonListener(ActionListener listener) {
        humanButton.addActionListener(listener);
    }

    public void addElfButtonListener(ActionListener listener) {
        elfButton.addActionListener(listener);
    }

    public void addDwarfButtonListener(ActionListener listener) {
        dwarfButton.addActionListener(listener);
    }

    public void addGnomeButtonListener(ActionListener listener) {
        gnomeButton.addActionListener(listener);
    }


}