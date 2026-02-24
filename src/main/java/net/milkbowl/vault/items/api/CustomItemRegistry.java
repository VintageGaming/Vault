package net.milkbowl.vault.items.api;

import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;

public interface CustomItemRegistry {

    void registerProvider(CustomItemProvider provider);

    boolean customItemExists(String customId);

    boolean customItemExists(String customId, String providerName);

    boolean matches(ItemStack itemStack, String customId);

    boolean matches(ItemStack itemStack, String customId, String providerName);

    boolean isCustomItem(ItemStack itemStack);

    boolean isCustomItem(ItemStack itemStack, String providerName);

    String getCustomItemProvider(String customId);

    String getCustomItemStackProvider(ItemStack itemStack);

    String getCustomItemId(ItemStack itemStack);

    String getCustomItemId(ItemStack itemStack, String providerName);

    ItemStack getCustomItemStack(String customId, int amount);

    ItemStack getCustomItemStack(String customId, int amount, String providerName);

    Map<String, String> getCustomItemData(ItemStack itemStack);

    Map<String, String> getCustomItemData(ItemStack itemStack, String providerName);

    Map<String, ItemStack> getCustomItems();

    Map<String, ItemStack> getCustomItems(String providerName);

    Map<String, Set<String>> getCustomItemIds();

    Set<String> getCustomItemIds(String providerName);

}
