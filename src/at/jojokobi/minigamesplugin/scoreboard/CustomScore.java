package at.jojokobi.minigamesplugin.scoreboard;

import org.bukkit.scoreboard.Scoreboard;

public interface CustomScore<T, E> {
	
	public T get(E e);
	
	public void set (T t, E e);
	
	public String getName ();
	
	public void initScoreboard (CustomScoreboard cs, Scoreboard scoreboard);
	
	public void updateScoreboard (CustomScoreboard cs, Scoreboard scoreboard);

}
