package nl.dizmizzer.knockback.utils;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.game.GameMap;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
public class MathUtil {

    public static int timeDifference(ZonedDateTime date, int[] time) {

        int second = date.getSecond();
        int min = date.getMinute();
        int hour = date.getHour();

        int secondleft = 0;
        int steal = 0;

        if (time[2] - second >= 0) {
            secondleft = secondleft + (time[2] - second);
        } else {
            steal = 1;
            secondleft = secondleft + (60 + time[2] - second);
        }
        if (time[1] - min - steal >= 0) {
            secondleft = secondleft + (time[1] - min - steal) * 60;
            steal = 0;
        } else {
            steal = 1;
            secondleft = secondleft + (60 + time[1] - min - steal) * 60;
        }

        if (time[0] - hour - steal >= 0) {
            secondleft = secondleft + (time[0] - hour - steal) * 60 * 60;
        } else {
            secondleft = secondleft + (24 + time[0] - hour - steal) * 60 * 60;
        }

        return secondleft;
    }

    public static List<String> sortDates(List<String> dates) {
        List<String> templist = new ArrayList<>();
        for (String date : dates) {
            if (templist.size() == 0) {
                templist.add(date);
            } else {
                for (int i = 0; i < templist.size(); i++) {
                    if (Integer.parseInt(templist.get(i).split(":")[0]) >
                            Integer.parseInt(date.split(":")[0])) {
                        templist.set(i, date);
                        break;
                    }
                }
                if (!templist.contains(date)) templist.add(date);
            }
        }
        return templist;
    }

    public static int closestTime(Set<String> time) {
        List<String> times = new ArrayList<>(time);
        ZonedDateTime zdt = ZonedDateTime.now();
        for (int i = 0; i < times.size(); i++) {
            String[] date;
            date = times.get(i).split(":");

            if (Integer.parseInt(date[0]) > zdt.getHour()) {

                int[] intdate = new int[date.length];
                for (int j = 0; j < date.length; j++) {
                    intdate[j] = Integer.parseInt(date[j]);
                }
                return MathUtil.timeDifference(zdt, intdate);

            } else if (Integer.parseInt(date[0]) == zdt.getHour()) {

                if (Integer.parseInt(date[1]) > zdt.getMinute()) {
                    int[] intdate = new int[date.length];
                    for (int j = 0; j < date.length; j++) {
                        intdate[j] = Integer.parseInt(date[j]);
                    }
                    return MathUtil.timeDifference(zdt, intdate);

                } else if (Integer.parseInt(date[1]) == zdt.getMinute()) {
                    if (Integer.parseInt(date[2]) >= zdt.getSecond()) {
                        int[] intdate = new int[date.length];
                        for (int j = 0; j < date.length; j++) {
                            intdate[j] = Integer.parseInt(date[j]);
                        }
                        return MathUtil.timeDifference(zdt, intdate);
                    }
                }
            }
            if (i == times.size() - 1) {
                date = times.get(0).split(":");
                int[] intdate = new int[date.length];
                for (int j = 0; j < date.length; j++) {
                    intdate[j] = Integer.parseInt(date[j]);
                }

                return MathUtil.timeDifference(zdt, intdate);
            }
        }

        return -1;
    }


    public static GameMap getClosestTimeMap(HashMap<String, GameMap> hashmap) {

        List<String> times = new ArrayList<>(hashmap.keySet());
        ZonedDateTime zdt = ZonedDateTime.now();
        for (int i = 0; i < times.size(); i++) {
            String[] date;
            date = times.get(i).split(":");

            if (Integer.parseInt(date[0]) > zdt.getHour()) {

                return hashmap.get(i);

            } else if (Integer.parseInt(date[0]) == zdt.getHour()) {

                if (Integer.parseInt(date[1]) > zdt.getMinute()) {
                    int[] intdate = new int[date.length];
                    for (int j = 0; j < date.length; j++) {
                        intdate[j] = Integer.parseInt(date[j]);
                    }
                    return hashmap.get(i);

                } else if (Integer.parseInt(date[1]) == zdt.getMinute()) {
                    if (Integer.parseInt(date[2]) >= zdt.getSecond()) {
                        int[] intdate = new int[date.length];
                        for (int j = 0; j < date.length; j++) {
                            intdate[j] = Integer.parseInt(date[j]);
                        }
                        return hashmap.get(i);
                    }
                }
            }
            if (i == times.size() - 1) {
                date = times.get(0).split(":");
                int[] intdate = new int[date.length];
                for (int j = 0; j < date.length; j++) {
                    intdate[j] = Integer.parseInt(date[j]);
                }

                return hashmap.get(0);
            }
        }

        for (GameMap gameMap : Knockback.getInstance().getGameMaps()) {
            if (!gameMap.isUsed()) return gameMap;
        }
        return null;
    }

}
