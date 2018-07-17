package nl.dizmizzer.knockback.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
public interface ClickAction {

    void run(Player player, Inventory inv);
}
