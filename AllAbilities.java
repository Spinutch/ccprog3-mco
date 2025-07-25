import java.util.ArrayList;

/**
 * The AllAbilities class serves as a central repository for all predefined abilities
 * available in the game. It initializes and stores static instances of Ability objects
 * categorized by character class (e.g., Mage, Rogue, Warrior) and includes shared actions
 * such as Defend and Recharge.
 *
 * This class is used for character creation and battle execution, ensuring consistent
 * access to ability definitions and their associated properties.
 *
 * All abilities are stored as public static variables and should not be modified at runtime.
 */

public class AllAbilities {
        /**
         * This method gets the list of abilities based on the character class chosen by
         * the player.
         *
         * @param characterClass The class of the character
         * @return A list of abilities based on the character class chosen by the player
         */
        public static ArrayList<Ability> getAbilitiesByClass(String characterClass) {
                ArrayList<Ability> abilities = new ArrayList<>();

                switch (characterClass) {
                        case "Mage":
                                abilities.add(new Ability("Arcane Bolt",
                                                "Launch a basic magical projectile that deals 20 arcane damage", 5, 20,
                                                0, false, null));
                                abilities
                                                .add(new Ability("Arcane Blast",
                                                                "Unleash a burst of fiery energy, dealing 65 arcane damage to the target. ",
                                                                30, 65, 0,
                                                                false));
                                abilities.add(new Ability("Mana Channel",
                                                "Draw upon ambient magical energy to restore your own. Restores 15 EP.",
                                                0, 0, 15, false,
                                                "EP"));
                                abilities.add(new Ability("Lesser Heal",
                                                "Weave a minor healing spell to mend your wounds. Restores 40 HP. ", 15,
                                                0, 40, false, "HP"));
                                abilities.add(new Ability("Arcane Shield",
                                                "Conjure a protective barrier of mystical energy around yourself. You do not\n"
                                                                + //
                                                                "take any damage for the round.",
                                                12, 0, 0, true)); // SPECIAL
                                break;

                        case "Rogue":
                                abilities.add(new Ability("Shiv",
                                                "A quick, precise stab that deals 20 physical damage.", 5, 20, 0,
                                                false));
                                abilities.add(new Ability("Backstab",
                                                "Strike a vital point and deal 35 points of physical damage.", 15, 35,
                                                0, false));
                                abilities.add(new Ability("Focus",
                                                "Take a moment to concentrate, restoring your mental energy. Restores 10 EP.",
                                                0, 0, 10, false,
                                                "EP"));
                                abilities.add(new Ability("Smoke Bomb",
                                                "Throw a smoke bomb, making you harder to hit. You have a 50% chance of evading any incoming attacks in the current round.",
                                                15, 0, 0, true));
                                abilities.add(new Ability("Sneak Attack",
                                                "You rely on your agility to evade your opponent, taking no damage from any of their attacks, while you deal 45 physical damage to them.",
                                                25, 45, 0, true)); // SPECIAL
                                break;

                        case "Warrior":
                                abilities.add(new Ability("Cleave",
                                                "A sweeping strike that deals 20 physical damage.", 5, 20, 0, false));
                                abilities.add(new Ability("Shield Bash",
                                                "Slam your shield into the opponent, dealing 35 physical damage.", 15,
                                                35, 0, false));
                                abilities.add(new Ability("Ironclad Defense",
                                                "Brace yourself, effectively taking no damage for the current round.",
                                                15, 0, 0, true));
                                abilities.add(new Ability("Bloodlust",
                                                "Tap into your inner fury, restoring a small amount of health. Restores 30 HP.",
                                                12, 0, 30,
                                                false,
                                                "HP"));
                                abilities.add(new Ability("Rallying Cry",
                                                "Let out a powerful shout, inspiring yourself and recovering 12 EP.", 0,
                                                0, 12, false, "EP"));
                                break;
                }

                return abilities;
        }

        /**
         * This method gets all abilities from all character classes.
         * Used for Gnome's special ability to choose from any class.
         *
         * @return A list of all abilities from all classes
         */
        public static ArrayList<Ability> getAllAbilities() {
                ArrayList<Ability> allAbilities = new ArrayList<>();
                
                allAbilities.addAll(getAbilitiesByClass("Mage"));
                
                allAbilities.addAll(getAbilitiesByClass("Rogue"));
                
                allAbilities.addAll(getAbilitiesByClass("Warrior"));
                
                return allAbilities;
        }
}
