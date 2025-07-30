package controller;

import model.Ability;
import model.Character;
import model.GameModel;
import view.BattleView;
import view.MainMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class BattleController {

    private GameModel model;
    private BattleView battleView;

    private Character player1;
    private Character player2;

    public BattleController(GameModel gameModel) {
        this.model = gameModel;
        this.player1 = gameModel.getPlayer1Character();
        this.player2 = gameModel.getPlayer2Character();

        this.battleView = new BattleView();
        initializeBattle();
    }

    private void initializeBattle() {
        // Set move dropdowns
        battleView.setPlayer1Moves(getAbilityNames(Arrays.asList(player1.getAbilities())));
        battleView.setPlayer2Moves(getAbilityNames(Arrays.asList(player2.getAbilities())));

        // Show initial stats
        updateStats();

        // Confirm button logic
        battleView.addConfirmListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTurn();
            }
        });

        battleView.setVisible(true);
    }

    private void updateStats() {
        battleView.setPlayer1Stats(player1.getName() + " - HP: " + player1.getHP() + " | EP: " + player1.getEP());
        battleView.setPlayer2Stats(player2.getName() + " - HP: " + player2.getHP() + " | EP: " + player2.getEP());
    }

    private void handleTurn() {

        Ability move1 = findAbilityByName(player1.getAbilities(), battleView.getPlayer1SelectedMove());
        Ability move2 = findAbilityByName(player2.getAbilities(), battleView.getPlayer2SelectedMove());

        if (move1 == null || move2 == null) {
            battleView.appendBattleLog("Error: One or both moves not found.\n");
            return;
        }
        StringBuilder log = new StringBuilder();



            boolean player1Valid = true;
            boolean player2Valid = true;

            // Player 1 turn
            if (player1.getEP() >= move1.getEpCost()) {
                player1.useEP(move1.getEpCost());
                log.append(player1.getName()).append(" used ").append(move1.getName()).append("\n");
                String p2DamageMessage = player2.takeDamage(move1.getDamage());
                log.append(p2DamageMessage).append("\n");
            } else {
                player1Valid = false;
                JOptionPane.showMessageDialog(null, "Pick a different ability, you don't have enough EP.");
                return; // STOP execution. Let the player choose again

            }

            // Player 2 turn
            if (player2.getEP() >= move2.getEpCost()) {
                player2.useEP(move2.getEpCost());
                log.append(player2.getName()).append(" used ").append(move2.getName()).append("\n");
                String p1DamageMessage = player1.takeDamage(move2.getDamage());
                log.append(p1DamageMessage).append("\n");
            } else {
                player2Valid = false;
                JOptionPane.showMessageDialog(null, "Pick a different ability, you don't have enough EP.");
                return; // STOP execution. Let the player choose again

            }

            // Only update stats and log if both had a valid turn or game must proceed
            if (player1Valid || player2Valid) {
                updateStats();
                battleView.appendBattleLog(log.toString());
                battleView.clearMoveSelection();
            }


        // Check if actual damage was possible before ending the game
        boolean move1DidDamage = move1.getDamage() > 0;
        boolean move2DidDamage = move2.getDamage() > 0;
        boolean eitherMoveCouldDamage = move1DidDamage || move2DidDamage;

        if ((player1.getHP() <= 0 || player2.getHP() <= 0) && eitherMoveCouldDamage) {
            declareWinner();
        }
    }

    private void declareWinner() {
        String winner;
        if (player1.getHP() <= 0 && player2.getHP() <= 0) {
            winner = "It's a draw!";
        } else if (player1.getHP() <= 0) {
            winner = player2.getName() + " wins!";
        } else {
            winner = player1.getName() + " wins!";
        }

        battleView.appendBattleLog("\n" + winner);

            String[] options = {"Rematch", "Character Management", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                battleView,
                winner + "\nWhat would you like to do next?",
                "Battle Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 0) {  // Rematch
                player1.resetStats();
                player2.resetStats();
                new BattleController(model);  // reuse same characters
                battleView.dispose();

            } else if (choice == 1) {  // Character Management
                // back to Player 1 menu
                MainMenuView menu = new MainMenuView(1);
                new MainMenuController(menu, model, 1);
                battleView.dispose();

            } else {
                System.exit(0);
            }

    }

    private String[] getAbilityNames(List<Ability> abilities) {
        return abilities.stream().map(Ability::getName).toArray(String[]::new);
    }

    private Ability findAbilityByName(Ability[] abilities, String selectedText) {
        String abilityName = selectedText.split(" - ")[0];  // Get name only

        for (Ability a : abilities) {
            if (a.getName().equals(abilityName)) {
                return a;
            }
        }
        return null;
    }



    public void showBattleView() {
        battleView.setVisible(true);
    }
}
