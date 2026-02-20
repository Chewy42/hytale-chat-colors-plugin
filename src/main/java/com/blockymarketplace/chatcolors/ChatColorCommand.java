package com.blockymarketplace.chatcolors;

import com.hypixel.hytale.server.core.command.Command;
import com.hypixel.hytale.server.core.command.CommandContext;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;

/**
 * /chatcolor [color]
 *
 * Usage:
 *   /chatcolor red          — named preset
 *   /chatcolor #FF4400      — custom hex
 *   /chatcolor reset        — remove colour override
 *   /chatcolor              — show current colour and preset list
 */
public final class ChatColorCommand extends Command {

    private final ChatColorRegistry registry;

    public ChatColorCommand(ChatColorRegistry registry) {
        super("chatcolor", "cc");
        this.registry = registry;
    }

    @Override
    public void execute(CommandContext ctx) {
        PlayerRef player = ctx.getPlayer();
        if (player == null) {
            ctx.send(Message.raw("This command must be run by a player."));
            return;
        }

        String uuid = player.getUuid().toString();

        if (ctx.getArgs().isEmpty()) {
            // Show current colour
            String current = registry.getColor(uuid);
            if (current == null) {
                ctx.send(prefix().insert(Message.raw(" You have no custom chat colour set.")));
            } else {
                ctx.send(prefix()
                    .insert(Message.raw(" Your current colour: "))
                    .insert(Message.raw(current).color(current).bold(true)));
            }
            ctx.send(Message.raw("Presets: " + ChatColors.presetList())
                .color("#AAAAAA"));
            ctx.send(Message.raw("Usage: /chatcolor <preset|#hex>")
                .color("#AAAAAA"));
            return;
        }

        String input = ctx.getArgs().get(0);
        String hex;
        try {
            hex = ChatColors.resolve(input);
        } catch (IllegalArgumentException e) {
            ctx.send(error(e.getMessage()));
            return;
        }

        registry.setColor(uuid, hex);

        if (hex == null) {
            ctx.send(prefix().insert(Message.raw(" Chat colour reset to default.")));
        } else {
            ctx.send(prefix()
                .insert(Message.raw(" Chat colour set to "))
                .insert(Message.raw(hex).color(hex).bold(true))
                .insert(Message.raw(".")));
        }
    }

    private static Message prefix() {
        return Message.raw("[ChatColors]").color("#19B36B").bold(true);
    }

    private static Message error(String text) {
        return Message.empty()
            .insert(prefix())
            .insert(Message.raw(" Error: ").color("#E03A3E").bold(true))
            .insert(Message.raw(text));
    }
}
