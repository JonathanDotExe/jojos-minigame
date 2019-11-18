package at.jojokobi.minigamesplugin;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import at.jojokobi.minigamesplugin.maps.ForestMapGenerator;
import at.jojokobi.minigamesplugin.maps.SimpleLobbyGenerator;
import at.jojokobi.minigamesplugin.minigames.TeamTroubleMinigame;
import at.jojokobi.minigamesplugin.util.Area;

public class MinigamesPlugin extends JavaPlugin{
	
	@Override
	public void onEnable() {
		super.onEnable();
		MinigameHandler handler = new MinigameHandler();
		WorldCreator generator = new WorldCreator("TeamTroubleWorld");
		generator.type(WorldType.FLAT);
		generator.generatorSettings("3;minecraft:air;127;");
		World world = this.getServer().createWorld(generator);
		world.setSpawnLocation(32, 70, 32);
		handler.add(new TeamTroubleMinigame(new ForestMapGenerator(), new SimpleLobbyGenerator(), new Area(new Location(world, 0, 0, 0), 64, 256,64)));
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}

}
