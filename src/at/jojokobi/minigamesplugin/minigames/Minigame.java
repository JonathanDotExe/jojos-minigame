package at.jojokobi.minigamesplugin.minigames;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface Minigame extends Listener{
	
	public void init (Plugin plugin);

	public void tick ();
	
	public Plugin getPlugin ();

}
