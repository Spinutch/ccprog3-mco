import java.util.*;

/**
 * The Main class serves as the entry point of the Fatal Fantasy: Tactics program.
 * It handles the overall program flow, including user interaction, character management,
 * and initiating battles between characters.
 *
 * This class follows the functional and structural requirements outlined in the MCO1 specifications.
 *
 * Main Program Flow:
 *   - Display main menu options to the user
 *   - Allow character creation, viewing, editing, and deletion
 *   - Prompt players to select characters and begin a battle
 *   - Delegate battle execution to the Battle class
 *   - Repeat or exit based on user input
 */

public class Main {
    public static Character selectedCharacterP1;
    public static Character selectedCharacterP2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Character[] charactersP1 = new Character[6];
        Character[] charactersP2 = new Character[6];

        // PLAYER 1 CHARACTER MANAGEMENT
        System.out.println("\n=========================================================");
        System.out.println("\n                    HELLO PLAYER 1!");
        System.out.println("\n          [WELCOME TO FATAL FANTASY: TACTICS]");
        characterManagement(sc, charactersP1, charactersP2, "Player 1");

        // PLAYER 2 CHARACTER MANAGEMENT
        System.out.println("\n=========================================================");
        System.out.println("\n                    HELLO PLAYER 2!");
        System.out.println("\n          [WELCOME TO FATAL FANTASY: TACTICS]");
        characterManagement(sc, charactersP2, charactersP1, "Player 2");

        // START THE BATTLE CLASS
        System.out.println("\nBoth players have finished character management. The battle will now begin!");

