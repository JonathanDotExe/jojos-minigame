package at.jojokobi.minigamesplugin.minigames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;

import at.jojokobi.minigamesplugin.maps.MapGenerator;
import at.jojokobi.minigamesplugin.maps.MultiTickTask;
import at.jojokobi.minigamesplugin.minigames.components.GameComponent;
import at.jojokobi.minigamesplugin.scoreboard.CustomScoreboard;
import at.jojokobi.minigamesplugin.scoreboard.CustomTeam;
import at.jojokobi.minigamesplugin.util.Area;

public abstract class BaseMinigame implements Minigame {
	
	private Scoreboard scoreboardView;
	private CustomScoreboard scoreboard;
	
	private MapGenerator generator;
	private MapGenerator lobbyGenerator;
	private Area gameArea;
	
	private boolean running = false;
	private int time = 0;
	
	private MultiTickTask task;
	private List<GameComponent> components = new ArrayList<GameComponent>();
	
	private Plugin plugin;
	
	public BaseMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
		super();
		this.generator = generator;
		this.lobbyGenerator = lobbyGenerator;
		this.gameArea = gameArea;
	}

	@Override
	public void init(Plugin plugin) {
		this.plugin = plugin;
		for (GameComponent gameComponent : components) {
			gameComponent.init(this);
			Bukkit.getPluginManager().registerEvents(gameComponent, plugin);
		}
		task = generateLobby();
		task.executeAll();
		startLobby();
		components.forEach(c -> c.startLobby());
	}
	
	@Override
	public void tick() {
		if (task != null && task.hasNext()) {
			task.next();
		}
		else if (running) {
			update ();
			components.forEach(c -> c.update());
			//Check end
			if (canGameFinish()) {
				//Determine winner
				Winner winner = determineWinner();
				if (winner != null) {
					for (Player player : scoreboard.getOnlinePlayers()) {
						player.sendTitle(winner.getChatColor() + winner.getName() + " won the game!", "Congratulations!", 10, 80, 10);
					}
				}
				end();
				components.forEach(c -> c.end());
				//Start lobby
				task = generateLobby();
				task.add(() -> {
					startLobby();
					components.forEach(c -> c.startLobby());
					time = 0;
					running = false;
					for (Player player : scoreboard.getOnlinePlayers()) {
						player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
					}
					scoreboard = null;
					scoreboardView = null;
				});
				task.executeAll();
			}
		}
		else {
			updateLobby();
			components.forEach(c -> c.updateLobby());
			//Check start
			if (canGameStart()) {
				time = 0;
				running = true;
				List<Player> players = determinePlayers();
				//Start game
				task = generateArena();
				task.add(() -> {
					//Init Scoreboard
					scoreboardView = Bukkit.getScoreboardManager().getNewScoreboard();
					scoreboard = new CustomScoreboard(getName());
					//Create Fields and Teams
					initScoreboard(scoreboard);
					//Add players
					for (Player player : players) {
						scoreboard.addPlayer(player);
						player.setScoreboard(scoreboardView);
					}
					//Show scoreboard
					scoreboard.initScoreboard(scoreboardView);
					assignTeams(players, scoreboard, scoreboardView);
					start();
					components.forEach(c -> c.start());
				});
				task.executeAll();
			}
		}
		//Scoreboard Update
		if (scoreboard != null && scoreboardView != null) {
			scoreboard.updateScoreboard(scoreboardView);
		}
		time++;
	}
	
	public abstract void start ();
	
	public abstract void update ();
	
	public abstract void updateLobby ();
	
	public abstract void end ();
	
	public abstract void startLobby ();
	
	public abstract boolean canGameFinish ();
	
	public abstract boolean canGameStart ();
	
	public abstract void initScoreboard (CustomScoreboard scoreboard);
	
	public abstract Winner determineWinner ();
	
	protected void setTeam (OfflinePlayer player, CustomTeam team) {
		scoreboard.setTeam(player, team, scoreboardView);
	}
	
	protected void leaveTeam (OfflinePlayer player) {
		scoreboard.leaveTeam(player, scoreboardView);
	}
	
	protected MultiTickTask generateLobby () {
		return lobbyGenerator.generate(gameArea);
	}
	
	protected MultiTickTask generateArena () {
		return generator.generate(gameArea);
	}
	
	public List<Player> determinePlayers () {
		return gameArea.getPos().getWorld().getPlayers();
	}
	
	protected void spreadPlayers (List<Player> players, Area gameArea) {
		Random random = new Random();
		for (Player player : players) {
			player.teleport(gameArea.getPos().clone().add(random.nextDouble() * gameArea.getWidth(), gameArea.getPos().getWorld().getMaxHeight() - gameArea.getPos().getY(), random.nextDouble() * gameArea.getLength()));
		}
	}
	
	protected void resetPlayers (List<Player> players) {
		for (Player player : players) {
			resetPlayer(player);
		}
	}
	
	public void sendGameTitle (String title, String subtitle, int fadeIn, int duration, int fadeOut) {
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.sendTitle(title, subtitle, fadeIn, duration, fadeOut);
		}
	}
	
	public void sendGameMessage (String message) {
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}
	
	protected void assignTeams (List<Player> players, CustomScoreboard board, Scoreboard scoreboardView) {
		List<Player> p = new ArrayList<Player>(players);
		Collections.shuffle(p);
		List<CustomTeam> teams = board.getTeamList();
		if (teams.size() > 0) {
			Iterator<CustomTeam> iter = teams.iterator();
			for (Player player : p) {
				board.setTeam(player, iter.next(), scoreboardView);
				if (!iter.hasNext()) {
					iter = teams.iterator();
				}
			}
		}
	}
	
	protected void resetPlayer (Player player) {
		player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		player.setGameMode(GameMode.SURVIVAL);
		player.setSaturation(0);
		player.setExhaustion(0);
		player.setRemainingAir(20);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		player.setFallDistance(0);
		player.getInventory().clear();
		player.setExp(0);
		player.setLevel(0);
		for (PotionEffect e : player.getActivePotionEffects()) {
			player.removePotionEffect(e.getType());
		}
	}

	public MapGenerator getGenerator() {
		return generator;
	}

	public MapGenerator getLobbyGenerator() {
		return lobbyGenerator;
	}

	public Area getGameArea() {
		return gameArea;
	}

	public boolean isRunning() {
		return running;
	}

	public int getTime() {
		return time;
	}

	public CustomScoreboard getScoreboard() {
		return scoreboard;
	}

	public abstract String getName ();
	
	protected void addComponent (GameComponent comp) {
		components.add(comp);
	}
	
	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	public void setGenerator(MapGenerator generator) {
		this.generator = generator;
	}

	public void setLobbyGenerator(MapGenerator lobbyGenerator) {
		this.lobbyGenerator = lobbyGenerator;
	}
	
}
