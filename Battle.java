import java.util.*;

/**
 * The Battle class handles the core logic for executing a turn-based battle
 * between two Character objects. It manages the round progression,
 * move selection, energy and health updates, and determines the winner.
 * 
 * This class adheres to the game flow described in the MCO1 specifications.
 *
 * Battle Flow:
 *   - Initialize with two Character objects
 *   - Regenerate EP at the start of each round
 *   - Display move options, prompt for input, and execute abilities
 *   - Display outcome and check for a winner after each round
 * 
 */

public class Battle {
    private Character player1;
    private Character player2;
    private Scanner scanner;

    /**
     * Constructs a new Battle object with the specified characters.
     * Each character is reset to full HP and EP at the start of the battle.
     *
     * @param p1 the first character participating in the battle
     * @param p2 the second character participating in the battle
     */
    public Battle(Character p1, Character p2, Scanner sc) {
        this.player1 = p1;
        this.player2 = p2;
        this.scanner = sc;
    }
    /**
     * Starts the round-by-round battle between the two characters.
     * Handles move selection, ability execution, EP regeneration, and displays round summaries.
     */
    public void startBattle() {
        int round = 1;
        boolean battleOngoing = true;
        int epSpentP1;
        int epSpentP2;
        String moveNameP1;
        String moveNameP2;
        int moveP1, moveP2;

        // Characters begin each fight at max HP and EP
        player1.resetStats();
        player2.resetStats();

        while (battleOngoing) {
            System.out.println("\n======================== ROUND " + round + " ========================");

            // At the start of each round
            player1.setDefending(false);
            player2.setDefending(false);
            player1.setEvading(false);
            player2.setEvading(false);
            player1.setShielded(false);
            player2.setShielded(false);

            // 1. At the start of each round, both characters regenerate +5 EP (capped at
            // max)
            player1.recharge();
            player2.recharge();

            // 2. Display the round number, current HP, and current EP of both characters
            displayStats(round);

            /*
             * 3. Display Player 1’s available list of moves, including EP cost
             * 4. Prompt Player 1 to choose a move, re-prompting them if the choice is
             * invalid (e.g., invalid choice, insufficient EP)
             * 5. Repeat steps 3-4 for Player 2
             */
            while (true) {
                moveP1 = displayAndPromptMove(player1);
                Ability[] abilities1 = player1.getAbilities();
                if (moveP1 >= 1 && moveP1 <= abilities1.length
                        && player1.getEP() < abilities1[moveP1 - 1].getEpCost()) {
                    System.out.println(
                            "\n[Sorry, you do not have enough EP to use the " + abilities1[moveP1 - 1].getName() + "]");
                    continue;
                }
                if (moveP1 == abilities1.length + 1 && player1.getEP() < 5) {
                    System.out.println("[Sorry, you do not have enough EP to use Defend]");
                    continue;
                }
                break;
            }
            while (true) {
                moveP2 = displayAndPromptMove(player2);
                Ability[] abilities2 = player2.getAbilities();
                if (moveP2 >= 1 && moveP2 <= abilities2.length
                        && player2.getEP() < abilities2[moveP2 - 1].getEpCost()) {
                    System.out.println(
                            "\n[Sorry, you do not have enough EP to use the " + abilities2[moveP2 - 1].getName() + "]");
                    continue;
                }
                if (moveP2 == abilities2.length + 1 && player2.getEP() < 5) {
                    System.out.println("[Sorry, you do not have enough EP to use Defend]");
                    continue;
                }
                break;
            }

            boolean p1Defending = (moveP1 == player1.getAbilities().length + 1);
            boolean p2Defending = (moveP2 == player2.getAbilities().length + 1);

            player1.setDefending(p1Defending);
            player2.setDefending(p2Defending);

            // Store EP before moves
            int player1EPBefore = player1.getEP();
            int player2EPBefore = player2.getEP();

            setFlagsBeforeAttacks(player1, moveP1);
            setFlagsBeforeAttacks(player2, moveP2);
            // 6. Execute moves
            executeMove(player1, player2, moveP1);
            executeMove(player2, player1, moveP2);

            // Compute EP spent
            epSpentP1 = player1EPBefore - player1.getEP();
            epSpentP2 = player2EPBefore - player2.getEP();

            // 7. Display the round’s outcome (e.g., damage dealt, health changes, EP spent)

            if (moveP1 == player1.getAbilities().length + 1) {
                moveNameP1 = "Defend";
            } else if (moveP1 == player1.getAbilities().length + 2) {
                moveNameP1 = "Recharge";
            } else {
                moveNameP1 = player1.getAbilities()[moveP1 - 1].getName();
            }

            if (moveP2 == player2.getAbilities().length + 1) {
                moveNameP2 = "Defend";
            } else if (moveP2 == player2.getAbilities().length + 2) {
                moveNameP2 = "Recharge";
            } else {
                moveNameP2 = player2.getAbilities()[moveP2 - 1].getName();
            }

            displayRoundOutcome(player1, player2, moveNameP1, moveNameP2, epSpentP1, epSpentP2, round);

            // 8. Check for battle end
            if (isBattleOver()) {
                declareWinner();
                battleOngoing = false;
            } else {
                round++;
            }
        }
    }

