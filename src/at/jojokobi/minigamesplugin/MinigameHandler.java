package at.jojokobi.minigamesplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import at.jojokobi.minigamesplugin.minigames.Minigame;

public class MinigameHandler {
	
	private List<Minigame> games = new ArrayList<>();
	
	public void start (Plugin plugin) {
		for (Minigame minigame : games) {
			Bukkit.getPluginManager().registerEvents(minigame, plugin);
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Minigame minigame : games) {
				minigame.tick();
			}
		}, 1, 1);
	}

	public boolean add(Minigame e) {
		return games.add(e);
	}

	public List<Minigame> getGames() {
		return games;
	}
	
}
