package net.milkbowl.vault;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import net.milkbowl.vault.economy.plugins.Economy_CMI;
import net.milkbowl.vault.economy.plugins.Economy_Essentials;
import net.milkbowl.vault.economy.plugins.Economy_Veco;
import net.milkbowl.vault.items.api.CustomItemProvider;
import net.milkbowl.vault.items.api.CustomItemRegistry;
import net.milkbowl.vault.items.CustomItems;
import net.milkbowl.vault.items.plugins.CustomItems_ItemsAdder;
import net.milkbowl.vault.items.plugins.CustomItems_Nexo;
import net.milkbowl.vault.items.plugins.CustomItems_Oraxen;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.permission.plugins.*;
import net.milkbowl.vault.veco.Veco;
import net.milkbowl.vault.veco.commands.Player_Commands;
import net.milkbowl.vault.veco.commands.Veco_Command;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Vault extends JavaPlugin { 
   private static Logger log;
   private Permission perms;
   private Economy econ;

   private net.milkbowl.vault2.economy.Economy vaultUnlockedEconomy;
   private boolean usingVaultUnlocked = false;

   private Economy_Veco veco;
   public boolean usingVEco = false;

   private CustomItemRegistry registry;

   private String currentVersionTitle = "";
   
   private ServicesManager sm;
   
   private static Vault plugin;
   
   public void onDisable() {
       if (usingVEco)
           Veco.saveBalances();
       getServer().getServicesManager().unregisterAll(this);
       Bukkit.getScheduler().cancelTasks(this);
   }
 
   
   public void onEnable() {
       plugin = this;
       log = getLogger();
       this.currentVersionTitle = getDescription().getVersion().split("-")[0];
       this.sm = getServer().getServicesManager();

       loadCustomItems();
       loadEconomy();
       loadPermission();
     
       getCommand("vault-info").setExecutor(this);
       getCommand("vault-convert").setExecutor(this);
       getCommand("vault-test").setExecutor(this);


       getCommand("veco").setExecutor(new Veco_Command());
       getCommand("veco").setTabCompleter(new Veco_Command());

       getCommand("pay").setExecutor(new Player_Commands());
       getCommand("balance").setExecutor(new Player_Commands());
       getCommand("pay").setTabCompleter(new Player_Commands());


       // Let Other Economies Register -- Try to Prevent Race Conditions
       new BukkitRunnable() {
           @Override
           public void run() {
               if (sm.getRegistration(Economy.class) != null && !vaultUnlockedPresent())
                   return;

               veco = new Economy_Veco(getInstance());
               sm.register(Economy.class, veco, getInstance(), ServicePriority.Lowest);
               econ = veco;
               usingVEco = true;
               log.info(String.format("[Economy] Veco loaded as backup economy system."));
           }
       }.runTaskLater(this, 1L);

     
       log.info(String.format("Enabled Version %s", getDescription().getVersion()));
   }

    private void loadEconomy() {

        hookEconomy("CMI Economy", Economy_CMI.class, ServicePriority.Normal, "com.Zrips.CMI.Modules.Economy.Economy");
        hookEconomy("Essentials Economy", Economy_Essentials.class, ServicePriority.Low, "com.earth2me.essentials.api.Economy", "com.earth2me.essentials.api.NoLoanPermittedException", "com.earth2me.essentials.api.UserDoesNotExistException");
    }

    /**
     * Attempts to load Permission Addons
     */
    private void loadPermission() {

        // Try to load PermissionsEx
        hookPermission("PermissionsEx", Permission_PermissionsEx.class, ServicePriority.Highest, "ru.tehkode.permissions.bukkit.PermissionsEx");

        // Try to load GroupManager
        hookPermission("GroupManager", Permission_GroupManager.class, ServicePriority.High, "org.anjocaido.groupmanager.GroupManager");

        Permission perms = new Permission_SuperPerms(this);
        sm.register(Permission.class, perms, this, ServicePriority.Lowest);
        log.info(String.format("[Permission] SuperPermissions loaded as backup permission system."));
    }

    private void loadCustomItems() {
        registry = new CustomItems();
        sm.register(CustomItemRegistry.class, registry, this, ServicePriority.Highest);
        log.info("[CustomItems] Custom Items Bridge Loaded");

        hookCustomItems("ItemsAdder", new CustomItems_ItemsAdder(this), "dev.lone.itemsadder.api.ItemsAdder");
        hookCustomItems("Oraxen", new CustomItems_Oraxen(this), "io.th0rgal.oraxen.OraxenPlugin");
        hookCustomItems("Nexo", new CustomItems_Nexo(this), "com.nexomc.nexo.NexoPlugin");
    }

   private void hookChat(String name, Class<? extends Chat> hookClass, ServicePriority priority, String... packages) {
     try {
       if (packagesExists(packages)) {
         Chat chat = hookClass.getConstructor(new Class[] { Plugin.class, Permission.class }).newInstance(this, this.perms);
         this.sm.register(Chat.class, chat, this, priority);
         log.info(String.format("[Chat] %s found: %s", name, chat.isEnabled() ? "Loaded" : "Waiting"));
       } 
     } catch (Exception e) {
       log.severe(String.format("[Chat] There was an error hooking %s - check to make sure you're using a compatible version!", name));
     } 
   }

   private void hookEconomy(String name, Class<? extends Economy> hookClass, ServicePriority priority, String... packages) {
     try {
       if (packagesExists(packages)) {
         Economy econ = hookClass.getConstructor( Plugin.class ).newInstance(this);
         this.sm.register(Economy.class, econ, this, priority);
         log.info(String.format("[Economy] %s found: %s", name, econ.isEnabled() ? "Loaded" : "Waiting"));
         usingVEco = false;
         this.econ = econ;
       } 
     } catch (Exception e) {
       log.severe(String.format("[Economy] There was an error hooking %s - check to make sure you're using a compatible version!", name));
     } 
   }


   private void hookPermission(String name, Class<? extends Permission> hookClass, ServicePriority priority, String... packages) {
     try {
       if (packagesExists(packages)) {
         Permission perms = hookClass.getConstructor(new Class[] { Plugin.class }).newInstance(this);
         this.sm.register(Permission.class, perms, this, priority);
         log.info(String.format("[Permission] %s found: %s", name, perms.isEnabled() ? "Loaded" : "Waiting"));
         this.perms = perms;
       } 
     } catch (Exception e) {
       log.severe(String.format("[Permission] There was an error hooking %s - check to make sure you're using a compatible version!", name));
     } 
   }

   private void hookCustomItems(String name, CustomItemProvider customItemProvider, String... packages) {
        try {
            if (packagesExists(packages)) {
                registry.registerProvider(customItemProvider);
                log.info(String.format("[CustomItems][%s] hooked!", name));
            }
        } catch (Exception e) {
            log.severe(String.format("[CustomItems] There was an error hooking %s - check to make sure you're using a compatible version!", name));
        }
   }


   public Permission getPerms() {
        return perms;
   }

   public boolean vaultUnlockedPresent() {
        if (usingVaultUnlocked) return true;

        RegisteredServiceProvider<net.milkbowl.vault2.economy.Economy> vaultUnlocked =Bukkit.getServicesManager().getRegistration(net.milkbowl.vault2.economy.Economy.class);
        if (vaultUnlocked != null) {
            usingVEco = false;
            usingVaultUnlocked = true;
            vaultUnlockedEconomy = vaultUnlocked.getProvider();
            return true;
        }
        return false;
   }

   public net.milkbowl.vault2.economy.Economy getVaultUnlocked() {
        return vaultUnlockedEconomy;
   }

   public Economy getEcon() {
        // Check for other plugins first
        if (usingVEco)
            econ = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        return econ;
   }

   public static Vault getInstance() {
        return plugin;
   }

    public static UUID getUUIDFromName(String name) {
        Player onlinePlayer = Bukkit.getPlayerExact(name);
        if (onlinePlayer != null) {
            return onlinePlayer.getUniqueId();
        }

        @SuppressWarnings("deprecation")
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);

        try {
            if (offlinePlayer.hasPlayedBefore()) {
                return offlinePlayer.getUniqueId();
            }
        }
        catch (NoSuchElementException e) {
            return null;
        }

        return null;
    }
   
   public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
     if (!sender.hasPermission("vault.admin")) {
       sender.sendMessage("You do not have permission to use that command!");
       return true;
     } 
     
     if (command.getName().equalsIgnoreCase("vault-info")) {
       infoCommand(sender);
       return true;
     }  if (command.getName().equalsIgnoreCase("vault-convert")) {
       convertCommand(sender, args);
       return true;
     }
     if (command.getName().equalsIgnoreCase("vault-test")) {
         testCommand(sender, args);
         return true;
     }
     
     sender.sendMessage("Vault Commands:");
     sender.sendMessage("  /vault-info - Displays information about Vault");
     sender.sendMessage("  /vault-convert [economy1] [economy2] - Converts from one Economy to another");
     return true;
   }

   // Used to help implement/test custom item plugins
   private void testCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;


        if (args.length < 1) {
            for (String itemId : registry.getCustomItemIds("ItemsAdder")) {
                player.sendMessage("Custom Item ID: " + itemId);
            }
        }

        if (args.length == 1) {
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack hand = player.getInventory().getItemInMainHand();
                player.sendMessage("Found Item in Hand: " + registry.isCustomItem(hand));
                if (registry.isCustomItem(hand))
                    player.sendMessage("Found Custom Item Provider: " + registry.getCustomItemStackProvider(hand));
            }
            player.sendMessage("Testing CustomId: " + args[0]);
            player.sendMessage("Found Custom Item in ItemsAdder: " + registry.customItemExists(args[0], "ItemsAdder"));
            player.sendMessage("Found Custom Item: " + registry.customItemExists(args[0]));
            if (registry.customItemExists(args[0], "ItemsAdder"))
                player.getInventory().addItem(registry.getCustomItemStack(args[0], 2));
        }

   }
 
   
   private void convertCommand(CommandSender sender, String[] args) {
     Collection<RegisteredServiceProvider<Economy>> econs = getServer().getServicesManager().getRegistrations(Economy.class);
     if (econs == null || econs.size() < 2) {
       sender.sendMessage("You must have at least 2 economies loaded to convert."); return;
     } 
     if (args.length != 2) {
       sender.sendMessage("You must specify only the economy to convert from and the economy to convert to. (names should not contain spaces)");
       return;
     } 
     Economy econ1 = null;
     Economy econ2 = null;
     String economies = "";
     for (RegisteredServiceProvider<Economy> econ : econs) {
       String econName = econ.getProvider().getName().replace(" ", "");
       if (econName.equalsIgnoreCase(args[0])) {
         econ1 = econ.getProvider();
       } else if (econName.equalsIgnoreCase(args[1])) {
         econ2 = econ.getProvider();
       } 
       if (economies.length() > 0) {
         economies = economies + ", ";
       }
       economies = economies + econName;
     } 
     
     if (econ1 == null) {
       sender.sendMessage("Could not find " + args[0] + " loaded on the server, check your spelling.");
       sender.sendMessage("Valid economies are: " + economies); return;
     } 
     if (econ2 == null) {
       sender.sendMessage("Could not find " + args[1] + " loaded on the server, check your spelling.");
       sender.sendMessage("Valid economies are: " + economies);
       
       return;
     } 
     sender.sendMessage("This may take some time to convert, expect server lag.");
     for (OfflinePlayer op : Bukkit.getServer().getOfflinePlayers()) {
       if (econ1.hasAccount(op) && 
         !econ2.hasAccount(op)) {
 
         
         econ2.createPlayerAccount(op);
         double diff = econ1.getBalance(op) - econ2.getBalance(op);
         if (diff > 0.0D) {
           econ2.depositPlayer(op, diff);
         } else if (diff < 0.0D) {
           econ2.withdrawPlayer(op, -diff);
         } 
       } 
     } 
     
     sender.sendMessage("Converson complete, please verify the data before using it.");
   }
 
   
   private void infoCommand(CommandSender sender) {
     String registeredEcons = null;
     Collection<RegisteredServiceProvider<Economy>> econs = getServer().getServicesManager().getRegistrations(Economy.class);
     for (RegisteredServiceProvider<Economy> registeredServiceProvider : econs) {
       Economy e = registeredServiceProvider.getProvider();
       if (registeredEcons == null) {
         registeredEcons = e.getName(); continue;
       } 
       registeredEcons = registeredEcons + ", " + e.getName();
     } 


     String registeredPerms = null;
     Collection<RegisteredServiceProvider<Permission>> perms = getServer().getServicesManager().getRegistrations(Permission.class);
     for (RegisteredServiceProvider<Permission> registeredServiceProvider : perms) {
       Permission p = registeredServiceProvider.getProvider();
       if (registeredPerms == null) {
         registeredPerms = p.getName(); continue;
       } 
       registeredPerms = registeredPerms + ", " + p.getName();
     } 
 
     
     String registeredChats = null;
     Collection<RegisteredServiceProvider<Chat>> chats = getServer().getServicesManager().getRegistrations(Chat.class);
     for (RegisteredServiceProvider<Chat> registeredServiceProvider : chats) {
       Chat c = registeredServiceProvider.getProvider();
       if (registeredChats == null) {
         registeredChats = c.getName(); continue;
       } 
       registeredChats = registeredChats + ", " + c.getName();
     } 
 
 
     
     RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
     Economy econ = null;
     if (rsp != null) {
       econ = rsp.getProvider();
     }
     Permission perm = null;
     RegisteredServiceProvider<Permission> rspp = getServer().getServicesManager().getRegistration(Permission.class);
     if (rspp != null) {
       perm = rspp.getProvider();
     }
     Chat chat = null;
     RegisteredServiceProvider<Chat> rspc = getServer().getServicesManager().getRegistration(Chat.class);
     if (rspc != null) {
       chat = rspc.getProvider();
     }
     
     sender.sendMessage(String.format("[%s] Vault v%s Information", getDescription().getName(), getDescription().getVersion()));
     sender.sendMessage(String.format("[%s] Economy: %s [%s]", getDescription().getName(), (econ == null) ? "None" : econ.getName(), registeredEcons));
     sender.sendMessage(String.format("[%s] Permission: %s [%s]", getDescription().getName(), (perm == null) ? "None" : perm.getName(), registeredPerms));
     sender.sendMessage(String.format("[%s] Chat: %s [%s]", getDescription().getName(), (chat == null) ? "None" : chat.getName(), registeredChats));
   }
   
   private static boolean packagesExists(String... packages) {
     try {
       for (String pkg : packages) {
         Class.forName(pkg);
       }
       return true;
     } catch (Exception e) {
       return false;
     } 
   }

 }