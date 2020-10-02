package at.jojokobi.minigamesplugin.minigames.components;

import org.bukkit.event.Listener;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;

public interface GameComponent extends Listener{
	
	public void init (BaseMinigame game);

	public abstract void start ();
	
	public abstract void update ();
	
	public abstract void updateLobby ();
	
	public abstract void end ();
	
	public abstract void startLobby ();
	
}
