package me.davipccunha.tests.economy.listener;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import me.davipccunha.utils.cache.RedisCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final EconomyPlugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final RedisCache<EconomyUserImpl> cache = plugin.getEconomyCache();
        final String name = event.getPlayer().getName();

        if (!cache.has(name))
            cache.add(name, new EconomyUserImpl(name));
    }
}
