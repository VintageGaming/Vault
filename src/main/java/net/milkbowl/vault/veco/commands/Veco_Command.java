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

import java.util.List;
import java.util.NoSuchElementException;

public class Veco_Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!commandSender.hasPermission("vault.admin")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        if (args.length != 3) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /veco <give/take> <player> <amount>");
            return true;
        }

        double amount = Double.parseDouble(args[2]);
        OfflinePlayer target = Vault.getInstance().getServer().getOfflinePlayer(args[1]);
        try {
            if (!target.hasPlayedBefore()) {
                commandSender.sendMessage(ChatColor.RED + "Player not found!");
                return true;
            }
        }
        catch (NoSuchElementException e) {
            commandSender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "give":
                if (Vault.getInstance().usingVEco) {
                    Vault.getInstance().getVeco().depositPlayer(target, amount);
                } else {
                    Vault.getInstance().getEcon().depositPlayer(target, amount);
                }
                if (target.isOnline())
                    ((Player) target).sendMessage(ChatColor.GREEN + "You have been given $" + amount);
                commandSender.sendMessage(ChatColor.GREEN + "Given $" + amount + " to " + target.getName());
                break;
            case "take":
                if (Vault.getInstance().usingVEco) {
                    Vault.getInstance().getVeco().withdrawPlayer(target, amount);
                } else {
                    Vault.getInstance().getEcon().withdrawPlayer(target, amount);
                }
                commandSender.sendMessage(ChatColor.GREEN + "Took $" + amount + " from " + target.getName());
                break;
            default:
                commandSender.sendMessage(ChatColor.RED + "Usage: /veco <give/take> <player> <amount>");
                break;
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!commandSender.hasPermission("vault.admin")) return null;
        if (args.length == 1) {
            return List.of("give", "take");
        }
        else if (args.length == 2) {
            return Vault.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).filter(name -> name.startsWith(args[1])).toList();
        }
        return List.of("0");
    }
}
