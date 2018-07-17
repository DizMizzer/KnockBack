package nl.dizmizzer.knockback.Scheduler;

import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.exceptions.WrongStateException;
import nl.dizmizzer.knockback.game.Game;
import nl.dizmizzer.knockback.game.GamePlayer;
import nl.dizmizzer.knockback.game.GameState;
import nl.dizmizzer.knockback.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

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
public class GameLoadScheduler extends BukkitRunnable implements GameSchedule {

    private Game game;
    private int countdown;

    //GameState LOBBY -> GameState PREGAME
    public GameLoadScheduler(Game g, int time) throws WrongStateException {
        if (g.getGameState() != GameState.LOBBY) throw new WrongStateException();
        this.game = g;
        this.countdown = time;
    }

    public void run() {
        countdown--;
        for (GamePlayer gamePlayer : game.getGamePlayers()) {
            if (countdown == 30 || countdown == 15 || countdown == 10 || countdown <= 5) {
                gamePlayer.sendMessage(StringManager.placeHolder(StringManager.countdown, countdown));
            }

            if (countdown == 0) {
                game.setGameState(GameState.PREGAME);
                new GameCountDownScheduler(game, 5).runTaskTimer(Knockback.getInstance(), 0, 20);
                cancel();
            }
        }
    }

    @Override
    public void stopCountdown() {
        if (countdown > 0) {
            for (GamePlayer gamePlayer : game.getGamePlayers()) {
                gamePlayer.sendMessage(StringManager.prefix + ChatColor.AQUA + "Countdown has been stopped!");
            }

            cancel();
        }
    }
}
