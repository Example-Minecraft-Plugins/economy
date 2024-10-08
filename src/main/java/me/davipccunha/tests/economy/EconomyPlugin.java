package me.davipccunha.tests.economy;

import lombok.Getter;
import me.davipccunha.tests.economy.api.EconomyAPI;
import me.davipccunha.tests.economy.command.EconomyCommand;
import me.davipccunha.tests.economy.listener.PlayerJoinListener;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import me.davipccunha.tests.economy.provider.EconomyProvider;
import me.davipccunha.utils.cache.RedisCache;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class EconomyPlugin extends JavaPlugin {

    private RedisCache<EconomyUserImpl> economyCache;

    @Override
    public void onEnable() {
        this.init();
        getLogger().info("Economy plugin carregado!");
    }

    public void onDisable() {
        getLogger().info("Economy plugin descarregado!");
    }

    private void init() {
        saveDefaultConfig();
        this.registerListeners(
                new PlayerJoinListener(this)
        );
        this.registerCommands();
        this.loadCaches();

        Bukkit.getServicesManager().register(EconomyAPI.class, new EconomyProvider(economyCache), this, ServicePriority.High);
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = getServer().getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        this.getCommand("coins").setExecutor(new EconomyCommand(this));
        this.getCommand("cash").setExecutor(new EconomyCommand(this));
    }

    private void loadCaches() {
        this.economyCache = new RedisCache<>("economy", EconomyUserImpl.class);
    }
}
