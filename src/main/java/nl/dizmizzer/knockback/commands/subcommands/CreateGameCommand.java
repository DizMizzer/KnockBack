package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GameMap;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

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
public class CreateGameCommand implements SubCommand {

    //Create a new game.
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("knockback.newgame")) return;
        if (args.length < 2) {
            Game game = new Game(UUID.randomUUID());
            if (!game.createGame()) {
                sender.sendMessage(ChatColor.RED + "Couldn't create a game!");
                return;
            }

            Bukkit.getServer().broadcastMessage(StringManager.newgame);
            return;
        }

        GameMap gameMap = null;
        for (GameMap map : Knockback.getInstance().getGameMaps()) {
            if (map.getMapName().equalsIgnoreCase(args[1]))
                gameMap = map;
        }

        if (gameMap == null) {
            sender.sendMessage(StringManager.prefix + ChatColor.RED + "Couldn't find map called '%mapname%'".replace("%mapname%", args[1]));
            return;
        }

        if (gameMap.isUsed()) {
            sender.sendMessage(StringManager.prefix + ChatColor.RED + "This map is already in use!");
            return;
        }

        Game game = new Game(UUID.randomUUID(), gameMap);
        if (!game.createGame()) {
            sender.sendMessage(ChatColor.RED + "Couldn't create a game!");
            return;
        }

        Bukkit.getServer().broadcastMessage(StringManager.newgame);
    }
}
