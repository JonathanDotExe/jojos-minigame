package at.jojokobi.minigamesplugin.maps;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import at.jojokobi.minigamesplugin.minigames.SkySiegeMinigame;
import at.jojokobi.minigamesplugin.stuctures.GameStructure;
import at.jojokobi.minigamesplugin.stuctures.sky.ClassicIsland;
import at.jojokobi.minigamesplugin.stuctures.sky.FarmIsland;
import at.jojokobi.minigamesplugin.stuctures.sky.JungleIsland;
import at.jojokobi.minigamesplugin.stuctures.sky.MineIsland;
import at.jojokobi.minigamesplugin.stuctures.sky.NetherIsland;
import at.jojokobi.minigamesplugin.stuctures.sky.RainbowIsland;
import at.jojokobi.minigamesplugin.stuctures.sky.SandIsland;
import at.jojokobi.minigamesplugin.stuctures.sky.SwampIsland;
import at.jojokobi.minigamesplugin.util.Area;

public class SkyMapGenerator implements MapGenerator {

	//private LootInventory loot = new LootInventory();

	public SkyMapGenerator() {

	}

	@Override
	public MultiTickTask generate(Area area) {
		MultiTickTask task = new MultiTickTask();
		Random random = new Random();

		//Clear
		MapGenerator.clear(area, task);
		
		// Landscape
		List<GameStructure> strucs = Arrays.asList(new ClassicIsland(), new FarmIsland(), new JungleIsland(), new MineIsland(), new NetherIsland(), new RainbowIsland(), new SandIsland(), new SwampIsland());
		for (int x = 0; x < area.getWidth(); x += SkySiegeMinigame.ISLAND_GRID_STEP) {
			for (int z = 0; z < area.getLength(); z += SkySiegeMinigame.ISLAND_GRID_STEP) {
				int xPos = x;
				int zPos = z;
				task.add(() -> {
					GameStructure struc = strucs.get(random.nextInt(strucs.size()));
					struc.generate(area.getPos().clone().add(xPos, 60, zPos), random);
				});
			}
		}
		//Clear world
		task.add(() -> {
			for (Entity entity : area.getPos().getWorld().getEntities()) {
				if (entity.getType() != EntityType.PLAYER)
					entity.remove();
			}
		}); 

		return task;
	}

	@Override
	public String getName() {
		return "sky";
	}

	@Override
	public String getDisplayName() {
		return "Skylands";
	}

}
