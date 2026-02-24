package net.milkbowl.vault.items.api;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface CustomItemProvider {

    // Returns the name of the provider
    String getProviderName();

    // Returns true if the custom item exists
    boolean customItemExists(String customId);

    // Returns true if the item matches the custom item
    boolean matches(ItemStack itemStack, String customId);

    // Returns true if the item is a custom item
    boolean isCustomItem(ItemStack itemStack);

    // Returns the custom item id
    String getCustomItemId(ItemStack itemStack);

    // Returns the custom item stack
    ItemStack getCustomItemStack(String customId, int amount);

    // Returns the custom item data
    Map<String, String> getCustomItemData(ItemStack itemStack);

    // Returns true if Block is a custom item
    boolean isCustomBlock(Block block);

    // Returns all custom items
    Map<String, ItemStack> getCustomItems();

}
