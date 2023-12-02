package at.jojokobi.minigamesplugin.minigames;

import org.bukkit.ChatColor;

import at.jojokobi.minigamesplugin.scoreboard.CustomTeam;

public class TeamWinner implements Winner {

	private CustomTeam team;
	
	public TeamWinner(CustomTeam team) {
		super();
		this.team = team;
	}

	@Override
	public String getName() {
		return team.getName();
	}

	@Override
	public ChatColor getChatColor() {
		return team.getColor();
	}
	
}
