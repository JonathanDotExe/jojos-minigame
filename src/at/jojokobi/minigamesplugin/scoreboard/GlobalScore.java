package at.jojokobi.minigamesplugin.scoreboard;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GlobalScore<T> implements CustomScore<T, Object> {
	
	private static final String GLOBAL_OBJECTIVE = "global_objective";
	
	private T value;
	private String name;
	

	public GlobalScore(T value, String name) {
		super();
		this.value = value;
		this.name = name;
	}

	@Override
	public T get(Object e) {
		return value;
	}

	@Override
	public void set(T t, Object e) {
		value = t;
	}
	
	public void set(T t) {
		set(t, null);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void initScoreboard(CustomScoreboard cs, Scoreboard scoreboard) {
		if (scoreboard.getObjective(GLOBAL_OBJECTIVE) == null) {
			Objective obj = scoreboard.registerNewObjective(GLOBAL_OBJECTIVE, "dummy", cs.getGameName());
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
	}

	@Override
	public void updateScoreboard(CustomScoreboard cs, Scoreboard scoreboard) {
		Objective obj = scoreboard.getObjective(name);
		obj.getScore(name).setScore(Integer.parseInt(value + ""));
	}

}