        boolean playingGame = true;
        while (playingGame) {
            Battle battle = new Battle(selectedCharacterP1, selectedCharacterP2, sc);
            do {
                battle.startBattle();
            } while (rematch(sc));
            
            // Ask what to do next
            System.out.println("\n=========================================================");
            System.out.println("What would you like to do next?");
            System.out.println("1 - Continue with same characters");
            System.out.println("2 - Return to character management");
            System.out.println("3 - Exit game");
            System.out.print("Choose an option: ");
            int nextAction = getIntInput(sc);
            
            switch (nextAction) {
                case 1: // Continue with same characters
                    System.out.println("\nContinuing with the same characters. Starting new battle!");
                    break;
                case 2: // Return to character management
                    System.out.println("\n=========================================================");
                    System.out.println("           Returning to character management");
                    System.out.println("=========================================================\n");
                    
                    // Player 1 character management (preserving existing characters)
                    characterManagement(sc, charactersP1, charactersP2, "Player 1");
                    
                    // Player 2 character management (preserving existing characters)
                    characterManagement(sc, charactersP2, charactersP1, "Player 2");
                    
                    System.out.println("\nBoth players have finished character management. The battle will now begin!");
                    break;
                case 3: // Exit game
                    playingGame = false;
                    System.out.println("\nThank you for playing Fatal Fantasy: Tactics!");
                    break;
                default:
                    System.out.println("Invalid option. Continuing with same characters.");
                    break;
            }
        }
        sc.close();
    }

    /**
     * This method handles 2.1.1 to 2.1.2 sections of the MCO specs. This method is
     * mainly in charge of the the character management for a player.
     * It gives the player options to view, create, edit, or delete characters.
     * 
     * @param sc              The Scanner object used to scan the input of the
     *                        players.
     * @param characters      The array of characters that the player creates
     * @param otherCharacters The array of characters that the opposing player
     *                        creates [Player 2]
     * @param playerName      The name of the player
     * @return The total number of characters created by the player given the
     *         limitations of 6 characters.
     */
    private static int characterManagement(Scanner sc, Character[] characters, Character[] otherCharacters,
            String playerName) {
        // Count existing characters
        int totalCharacters = 0;
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] != null) {
                totalCharacters++;
            }
        }
        
        int characterSelect;
        ArrayList<Ability> setAbilities = new ArrayList<>();

        boolean characterChosenForBattle = false;
        do {
            displayCharacterOptions();
            System.out.print(playerName + " - What would you like to do: ");
            characterSelect = getIntInput(sc);
            System.out.println();

            switch (characterSelect) {
                case 1: // VIEW CHARACTERS
                    viewCharacters(characters, totalCharacters, sc);
                    // No break needed to exit loop; just let it continue
                    break;
                case 2: // CREATE CHARACTER
                    if (totalCharacters < 6) {
                        System.out.println("---------------------------------------------------------");
                        System.out.print("\n                   [CHARACTER CREATION]\n");
                        String name;
                        boolean uniqueName;
                        do {
                            System.out.print("\nEnter the name of your character: ");
                            name = sc.nextLine().trim();
                            uniqueName = true;
                            // Check current player's characters
                            for (int i = 0; i < totalCharacters; i++) {
                                if (characters[i] != null && characters[i].getName().equalsIgnoreCase(name)) {
                                    uniqueName = false;
                                }
                            }
                            // Check other player's characters
                            for (int i = 0; i < otherCharacters.length; i++) {
                                if (otherCharacters[i] != null && otherCharacters[i].getName().equalsIgnoreCase(name)) {
                                    uniqueName = false;
                                }
                            }
                            if (!uniqueName) {
                                System.out.println("\n---------------------------------------------------------");
                                System.out.println("    That name is already taken! Please choose another.");
                                System.out.println("---------------------------------------------------------");
                            }
                        } while (!uniqueName);

                        // RACE SELECTION
                        Race selectedRace = null;
                        boolean checkRace = false;
                        while (!checkRace) {
                            System.out.println("\n---------------------------------------------------------");
                            System.out.println("                 [RACE SELECTION]");
                            System.out.println("---------------------------------------------------------");
                            System.out.println("Choose a race for your character:\n");
                            Race[] races = Race.show_race_array();
                            for (int i = 0; i < races.length; i++) {
                                Race race = races[i];
                                System.out.printf("- %s\n", race.getName());
                                System.out.println("   " + race.getDescription());
                                String bonus = "";
                                if (race.getHpBonus() > 0) {
                                    bonus += "+ " + race.getHpBonus() + " max HP";
                                }
                                if (race.getEpBonus() > 0) {
                                    if (!bonus.isEmpty()) bonus += ", ";
                                    bonus += "+ " + race.getEpBonus() + " max EP";
                                }
                                if (race.hasExtraAbilitySlot()) { // for the gnome race // SPECIAL
                                    if (!bonus.isEmpty()) bonus += ", ";
                                    bonus += "+1 additional ability slot (choice from ANY class)";
                                }
                                System.out.println("   Bonus: " + bonus);
                                System.out.println("---------------------------------------------------------");
                            }
                            System.out.print("Type the name of your chosen race: ");
                            String raceInput = sc.nextLine().trim();
                            for (int i = 0; i < races.length; i++) {
                                if (races[i].getName().equalsIgnoreCase(raceInput)) {
                                    selectedRace = races[i];
                                    checkRace = true;
                                    System.out.println("\n[You have chosen the " + selectedRace.getName() + "]");
                                    break;
                                }
                            }
                            if (!checkRace) {
                                System.out.println("\n---------------------------------------------------------");
                                System.out.println("      Invalid choice! Please type a valid race name.");
                                System.out.println("---------------------------------------------------------");
                            }
                        }

                        String characterClass = "";
                        boolean checkClass = false;
                        while (!checkClass) {
                            System.out.print("\nChoose a character class (Mage, Rogue, Warrior): ");
                            characterClass = sc.nextLine().trim();
                            String classLower = characterClass.toLowerCase();
                            if (classLower.equals("mage") || classLower.equals("rogue")
                                    || classLower.equals("warrior")) {
                                characterClass = classLower.substring(0, 1).toUpperCase() + classLower.substring(1);
                                checkClass = true;
                            } else {
                                System.out.println("\n---------------------------------------------------------");
                                System.out.println(" Invalid! Please choose between Mage, Rogue, or Warrior: ");
                                System.out.println("---------------------------------------------------------\n");
                            }
                        }

                        ArrayList<Ability> availableAbilities = AllAbilities.getAbilitiesByClass(characterClass);
                        int abilitySlots = selectedRace.hasExtraAbilitySlot() ? 4 : 3;
                        System.out.println("\n---------------------------------------------------------");
                        if (selectedRace.hasExtraAbilitySlot()) {
                            System.out.println("Choose 3 abilities from your class, and 1 from ANY class (Gnome bonus):");
                        } else {
                            System.out.println("Choose 3 abilities for your character:");
                        }
                        for (int i = 0; i < availableAbilities.size(); i++) {
                            Ability ability = availableAbilities.get(i);
                            System.out.printf("%d. %s (EP: %d) - %s\n", i + 1, ability.getName(), ability.getEpCost(), ability.getDescription());
                        }
                        System.out.println("---------------------------------------------------------");
                        setAbilities.clear();
                        // For Gnome: 3 from class and then 1 from any class
                        if (selectedRace.hasExtraAbilitySlot()) {
                            // Pick 3 classes first based on the class
                            String[] numbering = {"first", "second", "third"};
                            for (int i = 0; i < 3; i++) {
                                boolean validChoice = false;
                                System.out.println();
                                String ctr = numbering[i];
                                while (!validChoice) {
                                    System.out.print("Choose your " + ctr + " ability: ");
                                    int abilityChoice = getIntInput(sc);
                                    if (abilityChoice >= 1 && abilityChoice <= availableAbilities.size()) {
                                        Ability chosen = availableAbilities.get(abilityChoice - 1);
                                        if (!setAbilities.contains(chosen)) {
                                            setAbilities.add(chosen);
                                            validChoice = true;
                                        } else {
                                            System.out.println("[You already picked that ability. Choose another.]");
                                        }
                                    } else {
                                        System.out.println("\n---------------------------------------------------------");
                                        System.out.println("          Invalid input! Please try again.");
                                        System.out.println("---------------------------------------------------------\n");
                                    }
                                }
                            }
                            // Pick 1 from any class // SPECIAL GNOME BONUS
                            ArrayList<Ability> allAbilities = AllAbilities.getAllAbilities();
                            System.out.println("\n[GNOME BONUS] Choose 1 additional ability from ANY class:");
                            for (int i = 0; i < allAbilities.size(); i++) {
                                Ability ability = allAbilities.get(i);
                                System.out.printf("%d. %s (Class: %s, EP: %d) - %s\n", i + 1, ability.getName(), ability.getDescription().contains("arcane") ? "Mage" : ability.getDescription().contains("fury") ? "Warrior" : "Rogue", ability.getEpCost(), ability.getDescription());
                            }
                            boolean validChoice = false;
                            while (!validChoice) {
                                System.out.print("Choose your bonus ability: ");
                                int bonusChoice = getIntInput(sc);
                                if (bonusChoice >= 1 && bonusChoice <= allAbilities.size()) {
                                    Ability chosen = allAbilities.get(bonusChoice - 1);
                                    if (!setAbilities.contains(chosen)) {
                                        setAbilities.add(chosen);
                                        validChoice = true;
                                    } else {
                                        System.out.println("[You already picked that ability. Choose another.]");
                                    }
                                } else {
                                    System.out.println("\n---------------------------------------------------------");
                                    System.out.println("          Invalid input! Please try again.");
                                    System.out.println("---------------------------------------------------------\n");
                                }
                            }
                        } else {
                            // any race other than the gnome: pick 3 from class
                            String[] numbering = {"first", "second", "third"};
                            for (int i = 0; i < 3; i++) {
                                boolean validChoice = false;
                                System.out.println();
                                String ctr = numbering[i];
                                while (!validChoice) {
                                    System.out.print("Choose your " + ctr + " ability: ");
                                    int abilityChoice = getIntInput(sc);
                                    if (abilityChoice >= 1 && abilityChoice <= availableAbilities.size()) {
                                        Ability chosen = availableAbilities.get(abilityChoice - 1);
                                        if (!setAbilities.contains(chosen)) {
                                            setAbilities.add(chosen);
                                            validChoice = true;
                                        } else {
                                            System.out.println("[You already picked that ability. Choose a different one.]");
                                        }
                                    } else {
                                        System.out.println("\n---------------------------------------------------------");
                                        System.out.println("          Invalid input! Please try again.");
                                        System.out.println("---------------------------------------------------------\n");
                                    }
                                }
                            }
                        }

                        characters[totalCharacters] = new Character(name, selectedRace, characterClass, setAbilities.toArray(new Ability[0]));
                        totalCharacters++;
                        System.out.println("\n---------------------------------------------------------");
                        System.out.println("            Character created successfully!");
                        System.out.println("---------------------------------------------------------");
                    } else {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("    You can only create up to 6 characters.");
                        System.out.println("---------------------------------------------------------");
                    }
                    break;
                case 3: // EDIT CHARACTER
                    if (totalCharacters == 0) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("          Please create your characters first!");
                        System.out.println("---------------------------------------------------------");
                        break; // Exit the switch case
                    }
                    listCharacters(characters, totalCharacters);
                    System.out.print("\nEnter the number of the character you want to edit: ");
                    int indexToEdit = getIntInput(sc) - 1;

                    if (indexToEdit >= 0 && indexToEdit < totalCharacters) {
                        Character characterToEdit = characters[indexToEdit];
                        
                        // Edit submenu
                        boolean editingDone = false;
                        while (!editingDone) {
                            System.out.println("\n---------------------------------------------------------");
                            System.out.println("            [EDITING " + characterToEdit.getName().toUpperCase() + "]");
                            System.out.println("---------------------------------------------------------");
                            System.out.println("1 - Edit Abilities");
                            System.out.println("2 - Manage Magic Items");
                            System.out.println("3 - View Character Details");
                            System.out.println("4 - Finish Editing");
                            System.out.print("\nWhat would you like to edit: ");
                            int editChoice = getIntInput(sc);
                            
                            switch (editChoice) {
                                case 1: // Edit Abilities
                                    editCharacterAbilities(sc, characterToEdit);
                                    break;
                                case 2: // Manage Magic Items
                                    manageMagicItems(sc, characterToEdit);
                                    break;
                                case 3: // View Character Details
                                    characterToEdit.displayCharacter();
                                    break;
                                case 4: // Finish Editing
                                    editingDone = true;
                                    System.out.println("\n[Finished editing " + characterToEdit.getName() + "]");
                                    break;
                                default:
                                    System.out.println("---------------------------------------------------------");
                                    System.out.println("          Invalid option! Please try again.");
                                    System.out.println("---------------------------------------------------------");
                            }
                        }

                    } else {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("          Invalid input! Please try again.");
                        System.out.println("---------------------------------------------------------");
                    }
                    break;
                case 4: // DELETE CHARACTER
                    listCharacters(characters, totalCharacters);
                    System.out.print("\nEnter the index of the character to delete: ");
                    int characterToDelete = getIntInput(sc) - 1;
                    if (characterToDelete >= 0 && characterToDelete < totalCharacters) {
                        characters[characterToDelete].deleteCharacter();
                        for (int i = characterToDelete; i < totalCharacters - 1; i++) {
                            characters[i] = characters[i + 1];
                        }
                        characters[totalCharacters - 1] = null;
                        totalCharacters--;
                        listCharacters(characters, totalCharacters);
                    } else {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("          Invalid input! Please try again.");
                        System.out.println("---------------------------------------------------------");
                    }
                    break;
                case 5: // CHOOSE CHARACTER FOR BATTLE
                    if (totalCharacters == 0) {
                        System.out.println("---------------------------------------------------------");
                        System.out.println("          Please create your characters first!");
                        System.out.println("---------------------------------------------------------");
                    } else {
                        boolean exitFlag = false;
                        while (!exitFlag) {
                            listCharacters(characters, totalCharacters);
                            System.out.print(
                                    "\nChoose a character to battle with or input '0' if you would like to go back to Character Management: ");
                            int characterChoice = getIntInput(sc);

                            if (characterChoice == 0) {
                                exitFlag = true;
                            } else if (characterChoice >= 1 && characterChoice <= totalCharacters) {
                                if (playerName.equals("Player 1")) {
                                    Main.selectedCharacterP1 = characters[characterChoice - 1];
                                    System.out.println("\n[You have selected " + Main.selectedCharacterP1.getName()
                                            + ". Get ready for battle!]");
                                } else if (playerName.equals("Player 2")) {
                                    Main.selectedCharacterP2 = characters[characterChoice - 1];
                                    System.out.println("\n[You have selected " + Main.selectedCharacterP2.getName()
                                            + ". Get ready for battle!]");
                                }
                                characterChosenForBattle = true;
                                exitFlag = true;
                            } else {
                                System.out.println("---------------------------------------------------------");
                                System.out.println("           Invalid choice! Please try again.");
                                System.out.println("---------------------------------------------------------");
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("---------------------------------------------------------");
                    System.out.println("          Invalid option! Please try again.");
                    System.out.println("---------------------------------------------------------");
            }
        } while (!characterChosenForBattle);

        return totalCharacters;
    }

    /**
     * This helper method that is used to get an integer input from the players.
     * It reads the next integer from the Scanner and clears the newline character
     * to avoid next line errors
     *
     * @param sc The Scanner object used to scan the input of the
     *           players.
     * @return The integer input from the user only.
     */
    private static int getIntInput(Scanner sc) {
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public static void displayCharacterOptions() {
        System.out.println("\n[Character Management Options]\n");
        System.out.println("1 - View Characters");
        System.out.println("2 - Create Character");
        System.out.println("3 - Edit Character");
        System.out.println("4 - Delete Character");
        System.out.println("5 - Choose Character for Battle\n");
    }

    /**
     * This method displays the characters of a player and allows them to view
     * detailed stats of each character based on the player's choice.
     *
     * @param characters      The array of characters that the player creates
     * @param totalCharacters The total number of characters created by the player.
     * @param sc              The Scanner object used to scan the input of the
     *                        players.
     */
    private static void viewCharacters(Character[] characters, int totalCharacters, Scanner sc) {
        if (totalCharacters == 0) {
            System.out.println("---------------------------------------------------------");
            System.out.println("          Please create your characters first!");
            System.out.println("---------------------------------------------------------");
        } else if (totalCharacters == 1) { // will show detailed view immediately
            characters[0].displayCharacter();
        } else {
            boolean exitFlag = false;
            while (!exitFlag) {
                System.out.println("---------------------------------------------------------");
                System.out.println("\nList of Characters:");
                for (int i = 0; i < totalCharacters; i++) {
                    System.out.println(
                            (i + 1) + " - " + characters[i].getName() + " (" + characters[i].getCharacterClass() + ")");
                }
                // OPTION TO VIEW CHARACTER DETAILS
                System.out.print("\nChoose a character to view their stats or input '0' if you would like to exit: ");
                int characterChoice = getIntInput(sc);

                if (characterChoice == 0) {
                    exitFlag = true;
                } else if (characterChoice >= 1 && characterChoice <= totalCharacters) {
                    characters[characterChoice - 1].displayCharacter();
                } else {
                    System.out.println("---------------------------------------------------------");
                    System.out.println("           Invalid choice! Please try again.");
                    System.out.println("---------------------------------------------------------");
                }
            }
        }
    }

    /**
     * This method lists the characters of a player and displays it as a list
     *
     * @param characters      The array of characters that the player creates
     * @param totalCharacters The total number of characters created by the player.
     */
    private static void listCharacters(Character[] characters, int totalCharacters) {
        if (totalCharacters == 0) {
            System.out.println("---------------------------------------------------------");
            System.out.println("               No characters to display.");
            System.out.println("---------------------------------------------------------");
        } else {
            System.out.println("[Character List]");
            for (int i = 0; i < totalCharacters; i++) {
                System.out.println(
                        (i + 1) + " - " + characters[i].getName() + " (" + characters[i].getCharacterClass() + ")");
            }
        }
    }

    /**
     * This method asks the user if they want a rematch after the battle is done.
     *
     * @param sc The Scanner object used to scan the input of the players.
     * @return true if the user wants a rematch, false otherwise.
     */
    private static boolean rematch(Scanner sc) {
        while (true) {
            System.out.print("Would you like a rematch? (yes/no): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("\n---------------------------------------------------------");
                System.out.println("          Invalid input! Please enter yes or no.");
                System.out.println("---------------------------------------------------------\n");
            }
        }
    }

    /**
     * Handles editing a character's abilities.
     * 
     * @param sc The Scanner object
     * @param character The character to edit
     */
    private static void editCharacterAbilities(Scanner sc, Character character) {
        String currentClass = character.getCharacterClass();
        ArrayList<Ability> availableAbilities = AllAbilities.getAbilitiesByClass(currentClass);

        System.out.println("\nChoose 3 new abilities for " + character.getName() + " (" + currentClass + "):");
        System.out.println();
        for (int i = 0; i < availableAbilities.size(); i++) {
            Ability ability = availableAbilities.get(i);
            System.out.printf("%d. %s (EP: %d) - %s\n", i + 1, ability.getName(), ability.getEpCost(),
                    ability.getDescription());
        }
        System.out.println();
        
        ArrayList<Ability> newAbilities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            boolean validChoice = false;
            while (!validChoice) {
                String ctr = "";
                switch (i) {
                    case 0:
                        ctr = "first";
                        break;
                    case 1:
                        ctr = "second";
                        break;
                    case 2:
                        ctr = "third";
                        break;
                }
                System.out.print("Choose your " + ctr + " ability: ");

                int abilityChoice = getIntInput(sc);
                if (abilityChoice >= 1 && abilityChoice <= availableAbilities.size()) {
                    newAbilities.add(availableAbilities.get(abilityChoice - 1));
                    validChoice = true;
                } else {
                    System.out.println("---------------------------------------------------------");
                    System.out.println(" Invalid! Please choose between the available abilities.");
                    System.out.println("---------------------------------------------------------");
                }
            }
        }

        character.setAbilities(newAbilities.toArray(new Ability[0]));
        System.out.println("\n[Abilities updated successfully!]");
    }

    /**
     * Handles magic item management for a character.
     * 
     * @param sc The Scanner object
     * @param character The character to manage items for
     */
    private static void manageMagicItems(Scanner sc, Character character) {
        boolean managingItems = true;
        while (managingItems) {
            System.out.println("\n---------------------------------------------------------");
            System.out.println("                 MAGIC ITEM MANAGEMENT");
            System.out.println("---------------------------------------------------------");
            System.out.println("1 - View Inventory");
            System.out.println("2 - Equip Item");
            System.out.println("3 - Unequip Item");
            System.out.println("4 - Back to Character Editing");
            System.out.print("\nChoose an option: ");
            int itemChoice = getIntInput(sc);
            
            switch (itemChoice) {
                case 1: // View Inventory
                    displayInventory(character);
                    break;
                case 2: // Equip Item
                    equipMagicItem(sc, character);
                    break;
                case 3: // Unequip Item
                    unequipMagicItem(character);
                    break;
                case 4: // Back to Character Editing
                    managingItems = false;
                    break;
                default:
                    System.out.println("---------------------------------------------------------");
                    System.out.println("          Invalid option! Please try again.");
                    System.out.println("---------------------------------------------------------");
            }
        }
    }

    /**
     * Displays a character's magic item inventory.
     * 
     * @param character The character whose inventory to display
     */
    private static void displayInventory(Character character) {
        System.out.println("\n" + character.getName() + "'s Magic Item Inventory:");
        System.out.println("---------------------------------------------------------");
        
        // Show equipped item
        if (character.getEquippedItem() != null) {
            System.out.println("You have equipped " + character.getEquippedItem().toString());
        } else {
            System.out.println("You did not equip any magic items.");
        }

        System.out.println("---------------------------------------------------------");
        System.out.println("\n                  PLAYER INVENTORY");
        System.out.println("---------------------------------------------------------");
        ArrayList<MagicItem> inventory = character.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Sorry, you currently have no magic items in your inventory.");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                MagicItem item = inventory.get(i);
                System.out.println("   " + (i + 1) + ". " + item.toString());
                System.out.println("      " + item.getDescription());
            }
        }
    }

    /**
     * Handles equipping a magic item.
     * 
     * @param sc The Scanner object
     * @param character The character to equip an item for
     */
    private static void equipMagicItem(Scanner sc, Character character) {
        ArrayList<MagicItem> inventory = character.getInventory();
        
        if (inventory.isEmpty()) {
            System.out.println("\n" + character.getName() + " has no items to equip.");
            return;
        }
        
        System.out.println("\nSelect an item to equip:");
        for (int i = 0; i < inventory.size(); i++) {
            MagicItem item = inventory.get(i);
            System.out.println((i + 1) + ". " + item.toString());
        }
        
        System.out.print("Choose item to equip (0 to cancel): ");
        int choice = getIntInput(sc);
        
        if (choice == 0) {
            return;
        }
        
        if (choice >= 1 && choice <= inventory.size()) {
            MagicItem itemToEquip = inventory.get(choice - 1);
            
            if (character.getEquippedItem() != null) {
                System.out.println("\n" + character.getName() + " already has " + 
                    character.getEquippedItem().getName() + " equipped.");
                System.out.print("Unequip current item and equip " + itemToEquip.getName() + "? (yes/no): ");
                String confirm = sc.nextLine().trim().toLowerCase();
                if (!confirm.equals("yes") && !confirm.equals("y")) {
                    return;
                }
                character.unequipItem();
            }
            
            if (character.equipItem(itemToEquip)) {
                System.out.println("\n[" + itemToEquip.getName() + " equipped successfully!]");
                if (itemToEquip.isPassive()) {
                    System.out.println("Passive effect: " + itemToEquip.getEffect());
                }
            } else {
                System.out.println("\nFailed to equip item.");
            }
        } else {
            System.out.println("\nInvalid choice.");
        }
    }

    /**
     * Handles unequipping the currently equipped magic item.
     * 
     * @param character The character to unequip an item from
     */
    private static void unequipMagicItem(Character character) {
        if (character.getEquippedItem() == null) {
            System.out.println("\n" + character.getName() + " has no item equipped.");
        } else {
            String itemName = character.getEquippedItem().getName();
            character.unequipItem();
            System.out.println("\n[" + itemName + " unequipped successfully!]");
        }
    }
}
