package nl.dizmizzer.knockback.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

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
public class LocationSerializer {

    public static Object[] serialize(Location loc) {
        Object[] o = new Object[4];
        o[0] = loc.getWorld().getName();
        o[1] = loc.getX();
        o[2] = loc.getY();
        o[3] = loc.getZ();
        return o;
    }

    public static Location toLocation(Object[] o) {
        return new Location(Bukkit.getWorld(String.valueOf(o[0])), (double) o[1], (double) o[2], (double) o[3]);
    }
}
