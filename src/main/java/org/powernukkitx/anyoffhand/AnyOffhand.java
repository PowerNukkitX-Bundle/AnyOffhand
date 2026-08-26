package org.powernukkitx.anyoffhand;

import org.powernukkitx.plugin.PluginBase;
import org.powernukkitx.plugin.annotation.PluginMeta;

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
public class AnyOffhand extends PluginBase {

    private static AnyOffhand INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    public static AnyOffhand get() {
        return INSTANCE;
    }
}