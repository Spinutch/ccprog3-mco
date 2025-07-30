package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BattleView extends JFrame {

    private JTextArea battleLog;
    private JButton confirmButton;
    private JComboBox<String> player1MoveCombo;
    private JComboBox<String> player2MoveCombo;
    private JLabel player1Stats;
    private JLabel player2Stats;

    public BattleView() {
        setTitle("Battle Phase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Battle Log
        battleLog = new JTextArea();
        battleLog.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(battleLog);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Battle Log"));
        add(logScrollPane, BorderLayout.CENTER);

        // Player panels
        JPanel playersPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        playersPanel.setBorder(BorderFactory.createTitledBorder("Players"));

        // Player 1 panel
        JPanel p1Panel = new JPanel(new BorderLayout());
        player1Stats = new JLabel("Player 1 - HP: -- | EP: --");
        player1MoveCombo = new JComboBox<>();
        p1Panel.add(player1Stats, BorderLayout.NORTH);
        p1Panel.add(new JLabel("Select Move:"), BorderLayout.CENTER);
        p1Panel.add(player1MoveCombo, BorderLayout.SOUTH);
        playersPanel.add(p1Panel);

        // Player 2 panel
        JPanel p2Panel = new JPanel(new BorderLayout());
        player2Stats = new JLabel("Player 2 - HP: -- | EP: --");
        player2MoveCombo = new JComboBox<>();
        p2Panel.add(player2Stats, BorderLayout.NORTH);
        p2Panel.add(new JLabel("Select Move:"), BorderLayout.CENTER);
        p2Panel.add(player2MoveCombo, BorderLayout.SOUTH);
        playersPanel.add(p2Panel);

        add(playersPanel, BorderLayout.NORTH);

        // Confirm button
        confirmButton = new JButton("Confirm Turn");
        add(confirmButton, BorderLayout.SOUTH);
    }

    public void setPlayer1Stats(String statsText) {
        player1Stats.setText(statsText);
    }

    public void setPlayer2Stats(String statsText) {
        player2Stats.setText(statsText);
    }

    public void setPlayer1Moves(String[] moveNames) {
        player1MoveCombo.setModel(new DefaultComboBoxModel<>(moveNames));
    }

    public void setPlayer2Moves(String[] moveNames) {
        player2MoveCombo.setModel(new DefaultComboBoxModel<>(moveNames));
    }

    public String getPlayer1SelectedMove() {
        return (String) player1MoveCombo.getSelectedItem();
    }

    public String getPlayer2SelectedMove() {
        return (String) player2MoveCombo.getSelectedItem();
    }

    public void appendBattleLog(String text) {
        battleLog.append(text + "\n");
    }

    public void clearMoveSelection() {
        player1MoveCombo.setSelectedIndex(0);
        player2MoveCombo.setSelectedIndex(0);
    }

    public void addConfirmListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }
}
