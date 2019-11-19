package at.jojokobi.minigamesplugin.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class CustomScoreboard {
	
	private String gameName;
	private Map<String, CustomTeam> teams = new HashMap<>();
	private List<CustomScore<?, ?>> scores = new ArrayList<>();
	private List<UUID> players = new ArrayList<>();
	private Map<UUID, String> playerTeamAssignments = new HashMap<>();
	
	
	public CustomScoreboard(String gameName) {
		super();
		this.gameName = gameName;
	}

	public void reset () {
		teams.clear();
		scores.clear();
		players.clear();
		playerTeamAssignments.clear();
	}
	
	public void addPlayer (OfflinePlayer player) {
		players.add(player.getUniqueId());
	}
	
	public void addTeam (CustomTeam team) {
		teams.put(team.getName(), team);
	}
	
	public void addScore (CustomScore<?, ?> score) {
		scores.add(score);
	}
	
	public void setTeam (OfflinePlayer player, CustomTeam team, Scoreboard scoreboard) {
		team.addPlayer(player, scoreboard);
		playerTeamAssignments.put(player.getUniqueId(), team.getName());
	}
	
	public CustomTeam getTeam (OfflinePlayer player) {
		return teams.get(playerTeamAssignments.get(player.getUniqueId()));
	}
	
	public void leaveTeam (OfflinePlayer player, Scoreboard scoreboard) {
		getTeam(player).removePlayer(player, scoreboard);
		playerTeamAssignments.remove(player.getUniqueId());
	}
	
	public void initScoreboard (Scoreboard scoreboard) {
		teams.forEach((k, v) -> v.initScoreboard(scoreboard));
		scores.forEach(s -> s.initScoreboard(this, scoreboard));
	}
	
	public void updateScoreboard (Scoreboard scoreboard) {
		scores.forEach(s -> s.updateScoreboard(this, scoreboard));
	}
	
	public List<CustomTeam> getTeamList() {
		return Arrays.asList(teams.entrySet().stream().map(e -> e.getValue()).toArray(CustomTeam[]::new));
	}

	public List<OfflinePlayer> getPlayers () {
		return Arrays.asList(players.stream().map(u -> Bukkit.getOfflinePlayer(u)).toArray(OfflinePlayer[]::new));
	}
	
	public List<Player> getOnlinePlayers () {
		return Arrays.asList(players.stream().map(u -> Bukkit.getPlayer(u)).filter(p -> p != null).toArray(Player[]::new));
	}

	public String getGameName () {
		return gameName;
	}

}
