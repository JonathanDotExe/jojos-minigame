package at.jojokobi.minigamesplugin.minigames;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
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
	private int maxWaitTime = 60 * 20;
	private int protectionTime = 2 * 60 * 20;
	
	private GlobalScore<Integer> timerScore;
	private PlayerScore playerScore;

	
	public TeamTroubleMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
		super(generator, lobbyGenerator, gameArea);
	}
	
	@Override
	public void start() {
		spreadPlayers(getScoreboard().getOnlinePlayers());
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateLobby() {
		
	}

	@Override
	public void end() {
		
		spreadPlayers(getScoreboard().getOnlinePlayers());
	}

	@Override
	public boolean canGameFinish() {
		return getTime() >= protectionTime && (getTime() >= gameDuration || getScoreboard().getPlayers().isEmpty() || getScoreboard().getPlayers().stream().allMatch(p -> getScoreboard().getTeam(p) == getScoreboard().getTeam(getScoreboard().getPlayers().get(0))));
	}
	


	@Override
	public boolean canGameStart() {
		int players = determinePlayers().size();
		return players >= maxPlayers || (getTime() > maxWaitTime && players >= 2);
	}

	@Override
	public void initScoreboard(CustomScoreboard scoreboard) {
		//Score
		timerScore = new GlobalScore<>(0, "Time: ");
		playerScore = new PlayerScore("score", "Score", DisplaySlot.PLAYER_LIST);
		scoreboard.addScore(timerScore);
		scoreboard.addScore(playerScore);
		//Teams
		scoreboard.addTeam(new CustomTeam(ChatColor.RED, "team_red", "Team Red", true, true));
		scoreboard.addTeam(new CustomTeam(ChatColor.BLUE, "team_blue", "Team Blue", true, true));
	}
	
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		if (getScoreboard().getOnlinePlayers().contains(event.getEntity()) && (!isRunning() || getTime() < protectionTime)) {
			event.setCancelled(true);
		}
	}

	@Override
	public Winner determineWinner() {
		List<Player> players = getScoreboard().getOnlinePlayers();
		players.sort((p1, p2) -> Integer.compare(playerScore.get(p2), playerScore.get(p1)));
		return players.isEmpty() ? null : new PlayerWinner(players.get(0), getScoreboard().getTeam(players.get(0)).getColor());
	}

}
