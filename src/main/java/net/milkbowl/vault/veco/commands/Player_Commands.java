package net.milkbowl.vault.veco.commands;

import net.milkbowl.vault.Vault;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class Player_Commands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to use this command!");
            return true;
        }
        Player player = (Player) commandSender;

        if ((cmd.getName().equalsIgnoreCase("bal") || cmd.getName().equalsIgnoreCase("balance")) && player.hasPermission("vault.bal")) {
            double balance;
            if (Vault.getInstance().vaultUnlockedPresent())
                balance = Vault.getInstance().getVaultUnlocked().getBalance("VEco", player.getUniqueId()).doubleValue();
            else
                balance = Vault.getInstance().getEcon().getBalance(player);

            player.sendMessage(ChatColor.GREEN + "Balance: " + ChatColor.GOLD + "$" + balance);
        } else if (cmd.getName().equalsIgnoreCase("pay") && player.hasPermission("vault.pay")) {
            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
                return true;
            }

            double amount = Double.parseDouble(args[1]);
            OfflinePlayer target = Vault.getInstance().getServer().getOfflinePlayer(args[0]);
            try {
                if (!target.hasPlayedBefore()) {
                    player.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
            }
            catch (NoSuchElementException e) {
                player.sendMessage(ChatColor.RED + "Player not found!");
                return true;
            }

            if (Vault.getInstance().vaultUnlockedPresent()) {
                if (!Vault.getInstance().getVaultUnlocked().has("VEco", player.getUniqueId(), BigDecimal.valueOf(amount))) {
                    player.sendMessage(ChatColor.RED + "You do not have enough money to pay that amount!");
                    return true;
                }
                Vault.getInstance().getVaultUnlocked().withdraw("VEco", player.getUniqueId(), BigDecimal.valueOf(amount));
                Vault.getInstance().getVaultUnlocked().deposit("VEco", target.getUniqueId(), BigDecimal.valueOf(amount));
            } else {
                if (!Vault.getInstance().getEcon().has(player, amount)) {
                    player.sendMessage(ChatColor.RED + "You do not have enough money to pay that amount!");
                    return true;
                }
                Vault.getInstance().getEcon().withdrawPlayer(player, amount);
                Vault.getInstance().getEcon().depositPlayer(target, amount);
            }

            player.sendMessage(ChatColor.GREEN + "Paid " + target.getName() + " $" + amount);

            if(target.isOnline())
                ((Player) target).sendMessage(ChatColor.GREEN + player.getName() + " paid you $" + amount);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("pay")) {
            if (strings.length == 1) {
                return Vault.getInstance().getServer().getOnlinePlayers().stream().filter(player -> player.getName().startsWith(strings[0])).map(player -> player.getName()).toList();
            }
        }
        return List.of("0");
    }
}
