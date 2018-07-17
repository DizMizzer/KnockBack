package nl.dizmizzer.knockback.manager;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
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
public class StringManager {

    public static String prefix = ChatColor.AQUA + "[" + ChatColor.GOLD + "KnockBack" + ChatColor.AQUA +"]";
    public static String newgame = prefix + ChatColor.AQUA + " A new game has started! Do " + ChatColor.YELLOW + "/kb join" + ChatColor.AQUA + " to join!";
    public static String onwin = prefix + ChatColor.AQUA + " %player% has won the game!";
    public static String countdown = prefix + ChatColor.YELLOW + " %countdown% " + ChatColor.AQUA + " seconds until the game starts!";
    public static String gamestart = prefix + ChatColor.AQUA + " All players have received their slapping cookie!";
    public static String elimination = prefix + ChatColor.RED + " You have been eliminated and returned to spawn!";

    private StringManager() {
        //TODO add more strings
    }

    public static String placeHolder(String s, int countdown) {
        return s.replace("%countdown%", String.valueOf(countdown));
    }

    public static String placeHolder(String s, Player player) {
        return s.replace("%player%", player.getDisplayName());
    }

}
