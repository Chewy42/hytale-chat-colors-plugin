# Hytale Chat Colors Plugin

Open-source Hytale server plugin that lets players personalise the colour of their chat name — supporting preset palette names and custom hex codes.

Available as a ready-to-install plugin on [BlockyMarketplace](https://blockymarketplace.com).

---

## Features

- `/chatcolor <preset|#hex>` — set your chat name colour
- `/chatcolor reset` — restore default colour
- `/chatcolor` — view current colour and available presets
- Colour preferences persist across sessions
- 10 built-in presets: `red`, `orange`, `yellow`, `green`, `teal`, `blue`, `purple`, `pink`, `white`, `gray`
- Full hex colour support (`#RRGGBB`)
- Command alias: `/cc`

## Requirements

- Java 21
- Hytale server API jar (`HytaleServer.jar`)

## Setup

1. Place your Hytale server API jar at:
   ```
   libs/HytaleServer.jar
   ```

2. Build the plugin:
   ```bash
   ./gradlew clean shadowJar
   ```

3. Copy the built jar from:
   ```
   build/libs/hytale-chat-colors-plugin-1.0.0.jar
   ```

4. Drop it in your server's `mods/` directory and restart.

## Commands

| Command | Description |
|---|---|
| `/chatcolor` | Show your current colour and preset list |
| `/chatcolor <preset>` | Set colour by preset name (e.g. `red`, `blue`) |
| `/chatcolor <#hex>` | Set colour by hex code (e.g. `#FF6A00`) |
| `/chatcolor reset` | Remove your colour override |

Alias: `/cc`

## Colour Presets

| Name | Hex |
|---|---|
| red | `#FF4444` |
| orange | `#FF8C00` |
| yellow | `#FFD700` |
| green | `#44FF77` |
| teal | `#00CED1` |
| blue | `#4488FF` |
| purple | `#9B59B6` |
| pink | `#FF69B4` |
| white | `#FFFFFF` |
| gray | `#AAAAAA` |

## Data Storage

Player colour preferences are stored in:
```
plugins/ChatColors/player_colors.properties
```

## License

MIT — free to use, modify, and distribute.

---

Made with ❤️ by [BlockyMarketplace](https://blockymarketplace.com)
