package nl.dizmizzer.knockback.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DizMizzer.
 * Users don't have permission to release
 * the code unless stated by the Developer.
 * You are allowed to copy the source code
 * and edit it in any way, but not distribute
 * it. If you want to distribute addons,
 * please use the API. If you can't access
 * a certain thing in the API, please contact
 * the developer in contact.txt.
 */
public class GUIHolder implements InventoryHolder {

    private Map<Integer, Icon> icons = new HashMap<>();
    private int size = 9;
    private String title = "";
    private Map<Integer, ItemStack> normal = new HashMap<>();
    public GUIHolder(int size, String title) {
        this.size = size;
        this.title = title;
    }

    public void setIcon(int slot, Icon item) {
        icons.put(slot, item);
    }

    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, this.size, this.title);

        for (Map.Entry<Integer, Icon> entry : icons.entrySet()) {
            inv.setItem(entry.getKey(), entry.getValue().getItemStack());
        }

        for (Map.Entry<Integer, ItemStack> entry : normal.entrySet()) {
            if (inv.getItem(entry.getKey()) == null || inv.getItem(entry.getKey()).getType() == Material.AIR)
                inv.setItem(entry.getKey(), entry.getValue());
        }
        return inv;
    }

    public Icon getIcon(int rawSlot) {
        if (!icons.containsKey(rawSlot)) return null;
        return icons.get(rawSlot);
    }

    public int getIcons() {
        return icons.size();
    }

    public void setNormal(int normal, ItemStack i) {
        this.normal.put(normal, i);
    }

    public int getNormals() {
        return normal.size();
    }
}
