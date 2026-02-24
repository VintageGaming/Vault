package net.milkbowl.vault.items.plugins;

import com.nexomc.nexo.NexoPlugin;
import com.nexomc.nexo.api.NexoBlocks;
import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import net.milkbowl.vault.items.api.CustomItemProvider;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CustomItems_Nexo implements CustomItemProvider {

    private NexoPlugin nexo;
    private Plugin plugin;

    public CustomItems_Nexo(Plugin plugin) {
        this.plugin = plugin;
        if (plugin.getServer().getPluginManager().getPlugin("Nexo") != null) {
            this.nexo = (NexoPlugin) plugin.getServer().getPluginManager().getPlugin("Nexo");
            plugin.getLogger().info("[CustomItems][Nexo] hooked.");
        }
    }

    @Override
    public String getProviderName() {
        return "Nexo";
    }

    @Override
    public boolean customItemExists(String customId) {
        return NexoItems.exists(customId);
    }

    @Override
    public boolean matches(ItemStack itemStack, String customId) {
        if (!customItemExists(customId)) return false;
        return NexoItems.isSameId(NexoItems.itemFromId(customId).getFinalItemStack(), itemStack);
    }

    @Override
    public boolean isCustomItem(ItemStack itemStack) {
        return NexoItems.exists(itemStack);
    }

    @Override
    public String getCustomItemId(ItemStack itemStack) {
        return NexoItems.idFromItem(itemStack);
    }

    @Override
    public ItemStack getCustomItemStack(String customId, int amount) {
        if (!customItemExists(customId)) return null;
        return NexoItems.itemFromId(customId).getFinalItemStack();
    }

    @Override
    public Map<String, String> getCustomItemData(ItemStack itemStack) {
        return Map.of();
    }

    @Override
    public boolean isCustomBlock(Block block) {
        return NexoBlocks.isCustomBlock(block);
    }

    @Override
    public Map<String, ItemStack> getCustomItems() {
        Map<String, ItemStack> CustomItems = new HashMap<>();
        for (Map.Entry<String, ItemBuilder> customItem : NexoItems.entries().entrySet()) {
            CustomItems.put(customItem.getKey(), customItem.getValue().getFinalItemStack());
        }
        return CustomItems;
    }
}
