package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.game.GameMap;
import nl.dizmizzer.knockback.manager.DataManager;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
public class EditSpawnCommand implements SubCommand {

    //Edit the spawn of a certain map.
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

        if (!sender.hasPermission("knockback.spawnedit")) {
            sender.sendMessage(StringManager.prefix + ChatColor.RED + " No permission.");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(StringManager.prefix + ChatColor.RED + " /kb setspawn [name]");
            return;
        }

        GameMap map = null;
        for (GameMap gameMap : Knockback.getInstance().getGameMaps()) {
            if (gameMap.getMapName().equalsIgnoreCase(args[1])) {
                map = gameMap;
                break;
            }
        }
        if (map == null) return;

        map.setSpawn(((Player)sender).getLocation());
        sender.sendMessage(StringManager.prefix + " Changed spawn location to your location!");
        DataManager.getInstance().addMap(map);
    }
}
