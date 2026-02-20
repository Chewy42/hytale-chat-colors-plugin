package com.blockymarketplace.chatcolors;

import com.hypixel.hytale.server.core.plugin.Plugin;
import com.hypixel.hytale.server.core.plugin.PluginLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores each player's chosen chat name colour, keyed by UUID string.
 * Persisted to a simple key=value file in the plugin data folder.
 */
public final class ChatColorRegistry {

    private static final String FILE_NAME = "player_colors.properties";

    private final Plugin plugin;
    private final Map<String, String> colorMap = new ConcurrentHashMap<>();
    private Path dataFile;

    public ChatColorRegistry(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        try {
            Path dir = plugin.getDataFolder().toPath();
            Files.createDirectories(dir);
            dataFile = dir.resolve(FILE_NAME);

            if (!Files.exists(dataFile)) {
                return;
            }

            Properties props = new Properties();
            try (InputStream in = Files.newInputStream(dataFile)) {
                props.load(in);
            }
            props.forEach((k, v) -> colorMap.put((String) k, (String) v));
            PluginLogger.info("[ChatColors] Loaded " + colorMap.size() + " player colour(s).");
        } catch (IOException e) {
            PluginLogger.warn("[ChatColors] Could not load player colours: " + e.getMessage());
        }
    }

    public void save() {
        if (dataFile == null) return;
        try {
            Properties props = new Properties();
            colorMap.forEach(props::setProperty);
            try (OutputStream out = Files.newOutputStream(dataFile)) {
                props.store(out, "ChatColors — player colour preferences");
            }
        } catch (IOException e) {
            PluginLogger.warn("[ChatColors] Could not save player colours: " + e.getMessage());
        }
    }

    /** Returns the stored hex colour for a player UUID, or null if unset. */
    public String getColor(String uuid) {
        return colorMap.get(uuid);
    }

    /** Sets (or clears when hex is null) a player's colour. */
    public void setColor(String uuid, String hex) {
        if (hex == null) {
            colorMap.remove(uuid);
        } else {
            colorMap.put(uuid, hex);
        }
        save();
    }
}
