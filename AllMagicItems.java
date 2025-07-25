import java.util.ArrayList;
import java.util.Random;

/**
 * The AllMagicItems class serves as a central repository for all predefined magic items
 * available in the game. It stores items categorized by rarity (Common, Uncommon, Rare)
 */
public class AllMagicItems {
    private static ArrayList<MagicItem> allItems = new ArrayList<>();
    private static Random random = new Random();

    static {
        initializeMagicItems();
    }

    /**
     * Sets up the collection of all magic items with their attributes and drop rates as well.
     */
    private static void initializeMagicItems() {
        // COMMON (60)
        allItems.add(new MagicItem("Potion of Minor Healing", "Single-Use", 
            "A basic potion that restores a small amount of health", 
            "Heals the user for 40 HP", 40, 0, 0.60));

        allItems.add(new MagicItem("Scroll of Minor Energy", "Single-Use",
            "A simple scroll inscribed with runes that replenish a small amount of energy",
            "Restores 20 EP to the user", 0, 20, 0.60));

        allItems.add(new MagicItem("Defender's Aegis", "Single-Use",
            "A small, temporary barrier that absorbs damage",
            "Negates all incoming damage", true, 0.60));

        // UNCOMMON (35)
        allItems.add(new MagicItem("Amulet of Vitality", "Passive",
            "An enchanted amulet that subtly strengthens your life force",
            "Increases max HP by 20", 20, 0, false, 0, 0, 0.35));

        allItems.add(new MagicItem("Ring of Focus", "Passive",
            "A plain ring that helps you concentrate, boosting your energy regeneration",
            "Additional +2 EP at the start of each turn", 0, 0, false, 0, 2, 0.35));

        // RARE (5)
        allItems.add(new MagicItem("Orb of Resilience", "Passive",
            "A small, smooth orb that provides a constant minor protective aura",
            "Heal +5 HP at the start of each turn", 0, 0, true, 5, 0, 0.05));

        allItems.add(new MagicItem("Ancient Tome of Power", "Passive",
            "A worn book filled with forgotten wisdom that grants a small, continuous surge of power",
            "Additional +5 EP at the start of each turn", 0, 0, false, 0, 5, 0.05));
    }

    /**
     * Returns a list of all available magic items.
     * 
     * @return ArrayList containing all magic items
     */
    public static ArrayList<MagicItem> getAllItems() {
        return new ArrayList<>(allItems);
    }

    /**
     * Randomly selects a magic item based on rarity tiers.
     * First determines the rarity (60% Common, 35% Uncommon, 5% Rare),
     * then randomly selects from items within that tier.
     * 
     * @return A randomly selected MagicItem, or null if no item is awarded
     */
    public static MagicItem getRandomItem() {
        double randomNumber = random.nextDouble();
        
        // Determine rarity tier
        if (randomNumber <= 0.60) {
            // Common items (60% chance)
            ArrayList<MagicItem> commonItems = new ArrayList<>();
            for (int i = 0; i < allItems.size(); i++) {
                MagicItem item = allItems.get(i);
                if (item.getDropRate() == 0.60) {
                    commonItems.add(item);
                }
            }
            if (commonItems.size() > 0) {
                int randomIndex = random.nextInt(commonItems.size());
                return commonItems.get(randomIndex);
            }
        } else if (randomNumber <= 0.95) {
            // Uncommon items (35% chance, cumulative 95%)
            ArrayList<MagicItem> uncommonItems = new ArrayList<>();
            for (int i = 0; i < allItems.size(); i++) {
                MagicItem item = allItems.get(i);
                if (item.getDropRate() == 0.35) {
                    uncommonItems.add(item);
                }
            }
            if (uncommonItems.size() > 0) {
                int randomIndex = random.nextInt(uncommonItems.size());
                return uncommonItems.get(randomIndex);
            }
        } else {
            // Rare items (5% chance)
            ArrayList<MagicItem> rareItems = new ArrayList<>();
            for (int i = 0; i < allItems.size(); i++) {
                MagicItem item = allItems.get(i);
                if (item.getDropRate() == 0.05) {
                    rareItems.add(item);
                }
            }
            if (rareItems.size() > 0) {
                int randomIndex = random.nextInt(rareItems.size());
                return rareItems.get(randomIndex);
            }
        }
        return null;
    }

    /**
     * Gets a magic item by name
     * 
     * @param name The name of the magic item to find
     * @return The MagicItem object, or does not return anything if not found
     */
    public static MagicItem getItemByName(String name) {
        for (MagicItem item : allItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Gets all single-use magic items.
     * 
     * @return ArrayList containing all single-use magic items
     */
    public static ArrayList<MagicItem> getSingleUseItems() {
        ArrayList<MagicItem> singleUseItems = new ArrayList<>();
        for (int i = 0; i < allItems.size(); i++) {
            MagicItem item = allItems.get(i);
            if (item.isSingleUse()) {
                singleUseItems.add(item);
            }
        }
        return singleUseItems;
    }

    /**
     * Gets all passive magic items.
     * 
     * @return ArrayList containing all passive magic items
     */
    public static ArrayList<MagicItem> getPassiveItems() {
        ArrayList<MagicItem> passiveItems = new ArrayList<>();
        for (MagicItem item : allItems) {
            if (item.isPassive()) {
                passiveItems.add(item);
            }
        }
        return passiveItems;
    }
}
