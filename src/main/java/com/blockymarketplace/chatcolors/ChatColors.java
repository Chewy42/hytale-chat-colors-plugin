package com.blockymarketplace.chatcolors;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Built-in colour presets and hex validation utilities.
 *
 * Players can reference any preset by name (e.g. /chatcolor red)
 * or supply their own 6-digit hex code (e.g. /chatcolor #FF6A00).
 */
public final class ChatColors {

    private ChatColors() {}

    /** Named preset palette available to all players. */
    public static final Map<String, String> PRESETS = new LinkedHashMap<>();

    static {
        PRESETS.put("red",    "#FF4444");
        PRESETS.put("orange", "#FF8C00");
        PRESETS.put("yellow", "#FFD700");
        PRESETS.put("green",  "#44FF77");
        PRESETS.put("teal",   "#00CED1");
        PRESETS.put("blue",   "#4488FF");
        PRESETS.put("purple", "#9B59B6");
        PRESETS.put("pink",   "#FF69B4");
        PRESETS.put("white",  "#FFFFFF");
        PRESETS.put("gray",   "#AAAAAA");
        PRESETS.put("reset",  null);          // null = remove colour override
    }

    /**
     * Resolve a player-supplied input to a hex colour string.
     *
     * @param input   Preset name or raw hex (with or without #)
     * @return        Normalised "#RRGGBB" string, or null to clear.
     * @throws IllegalArgumentException if the input is invalid.
     */
    public static String resolve(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Colour input must not be blank.");
        }

        String lower = input.toLowerCase().trim();

        // Named preset lookup
        if (PRESETS.containsKey(lower)) {
            return PRESETS.get(lower); // may be null (reset)
        }

        // Raw hex — strip leading # if present, then validate
        String hex = lower.startsWith("#") ? lower : "#" + lower;
        if (!hex.matches("#[0-9a-f]{6}")) {
            throw new IllegalArgumentException(
                "'" + input + "' is not a valid preset or hex colour. " +
                "Use a name like 'red' or a hex code like '#FF4400'."
            );
        }
        return hex.toUpperCase();
    }

    /** Returns a comma-separated list of preset names for help text. */
    public static String presetList() {
        return String.join(", ", PRESETS.keySet());
    }
}
