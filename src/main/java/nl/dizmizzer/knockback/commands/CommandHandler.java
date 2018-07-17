package nl.dizmizzer.knockback.commands;

import nl.dizmizzer.knockback.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

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

public class CommandHandler implements CommandExecutor {

    private static HashMap<String, SubCommand> commands = new HashMap<>();

    public CommandHandler() {
        commands.put("create", new CreateCommand());
        commands.put("newgame", new CreateGameCommand());
        commands.put("join", new JoinCommand());
        commands.put("force", new ForceStartCommand());
        commands.put("stop", new StopCommand());
        commands.put("loot", new LootCommand());
        commands.put("games", new GamesCommand());
        commands.put("help", new HelpCommand());
        commands.put("maps", new MapsCommand());
        commands.put("leave", new LeaveCommand());
        commands.put("setspawn", new EditSpawnCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            commands.get("help").execute(sender, args);
        } else {
            if (commands.containsKey(args[0])) {
                commands.get(args[0]).execute(sender, args);
                return true;
            }
            commands.get("help").execute(sender, args);
        }

        return true;
    }
}
