package at.jojokobi.minigamesplugin.minigames;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerWinner implements Winner {
	
	private Player player;
	private ChatColor color;

	
	public PlayerWinner(Player player, ChatColor color) {
		super();
		this.player = player;
		this.color = color;
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public ChatColor getChatColor() {
		return color;
	}

}
