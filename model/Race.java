package model;
/**
 * The Race class represents the different races available for character creation
 *
 * Appendix E: Race Stats and bonuses:
 *   - Human: +15 to max HP, +5 to max EP
 *   - Dwarf: +30 to max HP
 *   - Elf: +15 to max EP
 *   - Gnome: +1 additional ability slot (will be able to choose from any class)
 */
public class Race {
    private final String name;
    private final String description;
    private final int hpBonus;
    private final int epBonus;
    private final boolean hasExtraAbilitySlot;

    public static final Race HUMAN = new Race("Human", "Adaptable and resilient, humans possess a balanced set of attributes.", 15, 5, false);
    public static final Race DWARF = new Race("Dwarf", "Stocky and tough, dwarves are known for their incredible endurance and steadfastness.", 30, 0, false);
    public static final Race ELF = new Race("Elf", "Graceful and naturally attuned to arcane energies, elves excel in precise actions and magical prowess.", 0, 15, false);
    public static final Race GNOME = new Race("Gnome", "Clever and resourceful, gnomes have a knack for finding hidden opportunities or leveraging unusual tricks.", 0, 0, true);

    private static final java.util.List<Race> raceList = new java.util.ArrayList<>();

    static {
        raceList.add(HUMAN);
        raceList.add(DWARF);
        raceList.add(ELF);
        raceList.add(GNOME);
    }

    /**
     * Constructs a Race with the specified properties.
     *
     * @param name the display name of the race
     * @param description a brief description of the race's characteristics
     * @param hpBonus the bonus HP the race provides
     * @param epBonus the bonus EP the race provides
     * @param hasExtraAbilitySlot whether this race gets an additional ability slot
     */
    private Race(String name, String description, int hpBonus, int epBonus, boolean hasExtraAbilitySlot) {
        this.name = name;
        this.description = description;
        this.hpBonus = hpBonus;
        this.epBonus = epBonus;
        this.hasExtraAbilitySlot = hasExtraAbilitySlot;
    }

    /**
     * Returns the display name of the race.
     *
     * @return the race name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the race.
     *
     * @return the race description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the HP bonus provided by this race.
     *
     * @return the HP bonus
     */
    public int getHpBonus() {
        return hpBonus;
    }

    /**
     * Returns the EP bonus provided by this race.
     *
     * @return the EP bonus
     */
    public int getEpBonus() {
        return epBonus;
    }

    /**
     * Returns whether this race provides an additional ability slot.
     *
     * @return true if the race has an extra ability slot, false otherwise
     */
    public boolean hasExtraAbilitySlot() {
        return hasExtraAbilitySlot;
    }

    @Override
    public String toString() {
    return name;  
    }

    /**
     * Returns a race by its name (case-insensitive).
     *
     * @param raceName the name of the race to find
     * @return the matching Race, or null if not found
     */
    public static Race getRaceByName(String raceName) {
        for (int i = 0; i < raceList.size(); i++) {
            Race race = raceList.get(i);
            if (race.getName().equalsIgnoreCase(raceName)) {
                return race;
            }
        }
        return null;
    }

    /**
     * Returns an array of all available race names.
     *
     * @return array of race names
     */
    public static String[] show_race() {
        String[] names = new String[raceList.size()];
        for (int i = 0; i < raceList.size(); i++) {
            names[i] = raceList.get(i).getName();
        }
        return names;
    }

    public static Race[] show_race_array() {
        return raceList.toArray(new Race[0]);
    }
}
