package at.jojokobi.minigamesplugin.minigames.components;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;

public class MeetingButtonComponent implements GameComponent {

	private int duration = 30 * 20;
	private int timeoutDuration = 60 * 20;
	private int timeout = 0;
	private int endTimestamp = 0;
	
	private BaseMinigame game;
	private ChatRangeComponent chat;
	
	
	public MeetingButtonComponent(ChatRangeComponent chat) {
		super();
		this.chat = chat;
	}

	@Override
	public void init(BaseMinigame game) {
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (game.getTime() >= timeout && game.getScoreboard() != null && game.getScoreboard().getPlayers().contains(event.getPlayer()) && event.getClickedBlock().getType() == Material.STONE_BUTTON && event.getClickedBlock().getRelative(0, 0, 1).getType() == Material.REDSTONE_LAMP) {
			timeout = game.getTime() + timeoutDuration;
			endTimestamp = game.getTime() + duration;
			game.sendGameTitle(ChatColor.RED + "A team meeting has been called", "by " + event.getPlayer().getDisplayName(), 10, 80, 10);
			chat.setActive(false);
		}
	}
	
	public boolean isMeeting() {
		return game.getTime() < endTimestamp;
	}

	@Override
	public void start() {
		timeout = -1;
		endTimestamp = -1;
		chat.setActive(true);
	}

	@Override
	public void update() {
		if (game.getTime() == endTimestamp) {
			game.sendGameTitle(ChatColor.RED + "The meeting has ended", "", 10, 80, 10);
			chat.setActive(true);
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
		chat.setActive(true);
	}

}
