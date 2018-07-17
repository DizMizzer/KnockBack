package nl.dizmizzer.knockback.game;

import nl.dizmizzer.knockback.Debug;
import nl.dizmizzer.knockback.Knockback;
import nl.dizmizzer.knockback.Scheduler.GameCountDownScheduler;
import nl.dizmizzer.knockback.Scheduler.GameSchedule;
import nl.dizmizzer.knockback.Scheduler.GameLoadScheduler;
import nl.dizmizzer.knockback.exceptions.WrongStateException;
import nl.dizmizzer.knockback.manager.CacheManager;
import nl.dizmizzer.knockback.manager.ConfigManager;
import nl.dizmizzer.knockback.manager.DataManager;
import nl.dizmizzer.knockback.manager.StringManager;
import nl.dizmizzer.knockback.utils.InventoryStringDeSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class Game implements Debug {

    private List<GamePlayer> gamePlayers = new ArrayList<>();
    private GamePlayer winner;
    private UUID gameid;
    private Location spawn;
    private int maxheight;
    private GameState gameState;
    private GameMap gameMap;
    private GameLoadScheduler gameLoadScheduler;
    private boolean forcestart = false;

    public Game(UUID uuid) {
        this.gameid = uuid;
        this.gameState = GameState.CREATED;
    }

    public Game(UUID uuid, GameMap map) {
        this.gameid = uuid;
        this.gameState = GameState.CREATED;
        setMap(map);
    }

    public Game(UUID uuid, Location location) {
        this.gameid = uuid;
        this.gameState = GameState.CREATED;
        this.spawn = location;
    }

    public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public GamePlayer getWinner() {
        return winner;
    }

    public void setWinner(GamePlayer winner) {
        this.winner = winner;
    }

    public UUID getGameid() {
        return gameid;
    }

    public void setGameid(UUID gameid) {
        this.gameid = gameid;
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getMaxheight() {
        return maxheight;
    }

    public void setMaxheight(int maxheight) {
        this.maxheight = maxheight;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void addPlayer(GamePlayer gamePlayer) {
        Knockback.debug(this, "DEBUG 1");
        if (gamePlayer.getGame() != null) {
            Knockback.debug(this, "DEBUG 2");
            getGamePlayers().add(gamePlayer);
        }
    }


    public void startGame(boolean force) {
        this.forcestart = force;
        try {
            gameLoadScheduler = new GameLoadScheduler(this, 15);
            gameLoadScheduler.runTaskTimer(Knockback.getInstance(), 0, 20);
        } catch (WrongStateException e) {
            e.printStackTrace();
        }
    }

    public void stopCountDown(GameSchedule schedule, GameStopReason reason) {
        switch (reason) {
            case FORCE:
                schedule.stopCountdown();
                break;
            case NOTENOUGH:
                if (!forcestart)
                    schedule.stopCountdown();
                break;
            default:
                if (!forcestart)
                schedule.stopCountdown();
                break;
        }
    }

    public boolean createGame() {
        if (getGameMap() == null) {
            for (GameMap gameMap : Knockback.getInstance().getGameMaps()) {
                if (gameMap.isUsed()) {
                    continue;
                }
                setMap(gameMap);
                gameMap.setGame(this);
                break;
            }
        }

        if (getGameMap() == null) {
            Bukkit.getServer().getLogger().info("No maps available! Waiting until a map is finished!");
            return false;
        }

        setGameState(GameState.LOBBY);
        Knockback.getInstance().addRunningGame(this);
        setSpawn(getGameMap().getSpawn());
        setMaxheight(getGameMap().getMaxHeight());
        return true;
    }

    public void finishGame(boolean force) {
        if (force) {
            for (GamePlayer gamePlayer : getGamePlayers()) {
                gamePlayer.setGame(null);
                gamePlayer.sendMessage(StringManager.prefix + " Game has been stopped!");
                gamePlayer.teleport(ConfigManager.getInstance().getSpawn());
                gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
                gamePlayer.getPlayer().getInventory().clear();
                try {
                    gamePlayer.getPlayer().getInventory().setContents(InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getInventory(gamePlayer.getPlayer().getUniqueId())).getContents());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ItemStack[] itemStacks = null;
                try {
                    itemStacks = InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getArmor(gamePlayer.getPlayer().getUniqueId())).getContents();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                List<ItemStack> armor = new ArrayList<>();
                for (ItemStack im : itemStacks) {
                    if (im != null)
                        armor.add(im);
                }

                gamePlayer.getPlayer().getInventory().setArmorContents(armor.toArray(new ItemStack[armor.size()]));
                CacheManager.getInstance().deletePlayerCache(gamePlayer.getPlayer().getUniqueId());
                gamePlayer.getPlayer().updateInventory();

            }
            getGamePlayers().clear();
            getGameMap().setGame(null);
            Knockback.getInstance().deleteRunningGame(this);
            return;
        }
        if (getGamePlayers().size() == 1) {
            setWinner(getGamePlayers().get(0));
        }
        if (getGamePlayers().size() == 0) {
            setWinner(null);
        }

        if (getWinner() != null) {
            getWinner().sendMessage(StringManager.placeHolder(StringManager.onwin, winner.getPlayer()));
            getWinner().teleport(ConfigManager.getInstance().getSpawn());
            getWinner().setGame(null);
            getWinner().getPlayer().getInventory().clear();
            try {
                getWinner().getPlayer().getInventory().setContents(InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getInventory(winner.getPlayer().getUniqueId())).getContents());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            ItemStack[] itemStacks = null;
            try {
                itemStacks = InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getArmor(winner.getPlayer().getUniqueId())).getContents();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            List<ItemStack> armor = new ArrayList<>();
            for (ItemStack im : itemStacks) {
                if (im != null)
                    armor.add(im);
            }

            getWinner().getPlayer().getInventory().setArmorContents(armor.toArray(new ItemStack[armor.size()]));
            getWinner().getPlayer().getInventory().addItem(DataManager.getInstance().getLoot());
            CacheManager.getInstance().deletePlayerCache(getWinner().getPlayer().getUniqueId());
            getWinner().getPlayer().updateInventory();
            getWinner().getPlayer().setGameMode(GameMode.SURVIVAL);
        }
        setGameState(GameState.POSTGAME);
        getGameMap().setGame(null);
        Knockback.getInstance().deleteRunningGame(this);
    }

    public void removePlayer(GamePlayer gamePlayer) {
        if (getGamePlayers().contains(gamePlayer)) {
            getGamePlayers().remove(gamePlayer);
        }

        gamePlayer.setGame(null);
        gamePlayer.sendMessage(StringManager.prefix + " Game has been stopped!");
        gamePlayer.teleport(ConfigManager.getInstance().getSpawn());
        gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
        gamePlayer.getPlayer().getInventory().clear();
        try {
            gamePlayer.getPlayer().getInventory().setContents(InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getInventory(gamePlayer.getPlayer().getUniqueId())).getContents());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ItemStack[] itemStacks = null;
        try {
            itemStacks = InventoryStringDeSerializer.inventoryFromBase64(CacheManager.getInstance().getArmor(gamePlayer.getPlayer().getUniqueId())).getContents();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        List<ItemStack> armor = new ArrayList<>();
        Knockback.debug(this, armor.size() + " 276");
        Knockback.debug(this, itemStacks.length  + " 277");
        assert itemStacks != null;
        for (ItemStack im : itemStacks) {
            if (im != null)
                armor.add(im);
        }

        gamePlayer.getPlayer().getInventory().setArmorContents(armor.toArray(new ItemStack[armor.size()]));
        CacheManager.getInstance().deletePlayerCache(gamePlayer.getPlayer().getUniqueId());
        gamePlayer.getPlayer().updateInventory();

        gamePlayer.setGame(null);
        gamePlayer.teleport(ConfigManager.getInstance().getSpawn());
    }

    public int getMaxPlayers() {
        return 12;
    }

    public int getMinPlayers() {
        return 2;
    }

    @Override
    public String getClassName() {
        return getClass().getName();
    }
}
