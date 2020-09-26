package at.jojokobi.minigamesplugin.minigames;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatRangeComponent implements GameComponent{

	private BaseMinigame game;
	private double range;
	
	@Override
	public void init(BaseMinigame game) {
		this.game = game;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (game.getScoreboard().getPlayers().contains(event.getPlayer())) {
			event.setCancelled(true);
			for (Entity e : event.getPlayer().getNearbyEntities(range, range, range)) {
				if (event.getRecipients().contains(e)) {
					e.sendMessage("<" + event.getPlayer() + "> " + event.getMessage());
				}
			}
		}
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
