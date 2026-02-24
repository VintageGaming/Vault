package net.milkbowl.vault.items.plugins;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import net.milkbowl.vault.items.api.CustomItemProvider;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CustomItems_ItemsAdder implements CustomItemProvider {

    ItemsAdder itemsAdder;
    private Plugin plugin;

    public CustomItems_ItemsAdder(Plugin plugin) {
        this.plugin = plugin;

        Plugin itemsAdderPlugin = plugin.getServer().getPluginManager().getPlugin("ItemsAdder");
        if (itemsAdderPlugin != null && itemsAdderPlugin.isEnabled()) {
            this.itemsAdder = (ItemsAdder) itemsAdderPlugin;
            plugin.getLogger().info("[CustomItems][ItemsAdder] hooked.");
        }
    }

    @Override
    public String getProviderName() {
        return "ItemsAdder";
    }

    @Override
    public boolean customItemExists(String customId) {
        return CustomStack.isInRegistry(customId);
    }

    @Override
    public boolean matches(ItemStack itemStack, String customId) {
        return CustomStack.byItemStack(itemStack).getId().equalsIgnoreCase(customId);
    }

    @Override
    public boolean isCustomItem(ItemStack itemStack) {
        return CustomStack.byItemStack(itemStack) != null;
    }

    @Override
    public String getCustomItemId(ItemStack itemStack) {
        return CustomStack.byItemStack(itemStack).getId();
    }

    @Override
    public ItemStack getCustomItemStack(String customId, int amount) {
        CustomStack customItem = CustomStack.getInstance(customId);
        if (customItem == null) return null;

        ItemStack itemStack = customItem.getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    @Override
    public Map<String, String> getCustomItemData(ItemStack itemStack) {
        return Map.of(); //Unsure What Custom Data to Include - No Simple getData() method
    }

    @Override
    public boolean isCustomBlock(Block block) {
        return CustomBlock.byAlreadyPlaced(block) != null;
    }

    @Override
    public Map<String, ItemStack> getCustomItems() {
        Map<String, ItemStack> customItems = new HashMap<>();
        for (String customItem : CustomStack.getNamespacedIdsInRegistry()) {
            customItems.put(customItem, CustomStack.getInstance(customItem).getItemStack());
        }
        return customItems;
    }
}
