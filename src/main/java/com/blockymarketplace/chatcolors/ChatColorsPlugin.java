package com.blockymarketplace.chatcolors;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import java.util.logging.Level;

public final class ChatColorsPlugin extends JavaPlugin {

    private ChatColorRegistry registry;

    public ChatColorsPlugin(JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        registry = new ChatColorRegistry();
    }

    @Override
    protected void start() {
        registry.load(getDataDirectory());
        getCommandRegistry().registerCommand(new ChatColorCommand(registry));
        getLogger().at(Level.INFO).log("[ChatColors] Enabled v%s — /chatcolor to set your name colour", getManifest().getVersion());
    }

    @Override
    protected void shutdown() {
        if (registry != null) registry.save();
    }
}
