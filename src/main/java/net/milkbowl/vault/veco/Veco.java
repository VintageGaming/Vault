package net.milkbowl.vault.veco;

import net.milkbowl.vault.Vault;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class Veco implements Listener {

    private static final HashMap<UUID, Double> onlineAccounts = new HashMap<>();
    private static FileConfiguration econConfig;
    private static File econFile;
    private static BukkitTask autoSaveTask;

    public static void loadBalanceFile(Plugin plugin) {
        econFile = new File(plugin.getDataFolder(), "veco.yml");
        if (!econFile.exists()) {
            econFile.getParentFile().mkdirs();
            try {
                econFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        econConfig = YamlConfiguration.loadConfiguration(econFile);

        plugin.getServer().getOnlinePlayers().forEach(Veco::loadBalance);

        // Auto-save every 5 minutes (6000 ticks)
        autoSaveTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (!Vault.getInstance().usingVEco)
                autoSaveTask.cancel();

            saveBalances();
        }, 6000L, 6000L);
    }

    public static double getPlayerBalance(UUID uuid) {
        if (onlineAccounts.containsKey(uuid))
            return onlineAccounts.get(uuid);
        else
            return econConfig.getDouble(uuid.toString(), 0);
    }

    public static double getPlayerBalance(OfflinePlayer player) {
        return getPlayerBalance(player.getUniqueId());
    }

    public static double getPlayerBalance(String playerName) {
        UUID playerUUID = Vault.getUUIDFromName(playerName);
        if (playerUUID == null) return 0;
        return getPlayerBalance(playerUUID);
    }

    public static void setPlayerBalance(UUID uuid, double balance) {
        onlineAccounts.put(uuid, balance);
    }

    public static void loadBalance(Player player) {
        double balance = econConfig.getDouble(player.getUniqueId().toString(), 0);
        onlineAccounts.put(player.getUniqueId(), balance);
    }

    private static void updateConfig(UUID uuid) {
        if (onlineAccounts.containsKey(uuid)) {
            econConfig.set(uuid.toString(), onlineAccounts.get(uuid));
        }
    }

    private static void saveConfigToDisk() {
        try {
            econConfig.save(econFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBalance(UUID uuid) {
        updateConfig(uuid);
        saveConfigToDisk();
    }

    public void saveBalance(Player player) {
        saveBalance(player.getUniqueId());
    }

    public void saveBalance(OfflinePlayer player) {
        saveBalance(player.getUniqueId());
    }

    public static void saveBalances() {
        for (UUID uuid : onlineAccounts.keySet()) {
            updateConfig(uuid);
        }
        saveConfigToDisk();
    }

    public static boolean offlinePlayerAccountExists(OfflinePlayer player) {
        return econConfig.contains(player.getUniqueId().toString());
    }

    public static boolean offlinePlayerAccountExists(String playerName) {
        UUID playerUUID = Vault.getUUIDFromName(playerName);
        if (playerUUID == null) return false;
        return econConfig.contains(playerUUID.toString());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        loadBalance(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        saveBalance(player);
        onlineAccounts.remove(player.getUniqueId());
    }

}
