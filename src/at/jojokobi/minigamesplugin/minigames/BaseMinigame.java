package at.jojokobi.minigamesplugin.minigames;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
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
	
	public BaseMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
		super();
		this.generator = generator;
		this.lobbyGenerator = lobbyGenerator;
		this.gameArea = gameArea;
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
				//Detrmine winner
				Winner winner = determineWinner();
				if (winner != null) {
					for (Player player : scoreboard.getOnlinePlayers()) {
						player.sendTitle(winner.getChatColor() + winner.getName() + " won the game!", "Congratulations!", 10, 80, 10);
					}
				}
				end();
				time = 0;
				running = false;
				for (Player player : scoreboard.getOnlinePlayers()) {
					player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
				}
				scoreboard = null;
				scoreboardView = null;
				//Start lobby
				task = generateArena();
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
				task = generateLobby();
				task.add(() -> {
					//Init Scoreboard
					scoreboardView = Bukkit.getScoreboardManager().getNewScoreboard();
					scoreboard = new CustomScoreboard();
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
					assignTeams(players, scoreboard);
					//Show scoreboard
					scoreboard.initScoreboard(scoreboardView);
					start();
				});
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
	
	public abstract boolean canGameFinish ();
	
	public abstract boolean canGameStart ();
	
	public abstract void initScoreboard (CustomScoreboard scoreboard);
	
	public abstract Winner determineWinner ();
	
	protected MultiTickTask generateLobby () {
		return lobbyGenerator.generate(gameArea);
	}
	
	protected MultiTickTask generateArena () {
		return generator.generate(gameArea);
	}
	
	protected List<Player> determinePlayers () {
		return gameArea.getPlayersInArea();
	}
	
	protected void spreadPlayers (List<Player> players) {
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
	
	protected void assignTeams (List<Player> players, CustomScoreboard board) {
		List<CustomTeam> teams = board.getTeamList();
		if (teams.size() > 0) {
			Iterator<CustomTeam> iter = teams.iterator();
			for (Player player: players) {
				board.setTeam(player, iter.next());
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
	
}
