package net.milkbowl.vault.permission.plugins;

import com.VintageGaming.VintagePerms.Management.Groups;
import com.VintageGaming.VintagePerms.SettingsManager;
import net.milkbowl.vault.permission.Permission;
import com.VintageGaming.VintagePerms.PermsMain;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Permission_VintagePerms extends Permission {

    private final String name = "VintagePerms";
    private PermsMain permission = null;

    public Permission_VintagePerms(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new PermissionServerListener(this), plugin);

        if (this.permission == null) {
            Plugin perms = plugin.getServer().getPluginManager().getPlugin("VintagePerms");
            if (perms != null && perms.isEnabled()) {
                this.permission = (PermsMain) perms;
            }

            log.info(String.format("[%s][Permission] %s hooked.", plugin.getDescription().getName(), "VintagePerms"));
        }
    }


    @Override
    public boolean isEnabled() {
        if (this.permission == null) {
            return false;
        }
        return this.plugin.isEnabled();
    }

    @Override
    public String getName() {
        return "VintagePerms";
    }

    @Override
    public boolean has(String worldName, String playerName, String permission) {
        Player p = Bukkit.getPlayer(playerName);
        return p.hasPermission(permission);
    }

    @Override
    public boolean playerHas(String worldName, String playerName, String permission) {
        // Most of the time, this can just call the other has() method.
        return has(worldName, playerName, permission);
    }

    @Override
    public boolean playerAdd(String worldName, String playerName, String permission) {
        // TODO: Add your logic to give a player a permission node.
        SettingsManager.getInstance().getUser(playerName).addPermission(permission);
        return true; // Return true on success
    }

    @Override
    public boolean playerRemove(String worldName, String playerName, String permission) {
        // TODO: Add your logic to take a permission node from a player.
        SettingsManager.getInstance().getUser(playerName).removePermission(permission);
        return true; // Return true on success
    }

    @Override
    public String getPrimaryGroup(String worldName, String playerName) {
        // TODO: Add your logic to get the name of the player's primary group.
        return SettingsManager.getInstance().getUser(playerName).getGroups().getFirst();
    }

    @Override
    public boolean playerInGroup(String worldName, String playerName, String group) {
        // TODO: Add your logic to check if a player is in a specific group.
        return SettingsManager.getInstance().getUser(playerName).getGroups().contains(group);
    }

    @Override
    public boolean playerAddGroup(String worldName, String playerName, String group) {
        SettingsManager.getInstance().getUser(playerName).addGroup(group, SettingsManager.getInstance().getUser(playerName).getGroups().size()-1);
        return true; // Return true on success
    }

    @Override
    public boolean playerRemoveGroup(String worldName, String playerName, String group) {
        SettingsManager.getInstance().getUser(playerName).removeGroup(group);
        return true; // Return true on success
    }

    @Override
    public boolean groupHas(String worldName, String groupName, String permission) {
        List<String> perms = SettingsManager.getGroup(groupName).getInheritedPermissions();

        if (worldName.isEmpty() || worldName == null)
            perms.addAll(SettingsManager.getGroup(groupName).getPerms());

        else
            perms.addAll(SettingsManager.getGroup(groupName).getWorldPerms(worldName));

        return perms.contains(permission);
    }

    @Override
    public boolean groupAdd(String worldName, String groupName, String permission) {
        if (worldName.isEmpty() || worldName == null)
            SettingsManager.getGroup(groupName).addPerm(permission);
        else
            SettingsManager.getGroup(groupName).addWorldPerm(worldName, permission);
        return true;
    }

    @Override
    public boolean groupRemove(String worldName, String groupName, String permission) {
        SettingsManager.getGroup(groupName).remPerm(permission);
        return true;
    }

    @Override
    public String[] getPlayerGroups(String worldName, String playerName) {
        // TODO: Add your logic to get a list of all groups a player is in.
        return SettingsManager.getInstance().getUser(playerName).getGroups().toArray(new String[0]);
    }

    @Override
    public String[] getGroups() {
        ArrayList<String> groups = new ArrayList<>();
        for (Groups g : SettingsManager.groups.values()) {
            groups.add(g.getName());
        }
        return groups.toArray(new String[0]);
    }

    @Override
    public boolean hasSuperPermsCompat() {
        // You can generally return true here. It means your plugin handles permissions.
        return true;
    }

    @Override
    public boolean hasGroupSupport() {
        // Return true if your plugin supports groups.
        return true;
    }

    //Custom Vault Update Methods

    @Override
    public String[] getPlayerPermissions(String worldName, OfflinePlayer player) {
        return SettingsManager.getInstance().getUser(((Player) player)).getPermissions().toArray(new String[0]);
    }

    @Override
    public String[] getGroupPermissions(String worldName, String groupName) {
        if (worldName.isEmpty() || worldName == null)
            return SettingsManager.getGroup(groupName).getPerms().toArray(new String[0]);

        return SettingsManager.getGroup(groupName).getWorldPerms(worldName).toArray(new String[0]);
    }

    @Override
    public String[] getGroupParents(String worldName, String groupName) {
        try {
            ArrayList<String> parents = SettingsManager.getGroup(groupName).getInheritance();
            return parents.toArray(new String[0]);
        } catch (NullPointerException e) {
            return new String[0];
        }
    }

    @Override
    public String getGroupPrefix(String groupName) {
        return SettingsManager.getGroup(groupName).getPrefix() != null ? SettingsManager.getGroup(groupName).getPrefix() : "";
    }

    @Override
    public boolean setGroupPrefix(String groupName, String prefix) {
        return SettingsManager.getGroup(groupName).setPrefix(prefix);
    }

    @Override
    public boolean groupCreate(String worldName, String groupName, boolean isDefault) {
        return SettingsManager.getInstance().createGroup(groupName);
    }

    @Override
    public boolean groupDelete(String worldName, String groupName) {
        return SettingsManager.getGroup(groupName).deleteGroup();
    }

    @Override
    public String getDefaultGroup(String worldName) {
        return SettingsManager.getInstance().getDefaultGroup();
    }


    public class PermissionServerListener
            implements Listener {
        Permission_VintagePerms permission = null;

        public PermissionServerListener(Permission_VintagePerms permission) {
            this.permission = permission;
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginEnable(PluginEnableEvent event) {
            if (this.permission.permission == null) {
                Plugin perms = event.getPlugin();
                if (perms.getDescription().getName().equals("VintagePerms")) {

                    this.permission.permission = (PermsMain) perms;
                    PermsMain.instance.getLogger().info(String.format("[%s][Permission] %s hooked.", perms.getDescription().getName(), "VintagePerms"));
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginDisable(PluginDisableEvent event) {
            if (this.permission.permission != null &&
                    event.getPlugin().getDescription().getName().equals("VintagePerms")) {
                this.permission.permission = null;
                PermsMain.instance.getLogger().info(String.format("[%s][Permission] %s un-hooked.", plugin.getDescription().getName(), "VintagePerms"));
            }
        }
    }

}
