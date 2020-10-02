package at.jojokobi.minigamesplugin.minigames.components;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import at.jojokobi.minigamesplugin.MinigamesPlugin;
import at.jojokobi.minigamesplugin.minigames.BaseMinigame;

public class ChatRangeComponent implements GameComponent{

	private BaseMinigame game;
	private double range = 10;
	private boolean active = true;
	
	@Override
	public void init(BaseMinigame game) {
		this.game = game;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (active && game.isRunning() && game.getScoreboard() != null && game.getScoreboard().getPlayers().contains(event.getPlayer())) {
			//Cancel chat
			event.setCancelled(true);
			Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(MinigamesPlugin.class) /*TODO injet minigame plugin*/, () -> {
				//Survival
				if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
					for (Entity e : event.getPlayer().getNearbyEntities(range, range, range)) {
						if ((event.getRecipients().contains(e) && ((Player) e).getGameMode() == GameMode.SURVIVAL)) {
							e.sendMessage("<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
						}
					}
					event.getPlayer().sendMessage("<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
				}
				//All except survival
				for (Player player : event.getRecipients()) {
					if (player.getGameMode() != GameMode.SURVIVAL) {
						player.sendMessage("<" + event.getPlayer().getDisplayName() + "> " + event.getMessage());
					}
				}
			});
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateLobby() {
		
	}

	@Override
	public void end() {
		
	}

	@Override
	public void startLobby() {
		
	}
	
}
