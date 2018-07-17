package nl.dizmizzer.knockback.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
public class Icon {

    private ItemStack itemStack = null;
    private List<ClickAction> clickActions = new ArrayList<>();

    public Icon(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Icon addAction(ClickAction ca) {
        this.clickActions.add(ca);
        return this;
    }

    public List<ClickAction> getClickActions() {
        return this.clickActions;
    }

    public void execute(Player player, Inventory inv) {
        for (ClickAction ca : this.clickActions)
            ca.run(player, inv);
    }

}
