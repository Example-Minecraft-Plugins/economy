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
        RedisCache<EconomyUserImpl> cache = plugin.getEconomyCache();

        if (!cache.has(event.getPlayer().getName())) {
            cache.add(event.getPlayer().getName(), new EconomyUserImpl(event.getPlayer().getName()));
        }
    }
}
