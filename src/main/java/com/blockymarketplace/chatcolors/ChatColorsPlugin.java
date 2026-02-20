package com.blockymarketplace.chatcolors;

import com.hypixel.hytale.server.core.plugin.Plugin;
import com.hypixel.hytale.server.core.plugin.PluginLogger;
import com.hypixel.hytale.server.core.command.CommandManager;

/**
 * ChatColors — Hytale server plugin.
 *
 * Lets players personalise the colour of their chat name using hex codes
 * or a palette of preset colours. Colours are persisted per-player across
 * sessions via the built-in config system.
 */
public final class ChatColorsPlugin extends Plugin {

    private static ChatColorsPlugin instance;
    private ChatColorRegistry registry;

    @Override
    public void onEnable() {
        instance = this;
        PluginLogger.info("[ChatColors] Enabling ChatColors v" + getDescription().getVersion());

        // Load player colour preferences
        registry = new ChatColorRegistry(this);
        registry.load();

        // Register the /chatcolor command
        CommandManager.register(new ChatColorCommand(registry));

        PluginLogger.info("[ChatColors] Ready. Use /chatcolor <hex|preset> to set your chat colour.");
    }

    @Override
    public void onDisable() {
        if (registry != null) {
            registry.save();
        }
        PluginLogger.info("[ChatColors] Disabled.");
    }

    public static ChatColorsPlugin getInstance() {
        return instance;
    }

    public ChatColorRegistry getRegistry() {
        return registry;
    }
}
