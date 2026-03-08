package com.blockymarketplace.chatcolors;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class ChatColorCommand extends AbstractPlayerCommand {

    private final ChatColorRegistry registry;
    private final OptionalArg<String> colorArg;

    public ChatColorCommand(ChatColorRegistry registry) {
        super("chatcolor", "Set your in-game chat name colour");
        this.registry = registry;
        this.colorArg = withOptionalArg("color", "Preset name, #hex code, or 'reset'", ArgTypes.STRING);
    }

    @Override
    protected void execute(
            CommandContext context,
            Store<EntityStore> store,
            Ref<EntityStore> ref,
            PlayerRef playerRef,
            World world
    ) {
        String uuid = playerRef.getUuid().toString();

        if (!context.provided(colorArg)) {
            String current = registry.getColor(uuid);
            if (current == null) {
                context.sendMessage(Message.raw("[ChatColors] No custom colour set."));
            } else {
                context.sendMessage(Message.raw("[ChatColors] Current colour: " + current));
            }
            context.sendMessage(Message.raw("[ChatColors] Presets: " + ChatColors.presetList()));
            context.sendMessage(Message.raw("[ChatColors] Usage: /chatcolor <preset|#hex|reset>"));
            return;
        }

        String arg = context.get(colorArg);

        if (arg.equalsIgnoreCase("reset")) {
            registry.setColor(uuid, null);
            context.sendMessage(Message.raw("[ChatColors] Chat colour reset to default."));
            return;
        }

        try {
            String hex = ChatColors.resolve(arg);
            registry.setColor(uuid, hex);
            context.sendMessage(Message.raw("[ChatColors] Chat colour set to " + hex + "."));
        } catch (IllegalArgumentException e) {
            context.sendMessage(Message.raw("[ChatColors] Error: " + e.getMessage()));
        }
    }
}
