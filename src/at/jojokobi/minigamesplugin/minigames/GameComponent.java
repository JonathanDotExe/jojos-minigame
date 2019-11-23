package at.jojokobi.minigamesplugin.minigames;

import org.bukkit.event.Listener;

public interface GameComponent extends Listener{

	public abstract void start ();
	
	public abstract void update ();
	
	public abstract void updateLobby ();
	
	public abstract void end ();
	
	public abstract void startLobby ();
	
}
