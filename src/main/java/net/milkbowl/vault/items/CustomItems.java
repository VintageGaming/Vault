package net.milkbowl.vault.items;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.items.api.CustomItemProvider;
import net.milkbowl.vault.items.api.CustomItemRegistry;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CustomItems implements CustomItemRegistry {

    private static final HashMap<String, CustomItemProvider> providers = new HashMap<>();

    @Override
    public void registerProvider(CustomItemProvider provider) {
        providers.put(provider.getProviderName().toLowerCase(), provider);
        Vault.getInstance().getLogger().info("Registered Custom Item Provider: " + provider.getProviderName());
    }

    @Override
    public boolean customItemExists(String customId) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.customItemExists(customId)) return true;
        }
        return false;
    }

    @Override
    public boolean customItemExists(String customId, String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return false;
        return providers.get(providerName.toLowerCase()).customItemExists(customId);
    }

    @Override
    public boolean matches(ItemStack itemStack, String customId) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.matches(itemStack, customId)) return true;
        }
        return false;
    }

    @Override
    public boolean matches(ItemStack itemStack, String customId, String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return false;
        return providers.get(providerName.toLowerCase()).matches(itemStack, customId);
    }

    @Override
    public boolean isCustomItem(ItemStack itemStack) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.isCustomItem(itemStack)) return true;
        }
        return false;
    }

    @Override
    public boolean isCustomItem(ItemStack itemStack, String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return false;
        return providers.get(providerName.toLowerCase()).isCustomItem(itemStack);
    }

    @Override
    public String getCustomItemProvider(String customId) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.customItemExists(customId)) return provider.getProviderName();
        }
        return null;
    }

    @Override
    public String getCustomItemStackProvider(ItemStack itemStack) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.isCustomItem(itemStack)) return provider.getProviderName();
        }
        return null;
    }

    @Override
    public String getCustomItemId(ItemStack itemStack) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.isCustomItem(itemStack)) return provider.getCustomItemId(itemStack);
        }
        return null;
    }

    @Override
    public String getCustomItemId(ItemStack itemStack, String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return null;
        return providers.get(providerName.toLowerCase()).getCustomItemId(itemStack);
    }

    @Override
    public ItemStack getCustomItemStack(String customId, int amount) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.customItemExists(customId)) return provider.getCustomItemStack(customId, amount);
        }
        return null;
    }

    @Override
    public ItemStack getCustomItemStack(String customId, int amount, String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return null;
        return providers.get(providerName.toLowerCase()).getCustomItemStack(customId, amount);
    }

    @Override
    public Map<String, String> getCustomItemData(ItemStack itemStack) {
        for (CustomItemProvider provider : providers.values()) {
            if (provider.isCustomItem(itemStack)) return provider.getCustomItemData(itemStack);
        }
        return Map.of();
    }

    @Override
    public Map<String, String> getCustomItemData(ItemStack itemStack, String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return Map.of();
        return providers.get(providerName.toLowerCase()).getCustomItemData(itemStack);
    }

    @Override
    public Map<String, ItemStack> getCustomItems() {
        if (providers.isEmpty()) return Map.of();

        Map<String, ItemStack> customItems = new HashMap<>();
        for (CustomItemProvider provider : providers.values()) {
            customItems.putAll(provider.getCustomItems());
        }
        return customItems;
    }

    @Override
    public Map<String, ItemStack> getCustomItems(String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return Map.of();

        return providers.get(providerName.toLowerCase()).getCustomItems();
    }

    @Override
    public Map<String, Set<String>> getCustomItemIds() {
        if (providers.isEmpty()) return Map.of();

        Map<String, Set<String>> customItemIds = new HashMap<>();
        for (CustomItemProvider provider : providers.values()) {
            customItemIds.put(provider.getProviderName(), provider.getCustomItems().keySet());
        }
        return customItemIds;
    }

    @Override
    public Set<String> getCustomItemIds(String providerName) {
        if (!providers.containsKey(providerName.toLowerCase())) return Set.of();
        return providers.get(providerName.toLowerCase()).getCustomItems().keySet();
    }

}
