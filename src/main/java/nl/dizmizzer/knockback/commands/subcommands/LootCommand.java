package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.inventory.ClickAction;
import nl.dizmizzer.knockback.inventory.GUIHolder;
import nl.dizmizzer.knockback.inventory.Icon;
import nl.dizmizzer.knockback.manager.ConfigManager;
import nl.dizmizzer.knockback.manager.DataManager;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
public class LootCommand implements SubCommand {

    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;
        if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("edit")) {
                if (sender.hasPermission("knockback.edit")) {
                    Icon addItem = new Icon(buildItem(Material.BOOK, ChatColor.GREEN + "Save!"));
                    addItem.addAction((player, inv) -> {
                        ItemStack[] items = inv.getContents();
                        items[inv.getSize() - 1] = null;
                        List<ItemStack> newItems = new ArrayList<>();
                        for (ItemStack item : items) {
                            if (item != null) newItems.add(item);
                        }

                        DataManager.getInstance().saveLoot(newItems.toArray(new ItemStack[newItems.size()]));
                        player.sendMessage(StringManager.prefix + ChatColor.AQUA + " Items are saved!");
                        player.closeInventory();
                    });
                    GUIHolder holder = new GUIHolder(6 * 9, "Insert items:");
                    holder.setIcon(53, addItem);
                    for (ItemStack i : DataManager.getInstance().getLoot()) {
                        holder.setNormal(holder.getIcons() + holder.getNormals() - 1, i);
                    }

                    ((Player) sender).openInventory(holder.getInventory());
                    return;
                }
            }
        }

        GUIHolder holder = new GUIHolder(6*9, "Available loot");
        for (ItemStack i : DataManager.getInstance().getLoot()) {
            holder.setNormal(holder.getNormals(), i);
        }

        ((Player)sender).openInventory(holder.getInventory());
    }

    private ItemStack buildItem(Material m, String s) {
        ItemStack is = new ItemStack(m);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(s);
        is.setItemMeta(im);
        return is;
    }

}
