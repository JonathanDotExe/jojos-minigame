package at.jojokobi.minigamesplugin.minigames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import at.jojokobi.minigamesplugin.maps.MapGenerator;

public class MapSwitchComponent implements GameComponent{
	
	private Map<String, MapGenerator> maps = new HashMap<String, MapGenerator>();
	private BaseMinigame minigame;
	private Map<UUID, String> votes = new HashMap<UUID, String>();
	private List<String> possibleMaps = new ArrayList<>();
	
	public MapSwitchComponent(List<MapGenerator> maps) {
		for (MapGenerator map : maps) {
			this.maps.put(map.getName(), map);
		}
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		if (event.getPlayer().getWorld() == minigame.getGameArea().getPos().getWorld() && !minigame.isRunning()) {
			informPlayer(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onAsyncPlayerChat (AsyncPlayerChatEvent event) {
		if (possibleMaps.contains(event.getMessage())) {
			event.setCancelled(true);
			Bukkit.getScheduler().runTask(minigame.getPlugin(), () -> {
				Player player = event.getPlayer();
				if (minigame.determinePlayers().contains(player)) {
					votes.put(player.getUniqueId(), event.getMessage());
					MapGenerator gen = maps.get(event.getMessage());
					for (Player pl : minigame.determinePlayers()) {
						pl.sendMessage(player.getDisplayName() + " voted for " + gen.getDisplayName() + "!");
					}
					var counts = new ArrayList<>(countVotes().entrySet());
					counts.sort((l, r) -> Integer.compare(r.getValue(), l.getValue())); 
					minigame.setGenerator(maps.get(counts.get(0).getKey()));
				}
			});
		}
	}
	
	@Override
	public void init(BaseMinigame game) {
		this.minigame = game;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLobby() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startLobby() {
		//New Map
		possibleMaps.clear();
		possibleMaps.addAll(maps.keySet());
		Random random = new Random();
		while (possibleMaps.size() > 2) {
			possibleMaps.remove(random.nextInt(possibleMaps.size()));
		}
		//Clear votes
		votes.clear();
		for (Player player : minigame.getGameArea().getPos().getWorld().getPlayers()) {
			informPlayer(player);
		}
	}
	
	private void informPlayer (Player player) {
		player.sendMessage("You can now vote for the next map!");
		for (String map : possibleMaps) {
			player.sendMessage("Answer with " + map + " for " + maps.get(map).getDisplayName() + "!");
		}
	}
	
	private Map<String, Integer> countVotes () {
		Map<String, Integer> amounts = new HashMap<String, Integer>();
		for (UUID player : votes.keySet()) {
			String map = votes.get(player);
			if (possibleMaps.contains(map)) {
				if (amounts.containsKey(map)) {
					amounts.put(map, amounts.get(map) + 1);
				}
				else {
					amounts.put(map, 1);
				}
			}
		}
		return amounts;
	}

}