    /**
     * DISPLAY METHODS
     */

    private void displayStats(int round) {
        System.out.println("                  [Player Statistics]");
        System.out.printf("            %-10s - HP: %-3d | EP: %-3d\n", player1.getName(), player1.getHP(),
                player1.getEP());
        System.out.printf("            %-10s - HP: %-3d | EP: %-3d\n", player2.getName(), player2.getHP(),
                player2.getEP());
    }

    private int displayAndPromptMove(Character player) {
        System.out.println("\n[" + player.getName() + "'s Available Moves]\n");
        Ability[] abilities = player.getAbilities();
        for (int i = 0; i < abilities.length; i++) {
            System.out.printf("%d. %s (EP: %d) - %s\n", i + 1, abilities[i].getName(), abilities[i].getEpCost(),
                    abilities[i].getDescription());
        }
        System.out.println((abilities.length + 1) + ". Defend (EP: 5) - Take half damage this round.");
        System.out.println((abilities.length + 2) + ". Recharge (EP: 0) - Do nothing and regain 5 EP.\n");
        System.out.print(player.getName() + ", What would you like to do: ");
        int moveChoice = getIntInput(scanner);
        return moveChoice;
    }

    private void displayRoundOutcome(Character player1, Character player2, String moveP1, String moveP2, int epSpentP1,
            int epSpentP2, int round) {
        System.out.println("\n-------------------- Round " + round + " Results --------------------\n");
        System.out.println(
                player1.getName() + " chose " + moveP1 + " while " + player2.getName() + " chose " + moveP2 + ".");
        System.out.println("\n                  [AFTER ROUND SUMMARY]");
        // Align output just like in displayStats
        System.out.printf("      %-10s - HP: %-3d | EP: %-3d (EP spent: %d)\n",
                player1.getName(), player1.getHP(), player1.getEP(), epSpentP1);
        System.out.printf("      %-10s - HP: %-3d | EP: %-3d (EP spent: %d)\n",
                player2.getName(), player2.getHP(), player2.getEP(), epSpentP2);
    }

    /**
     * Checks if either or both characters have 0 HP. If so, prints the result
     * and returns true to indicate that the battle is over.
     *
     * @return true if the battle has ended; false otherwise
     */

    private boolean isBattleOver() {
        return player1.getHP() <= 0 || player2.getHP() <= 0;
    }

    /**
     * Determines and prints the result of the battle based on the remaining HP of both players.
     * 
     *   - If both players have 0 or less HP, the result is a draw.
     *   - If only one player has HP above 0, that player is declared the winner.
     */
    private void declareWinner() {
        System.out.println("\n---------------------------------------------------------");
        if (player1.getHP() <= 0 && player2.getHP() <= 0) {
            System.out.println("[Both players have lost all their HP! It's a draw!]");
        } else if (player1.getHP() <= 0) {
            System.out.println("                   [" + player2.getName() + " wins!]");
        } else if (player2.getHP() <= 0) {
            System.out.println("                   [" + player1.getName() + " wins!]");
        }
        System.out.println("---------------------------------------------------------\n");
    }

    /**
     * Executes the selected move for the given character. This includes:
     * 
     *   - Reducing EP based on the selected ability’s cost.
     *   - Applying healing, shielding, or damage depending on the ability type.
     *   - Handling special move effects such as defending or recharging.
     * 
     *
     * @param currentPlayer the character performing the move
     * @param target the opposing character receiving the effect (if applicable)
     * @param moveChoice the name of the move to be executed
     * 
     */

