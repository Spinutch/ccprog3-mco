package model;
/**
 * The MagicItem class represents magic items that can be collected, equipped, and used by characters.
 * Magic items can either be single-use (consumed when activated) or passive (providing continuous effects).
 * 
 * Magic Item Features:
 *   - Single-use items: activated manually and consumed after use
 *   - Passive items: provide continuous bonuses when equipped
 *   - HP/EP restoration effects are capped at character's maximum values
 *   - Drop rates determine the likelihood of obtaining specific items
 */
public class MagicItem {
    private String name;
    private String activationType; 
    private String description;
    private String effect;
    private int hpRestore;
    private int epRestore;
    private int hpBonus; 
    private int epBonus; 
private boolean shieldAll;
    private boolean healPerTurn; 
    private int healAmount;
    private int epPerTurn; 
    private double dropRate; 

    /**
     * Constructor for single-use items with HP/EP restoration
     */
    public MagicItem(String name, String activationType, String description, String effect, 
                     int hpRestore, int epRestore, double dropRate) {
        this.name = name;
        this.activationType = activationType;
        this.description = description;
        this.effect = effect;
        this.hpRestore = hpRestore;
        this.epRestore = epRestore;
        this.dropRate = dropRate;
        this.hpBonus = 0;
        this.epBonus = 0;
        this.shieldAll = false;
        this.healPerTurn = false;
        this.healAmount = 0;
        this.epPerTurn = 0;
    }

    /**
     * Constructor for passive items with stat bonuses
     */
    public MagicItem(String name, String activationType, String description, String effect,
                     int hpBonus, int epBonus, boolean healPerTurn, int healAmount, int epPerTurn, double dropRate) {
        this.name = name;
        this.activationType = activationType;
        this.description = description;
        this.effect = effect;
        this.hpBonus = hpBonus;
        this.epBonus = epBonus;
        this.healPerTurn = healPerTurn;
        this.healAmount = healAmount;
        this.epPerTurn = epPerTurn;
        this.dropRate = dropRate;
        this.hpRestore = 0;
        this.epRestore = 0;
        this.shieldAll = false;
    }

    /**
     * Constructor for defensive items (Defender's Aegis)
     */
    public MagicItem(String name, String activationType, String description, String effect,
                     boolean negatesAllDamage, double dropRate) {
        this.name = name;
        this.activationType = activationType;
        this.description = description;
        this.effect = effect;
        this.shieldAll = negatesAllDamage;
        this.dropRate = dropRate;
        this.hpRestore = 0;
        this.epRestore = 0;
        this.hpBonus = 0;
        this.epBonus = 0;
        this.healPerTurn = false;
        this.healAmount = 0;
        this.epPerTurn = 0;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getActivationType() {
        return activationType;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

    public int getHpRestore() {
        return hpRestore;
    }

    public int getEpRestore() {
        return epRestore;
    }

    public int getHpBonus() {
        return hpBonus;
    }

    public int getEpBonus() {
        return epBonus;
    }

    public boolean shieldAll() {
        return shieldAll;
    }

    public boolean isHealPerTurn() {
        return healPerTurn;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public int getEpPerTurn() {
        return epPerTurn;
    }

    public double getDropRate() {
        return dropRate;
    }

    public boolean isSingleUse() {
        return activationType.equals("Single-Use");
    }

    public boolean isPassive() {
        return activationType.equals("Passive");
    }

    @Override
    public String toString() {
        return name + " (" + activationType + ") - " + effect;
    }
}
