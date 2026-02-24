package net.milkbowl.vault.items.plugins;

import io.th0rgal.oraxen.api.OraxenBlocks;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import io.th0rgal.oraxen.items.ItemUpdater;
import net.milkbowl.vault.items.CustomItems;
import net.milkbowl.vault.items.api.CustomItemProvider;
import io.th0rgal.oraxen.OraxenPlugin;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CustomItems_Oraxen implements CustomItemProvider {

    // WORKING

    private OraxenPlugin oraxen;
    private Plugin plugin;
    public boolean isEnabled = false;

    public CustomItems_Oraxen(Plugin plugin) {
        this.plugin = plugin;

        Plugin oraxenPlugin = plugin.getServer().getPluginManager().getPlugin("Oraxen");
        if (oraxenPlugin != null && oraxenPlugin.isEnabled()) {
            this.oraxen = (OraxenPlugin) oraxenPlugin;
            this.isEnabled = true;
            plugin.getLogger().info("[CustomItems][Oraxen] hooked.");
        }
    }

    @Override
    public String getProviderName() {
        return "Oraxen";
    }

    @Override
    public boolean customItemExists(String customId) {
        return OraxenItems.exists(customId);
    }

    @Override
    public boolean matches(ItemStack itemStack, String customId) {
        return OraxenItems.exists(itemStack);
    }

    @Override
    public boolean isCustomItem(ItemStack itemStack) {
        return OraxenItems.exists(itemStack);
    }

    @Override
    public String getCustomItemId(ItemStack itemStack) {
        return OraxenItems.getIdByItem(itemStack);
    }

    @Override
    public ItemStack getCustomItemStack(String customId, int amount) {
        ItemBuilder itemBuilder = OraxenItems.getItemById(customId);
        ItemStack customItem = itemBuilder.build();
        customItem.setAmount(amount);

        customItem =  ItemUpdater.updateItem(customItem);

        return customItem;
    }

    @Override
    public Map<String, String> getCustomItemData(ItemStack itemStack) {
        return Map.of(); // Unsure What Custom Data to Include - No Simple getData() method
    }

    @Override
    public boolean isCustomBlock(Block block) {
        return OraxenBlocks.isOraxenBlock(block) || OraxenBlocks.isOraxenChorusBlock(block) ||
                OraxenBlocks.isOraxenNoteBlock(block) || OraxenBlocks.isOraxenStringBlock(block);
    }

    @Override
    public Map<String, ItemStack> getCustomItems() {
        Map<String, ItemStack> items = new HashMap<>();
        for (Map.Entry<String, ItemBuilder> customItem : OraxenItems.getEntries()) {
            items.put(customItem.getKey(), ItemUpdater.updateItem(customItem.getValue().build()));
        }
        return items;
    }
}
