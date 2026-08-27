# AnyOffhand

AnyOffhand is a simple plugin for [PowerNukkitX](https://github.com/PowerNukkitX/PowerNukkitX) servers. It lets players quickly swap an item between their main hand and offhand.

## How to use it

1. Hold an item in your main hand.
2. Sneak twice quickly.
3. The items in your main hand and offhand will be swapped.

After swapping, wait for the short cooldown before trying again.

## Installation

1. Download the latest `AnyOffhand.jar` file.
2. Place it in the `plugins` folder of your PowerNukkitX server.
3. Restart the server.

The plugin creates its configuration file automatically when it starts for the first time.

## Configuration

Open `plugins/AnyOffhand/config.yml` to change which items players can move to their offhand and how the sneak trigger behaves.

```yaml
item:
  mode: "whitelist"
  items:
    - "minecraft:torch"

trigger:
  amount: 2
  delta: 1000
  cooldown: 3000
```

### Item mode

- `whitelist`: Only the items listed under `items` can be moved to the offhand.
- `blacklist`: Every item except the items listed under `items` can be moved to the offhand.

Use item identifiers such as `minecraft:torch` in the list.

### Trigger settings

- `amount`: Number of times a player must sneak to swap items.
- `delta`: Maximum time between sneaks, in milliseconds.
- `cooldown`: Waiting time after a swap, in milliseconds.

Restart the server after changing the configuration.

## Requirements

- A PowerNukkitX server compatible with API 3.0.0

## Support

If you find a problem, please open an issue on the [GitHub repository](https://github.com/PowerNukkitX-Bundle/AnyOffhand/issues).
