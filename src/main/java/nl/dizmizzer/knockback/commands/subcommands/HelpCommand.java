package nl.dizmizzer.knockback.commands.subcommands;

import nl.dizmizzer.knockback.commands.SubCommand;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
public class HelpCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.AQUA + "----------=[" + StringManager.prefix + ChatColor.AQUA + " Commands]=----------");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.AQUA + "/kb help:" + ChatColor.YELLOW + " See all available commands.");
        sender.sendMessage(ChatColor.AQUA + "/kb join:" + ChatColor.YELLOW + " Join a running game!");
        sender.sendMessage(ChatColor.AQUA + "/kb loot:" + ChatColor.YELLOW + " See all available loot.");
        sender.sendMessage(ChatColor.AQUA + "/kb leave:" + ChatColor.YELLOW + " Leave your current game.");

        if (sender.hasPermission("knockback.admin")) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.AQUA + "/kb newgame [map]:" + ChatColor.YELLOW + " Create a new game.");
            sender.sendMessage(ChatColor.AQUA + "/kb create (name):"  + ChatColor.YELLOW + " Create a new map.");
            sender.sendMessage(ChatColor.AQUA + "/kb games:" + ChatColor.YELLOW + " Look at all available maps.");
            sender.sendMessage(ChatColor.AQUA + "/kb maps:" + ChatColor.YELLOW + " List of all available maps.");
            sender.sendMessage(ChatColor.AQUA + "/kb stop [mapID]:" + ChatColor.YELLOW + " Stop the game you are in.");
            sender.sendMessage(ChatColor.AQUA + "/kb force:" + ChatColor.YELLOW +" Force a game to start.");
            sender.sendMessage(ChatColor.AQUA + "/kb setspawn (map):" + ChatColor.YELLOW +" Change the spawn of a map.");
        }
    }
}
