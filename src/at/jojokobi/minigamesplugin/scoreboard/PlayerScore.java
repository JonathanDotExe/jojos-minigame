package at.jojokobi.minigamesplugin.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerScore implements CustomScore<Integer, Player> {
	
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
	public Integer get(Player e) {
		return values.get(e.getUniqueId());
	}

	@Override
	public void set(Integer t, Player e) {
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
	}

	@Override
	public void updateScoreboard(CustomScoreboard cs, Scoreboard scoreboard) {
		Objective obj = scoreboard.getObjective(name);
		values.entrySet().forEach(e -> {
			obj.getScore(Bukkit.getOfflinePlayer(e.getKey()).getName()).setScore(e.getValue());
		});
	}

}
