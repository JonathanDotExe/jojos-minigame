package at.jojokobi.minigamesplugin.minigames;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;

import at.jojokobi.minigamesplugin.maps.MapGenerator;
import at.jojokobi.minigamesplugin.maps.MultiTickTask;
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
	
	

	public BaseMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
		super();
		this.generator = generator;
		this.lobbyGenerator = lobbyGenerator;
		this.gameArea = gameArea;
	}

	@Override
	public void init(Plugin plugin) {
		for (GameComponent gameComponent : components) {
			Bukkit.getPluginManager().registerEvents(gameComponent, plugin);
		}
		task = generateLobby();
		task.executeAll();
	}
	
	@Override
	public void tick() {
		if (task != null && task.hasNext()) {
			task.next();
		}
		else if (running) {
			update ();
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
				//Start lobby
				task = generateLobby();
				task.add(() -> {
					startLobby();
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
					for (Player player : players) {
						scoreboard.addPlayer(player);
						player.setScoreboard(scoreboardView);
					}
					//Create Fields and Teams
					initScoreboard(scoreboard);
					//Add players
					for (Player player : players) {
						scoreboard.addPlayer(player);
					}
					//Show scoreboard
					scoreboard.initScoreboard(scoreboardView);
					assignTeams(players, scoreboard, scoreboardView);
					start();
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
	
	@EventHandler
	public void onPlayerMove (PlayerMoveEvent event) {
		Location to = event.getTo();
		if (to.getY() < 0){
			Random random = new Random();
			Location place = new Location(to.getWorld(), random.nextInt(60) - 30, 100, random.nextInt(60) - 30);
			place.setY(place.getWorld().getHighestBlockYAt(place) + 1);
			event.setTo(place);
		}
	}
	
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
	
	protected List<Player> determinePlayers () {
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
	
	protected void assignTeams (List<Player> players, CustomScoreboard board, Scoreboard scoreboardView) {
		List<CustomTeam> teams = board.getTeamList();
		if (teams.size() > 0) {
			Iterator<CustomTeam> iter = teams.iterator();
			for (Player player: players) {
				board.setTeam(player, iter.next(), scoreboardView);
				if (!iter.hasNext()) {
					iter = teams.iterator();
				}
			}
		}
	}
	
	protected void resetPlayer (Player player) {
		player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		player.setSaturation(0);
		player.setExhaustion(0);
		player.setRemainingAir(10);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		player.setFallDistance(0);
		player.getInventory().clear();
		player.setExp(0);
		player.setLevel(0);
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
	
}
