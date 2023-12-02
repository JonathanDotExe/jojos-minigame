package at.jojokobi.minigamesplugin;

import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import at.jojokobi.minigamesplugin.maps.CaveMapGenerator;
import at.jojokobi.minigamesplugin.maps.SimpleLobbyGenerator;
import at.jojokobi.minigamesplugin.maps.SkyMapGenerator;
import at.jojokobi.minigamesplugin.maps.SnowMapGenerator;
import at.jojokobi.minigamesplugin.minigames.MurdererMinigame;
import at.jojokobi.minigamesplugin.minigames.SkySiegeMinigame;
import at.jojokobi.minigamesplugin.minigames.TeamEndlessMinigame;
import at.jojokobi.minigamesplugin.minigames.TeamTroubleMinigame;
import at.jojokobi.minigamesplugin.util.Area;

public class MinigamesPlugin extends JavaPlugin{
	
	@Override
	public void onEnable() {
		super.onEnable();
		MinigameHandler handler = new MinigameHandler();
		//Team Trouble Lobby 1
		{
			WorldCreator generator = new WorldCreator("TeamTroubleWorld");
			generator.type(WorldType.FLAT);
			generator.generatorSettings("{\"biome\":\"minecraft:the_void\", \"layers\":[{\"block\":\"minecraft:air\", \"height\":1}]}");
			World world = this.getServer().createWorld(generator);
			world.setSpawnLocation(32, 70, 32);
			world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
			handler.add(new TeamTroubleMinigame(new SnowMapGenerator(), new SimpleLobbyGenerator(), new Area(new Location(world, 0, 0, 0), 64, 256,64)));
		}
		
		//Endless Teams Lobby 1
		{
			WorldCreator generator = new WorldCreator("TeamEndlessWorld");
			generator.type(WorldType.FLAT);
			generator.generatorSettings("{\"biome\":\"minecraft:the_void\", \"layers\":[{\"block\":\"minecraft:air\", \"height\":1}]}");
			World world = this.getServer().createWorld(generator);
			world.setSpawnLocation(32, 70, 32);
			world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
			handler.add(new TeamEndlessMinigame(new SnowMapGenerator(), new SimpleLobbyGenerator(), new Area(new Location(world, 0, 0, 0), 64, 256,64)));
		}
		
		//Murderer Lobby 1
		{
			WorldCreator generator = new WorldCreator("MurdererWorld");
			generator.type(WorldType.FLAT);
			generator.generatorSettings("{\"biome\":\"minecraft:the_void\", \"layers\":[{\"block\":\"minecraft:air\", \"height\":1}]}");
			World world = this.getServer().createWorld(generator);
			world.setSpawnLocation(32, 70, 32);
			world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
			handler.add(new MurdererMinigame(new CaveMapGenerator(), new SimpleLobbyGenerator(), new Area(new Location(world, 0, 0, 0), 64, 256, 64)));
		}
		
		//Sky Siege Lobby 1
		{
			WorldCreator generator = new WorldCreator("SkySiegeWorld");
			generator.type(WorldType.FLAT);
			generator.generatorSettings("{\"biome\":\"minecraft:the_void\", \"layers\":[{\"block\":\"minecraft:air\", \"height\":1}]}");
			World world = this.getServer().createWorld(generator);
			world.setSpawnLocation(32, 70, 32);
			world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
			handler.add(new SkySiegeMinigame(new SkyMapGenerator(), new SimpleLobbyGenerator(), new Area(new Location(world, 0, 0, 0), 128, 256, 128)));
		}
		
		handler.start(this);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}

}
