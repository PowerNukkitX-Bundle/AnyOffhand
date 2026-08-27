package org.powernukkitx.anyoffhand;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.powernukkitx.Player;
import org.powernukkitx.block.BlockID;
import org.powernukkitx.event.EventHandler;
import org.powernukkitx.event.EventPriority;
import org.powernukkitx.event.Listener;
import org.powernukkitx.event.player.PlayerQuitEvent;
import org.powernukkitx.event.player.PlayerToggleSneakEvent;
import org.powernukkitx.inventory.HumanInventory;
import org.powernukkitx.item.Item;
import org.powernukkitx.plugin.PluginBase;
import org.powernukkitx.plugin.annotation.EventListener;
import org.powernukkitx.plugin.annotation.PluginMeta;
import org.powernukkitx.utils.Config;

import java.util.List;

@PluginMeta(
        name = "AnyOffhand",
        version = "1.0.0",
        authors = {
                "Buddelbubi"
        },
        api = {
                "3.0.0"
        },
        website = "https://github.com/PowerNukkitX-Bundle/AnyOffhand"
)
@EventListener
public class AnyOffhand extends PluginBase implements Listener {

    private static AnyOffhand INSTANCE;

    public static Mode mode;
    public static List<String> items;

    public static int trigger_count;
    public static int trigger_delta;
    public static int trigger_cooldown;

    private static Object2ObjectArrayMap<String, Pair<Integer, Long>> SNEAK_DATA = new Object2ObjectArrayMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Config config = getConfig();
        mode = Mode.valueOf(config.getString("item.mode", "whitelist").toUpperCase());
        items = config.getStringList("item.items");
        trigger_count = config.getInt("item.amount", 3);
        trigger_delta = config.getInt("item.delta", 500);
        trigger_cooldown = config.getInt("item.cooldown", 3000);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if (!event.isCancelled()) {
            if (!event.isSneaking()) {
                Player player = event.getPlayer();
                String name = player.getName();
                Long time = System.currentTimeMillis();
                if (!SNEAK_DATA.containsKey(name)) {
                    SNEAK_DATA.put(name, Pair.of(0,  0L));
                }
                Pair<Integer, Long> data = SNEAK_DATA.get(name);
                long delta = time - data.second();
                if (data.first() >= trigger_count) {
                    if (delta > trigger_cooldown) {
                        data = Pair.of(0, 0L);
                    } else return;
                }
                if (delta > trigger_delta) {
                    data = Pair.of(0, 0L);
                }
                data = Pair.of(data.first()+1, time);
                if (data.first() == trigger_count) {
                    HumanInventory inventory = player.getInventory();
                    Item hand = inventory.getItemInMainHand();
                    if (!hand.isNull()) {
                        if (items.contains(hand.getId())) {
                            if (mode == Mode.BLACKLIST) return;
                        } else if (mode == Mode.WHITELIST) return;
                    }
                    Item offhand = inventory.getItemInOffhand();
                    player.getInventory().setItemInOffhand(hand);
                    player.getInventory().setItemInMainHand(offhand);
                }
                SNEAK_DATA.put(name, data);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SNEAK_DATA.remove(event.getPlayer().getName());
    }

    public static AnyOffhand get() {
        return INSTANCE;
    }

    public enum Mode {
        WHITELIST,
        BLACKLIST
    }
}
