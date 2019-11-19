package at.jojokobi.minigamesplugin.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CustomTeam {
	
	private ChatColor color;
	private String name;
	private String displayName;
	private boolean friendlyFire;
	private boolean canSeeFriendlyInvisible;
	
	public CustomTeam(ChatColor color, String name, String displayName, boolean friendlyFire,
			boolean canSeeFriendlyInvisible) {
		super();
		this.color = color;
		this.name = name;
		this.displayName = displayName;
		this.friendlyFire = friendlyFire;
		this.canSeeFriendlyInvisible = canSeeFriendlyInvisible;
	}

	public void initScoreboard (Scoreboard scoreboard) {
		Team team = scoreboard.registerNewTeam(name);
		team.setDisplayName(displayName);
		team.setColor(color);
		team.setAllowFriendlyFire(friendlyFire);
		team.setCanSeeFriendlyInvisibles(canSeeFriendlyInvisible);
	}
	
	public void addPlayer (OfflinePlayer player, Scoreboard scoreboard) {
		Team team = scoreboard.getTeam(name);
		team.addEntry(player.getName());
	}
	
	public void removePlayer (OfflinePlayer player, Scoreboard scoreboard) {
		Team team = scoreboard.getTeam(name);
		team.removeEntry(player.getName());
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isFriendlyFire() {
		return friendlyFire;
	}

	public void setFriendlyFire(boolean friendlyFire) {
		this.friendlyFire = friendlyFire;
	}

	public boolean isCanSeeFriendlyInvisible() {
		return canSeeFriendlyInvisible;
	}

	public void setCanSeeFriendlyInvisible(boolean canSeeFriendlyInvisible) {
		this.canSeeFriendlyInvisible = canSeeFriendlyInvisible;
	}

}
