package at.jojokobi.minigamesplugin.minigames;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;

import at.jojokobi.minigamesplugin.maps.MapGenerator;
import at.jojokobi.minigamesplugin.scoreboard.CustomScoreboard;
import at.jojokobi.minigamesplugin.scoreboard.CustomTeam;
import at.jojokobi.minigamesplugin.scoreboard.GlobalScore;
import at.jojokobi.minigamesplugin.scoreboard.PlayerScore;
import at.jojokobi.minigamesplugin.util.Area;

public class TeamTroubleMinigame extends BaseMinigame{

	private int gameDuration = 10 * 60 * 20;
	private int maxPlayers = 8;
	private int maxWaitTime = 20 * 20;
	private int protectionTime = 2 * 60 * 20;
	
	private GlobalScore<Integer> timerScore;
	private PlayerScore playerScore;
	
	private CustomTeam redTeam;
	private CustomTeam blueTeam;

	
	public TeamTroubleMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
		super(generator, lobbyGenerator, gameArea);
	}
	
	@Override
	public void start() {
		spreadPlayers(getScoreboard().getOnlinePlayers(), getGameArea());
	}

	@Override
	public void update() {
		if (getTime() == protectionTime && isRunning()) {
			sendGameTitle(ChatColor.YELLOW + "The protection time ends now!", "", 10, 80, 10);
			timerScore.set(getTime());
		}
	}

	@Override
	public void updateLobby() {
		
	}

	@Override
	public void end() {
		
	}

	@Override
	public boolean canGameFinish() {
		return getTime() >= protectionTime + 60 * 20 && (getTime() >= gameDuration || getScoreboard().getPlayers().isEmpty() || getScoreboard().getPlayers().stream().allMatch(p -> getScoreboard().getTeam(p) == getScoreboard().getTeam(getScoreboard().getPlayers().get(0))));
	}

	@Override
	public boolean canGameStart() {
		int players = determinePlayers().size();
		return players >= maxPlayers || (getTime() > maxWaitTime && players >= 1);
	}

	@Override
	public void initScoreboard(CustomScoreboard scoreboard) {
		//Score
		timerScore = new GlobalScore<>(0, "Time: ");
		playerScore = new PlayerScore("score", "Score", DisplaySlot.PLAYER_LIST);
		scoreboard.addScore(timerScore);
		scoreboard.addScore(playerScore);
		//Teams
		scoreboard.addTeam(redTeam = new CustomTeam(ChatColor.RED, "team_red", "Team Red", true, true));
		scoreboard.addTeam(blueTeam = new CustomTeam(ChatColor.BLUE, "team_blue", "Team Blue", true, true));
	}
	
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		if (getScoreboard() != null && getScoreboard().getOnlinePlayers().contains(event.getEntity())) {
			//Prevent damage in protection time
			if (!isRunning() || getTime() < protectionTime) {
				event.setDamage(0);
			}
			else if (event.getEntity() instanceof Player){
				Player player = (Player) event.getEntity();
				if (player.getHealth() - event.getFinalDamage() <= 0.5) {
					CustomTeam newTeam;
					//Switch team
					if (getScoreboard().getTeam(player) ==  blueTeam) {
						newTeam = redTeam;
					}
					else {
						newTeam = blueTeam;
					}
					setTeam(player, newTeam);
					sendGameTitle(newTeam.getColor() + player.getName() + " is now in " + newTeam.getDisplayName() + "!", "", 10, 80, 10);
					event.setDamage(0);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		if (event.getPlayer().getWorld() == getGameArea().getPos().getWorld()) {
			spreadPlayers(Arrays.asList(event.getPlayer()), getGameArea());
		}
	}
	
	private void sendGameTitle (String title, String subtitle, int fadeIn, int duration, int fadeOut) {
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.sendTitle(title, subtitle, fadeIn, duration, fadeOut);
		}
	}

	@Override
	public Winner determineWinner() {
		List<Player> players = getScoreboard().getOnlinePlayers();
		System.out.println(players);
		players.sort((p1, p2) -> Integer.compare(playerScore.get(p2), playerScore.get(p1)));
		return players.isEmpty() ? null : new PlayerWinner(players.get(0), getScoreboard().getTeam(players.get(0)).getColor());
	}

	@Override
	public String getName() {
		return "Team Trouble";
	}

	@Override
	public void startLobby() {
		spreadPlayers(getScoreboard().getOnlinePlayers(), getGameArea());
	}

}
