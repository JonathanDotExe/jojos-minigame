package at.jojokobi.minigamesplugin.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerScore implements CustomScore<Integer, OfflinePlayer> {
	
	private Map<UUID, Integer> values = new HashMap<>();
	private String name;
	private String displayName;
	private DisplaySlot slot;
	

	public PlayerScore(String name, String displayName, DisplaySlot slot) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.slot = slot;
	}

	@Override
	public Integer get(OfflinePlayer e) {
		return values.get(e.getUniqueId());
	}

	@Override
	public void set(Integer t, OfflinePlayer e) {
		values.put(e.getUniqueId(), t);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void initScoreboard(CustomScoreboard cs, Scoreboard scoreboard) {
		Objective obj = scoreboard.registerNewObjective(name, "dummy", displayName);
		obj.setDisplaySlot(slot);
		for (OfflinePlayer player : cs.getPlayers()) {
			set(0, player);
		}
	}

	@Override
	public void updateScoreboard(CustomScoreboard cs, Scoreboard scoreboard) {
		Objective obj = scoreboard.getObjective(name);
		values.entrySet().forEach(e -> {
			obj.getScore(Bukkit.getOfflinePlayer(e.getKey()).getName()).setScore(e.getValue());
		});
	}

}
