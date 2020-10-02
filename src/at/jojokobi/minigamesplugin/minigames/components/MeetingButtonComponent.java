package at.jojokobi.minigamesplugin.minigames.components;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;

public class MeetingButtonComponent implements GameComponent {

	private int duration = 30;
	private int timeoutDuration = 60;
	private int timeout = 0;
	private int endTimestamp = 0;
	
	private BaseMinigame game;
	
	@Override
	public void init(BaseMinigame game) {
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (game.getTime() >= timeout && game.getScoreboard() != null && game.getScoreboard().getPlayers().contains(event.getPlayer()) && event.getMaterial() == Material.STONE_BUTTON && event.getClickedBlock().getRelative(1, 0, 0).getType() == Material.REDSTONE_LAMP) {
			timeout = game.getTime() + timeoutDuration;
			endTimestamp = game.getTime() + duration;
			game.sendGameTitle(Color.RED + "A team meeting has been called", "by " + event.getPlayer().getDisplayName(), 10, 80, 10);
		}
	}
	
	public boolean isMeeting() {
		return game.getTime() < endTimestamp;
	}

	@Override
	public void start() {
		timeout = -1;
		endTimestamp = -1;
	}

	@Override
	public void update() {
		if (game.getTime() == endTimestamp) {
			game.sendGameTitle(Color.RED + "The meeting has ended", "", 10, 80, 10);
		}
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