    private void executeMove(Character currentPlayer, Character target, int moveChoice) {
        Ability[] abilities = currentPlayer.getAbilities();
        int numAbilities = abilities.length;
        boolean moveExecuted = false;

        // Set flags for special abilities or defend    
        setFlagsBeforeAttacks(currentPlayer, moveChoice);

        while (!moveExecuted) {
            if (moveChoice >= 1 && moveChoice <= numAbilities) {
                Ability userInput = abilities[moveChoice - 1];
                if (currentPlayer.getEP() >= userInput.getEpCost()) {
                    currentPlayer.useEP(userInput.getEpCost());

                    if (userInput.isSpecialAbility()) {
                        switch (userInput.getName()) {
                            case "Arcane Shield":
                            case "Ironclad Defense":
                                System.out.println(
                                        currentPlayer.getName() + " is shielded and will take no damage this round!");
                                break;
                            case "Smoke Bomb":
                                System.out.println(currentPlayer.getName()
                                        + " used Smoke Bomb and may evade attacks this round! (50% chance)");
                                break;
                            case "Sneak Attack":
                                target.takeDamage(userInput.getDamage());
                                System.out.println(currentPlayer.getName() + " used Sneak Attack, can evade and deal "
                                        + userInput.getDamage() + " damage!");
                                break;
                        }
                    } else {
                        // ALL THE NORMAL ABILITIES
                        if (userInput.getDamage() > 0) {
                            target.takeDamage(userInput.getDamage());
                            System.out.println(currentPlayer.getName() + " used " + userInput.getName() +
                                    " and can give " + userInput.getDamage() + " damage to " + target.getName() + "!");
                        }
                        if (userInput.getRestore() > 0) {
                            if ("HP".equals(userInput.getRestoreType())) {
                                currentPlayer.heal(userInput.getRestore());
                                System.out.println(
                                        currentPlayer.getName() + " healed for " + userInput.getRestore() + " HP!");
                            } else {
                                currentPlayer.restore("EP", userInput.getRestore());
                                System.out.println(
                                        currentPlayer.getName() + " restored " + userInput.getRestore() + " EP!");
                            }
                        }
                    }
                    moveExecuted = true;
                } else {
                    System.out.println("\n[Sorry, you do not have enough EP to use the " + userInput.getName() + "]");
                    moveChoice = displayAndPromptMove(currentPlayer);
                }
            } else if (moveChoice == numAbilities + 1) {
                // DEFEND
                if (currentPlayer.getEP() >= 5) {
                    currentPlayer.useEP(5);
                    currentPlayer.setDefending(true);
                    System.out.println(currentPlayer.getName() + " is defending and will take half damage this round!");
                    moveExecuted = true;
                } else {
                    System.out.println("[Sorry, you do not have enough EP to use Defend]");
                    moveChoice = displayAndPromptMove(currentPlayer);
                }
            } else if (moveChoice == numAbilities + 2) {
                // RECHARGE
                currentPlayer.recharge();
                System.out.println("[" + currentPlayer.getName() + " recharged and regained 5 EP!]");
                moveExecuted = true;
            } else {
                System.out.println("\n---------------------------------------------------------");
                System.out.println("          Invalid move choice! Please try again.");
                System.out.println("---------------------------------------------------------\n");
                moveChoice = displayAndPromptMove(currentPlayer);
            }
        }
    }

    /**
     * Sets pre-attack flags for each character based on their selected move. 
     * These flags influence how damage or effects are applied
     * during the round's execution.
     *
     * @param player the character whose move is being analyzed
     * @param moveChoice    the move selected by the character
     */

    private void setFlagsBeforeAttacks(Character player, int moveChoice) {
        Ability[] abilities = player.getAbilities();
        int numAbilities = abilities.length;
        if (moveChoice >= 1 && moveChoice <= numAbilities) {
            Ability userInput = abilities[moveChoice - 1];
            if (userInput.isSpecialAbility()) {
                switch (userInput.getName()) {
                    case "Arcane Shield":
                    case "Ironclad Defense":
                        player.setShielded(true);
                        break;
                    case "Smoke Bomb":
                    case "Sneak Attack":
                        player.setEvading(true);
                        break;
                }
            }
        } else if (moveChoice == numAbilities + 1) {
            // Defend
            player.setDefending(true);
        }
    }

    /**
     * Prompts the user to enter an integer using the provided Scanner.
     * If the input is not a valid integer, the method displays an error message
     * and continues prompting until a valid integer is entered.
     *
     * @param sc the Scanner object used to read user input
     * @return the valid integer entered by the user
     */

    public static int getIntInput(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("\n---------------------------------------------------------");
            System.out.println("        Invalid input! Please enter an integer: ");
            System.out.println("---------------------------------------------------------\n");
            sc.next();
        }
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }
}