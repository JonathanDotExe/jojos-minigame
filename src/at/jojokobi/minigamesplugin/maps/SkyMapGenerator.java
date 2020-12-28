package at.jojokobi.minigamesplugin.maps;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.generation.population.OreModifier;
import at.jojokobi.minigamesplugin.stuctures.cave.CaveStructure;
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
		for (int x = 0; x < area.getWidth(); x += 16) {
			for (int z = 0; z < area.getLength(); z += 16) {
				int xPos = x;
				int zPos = z;
				task.add(() -> {
					int width = (int) Math.min(16, area.getWidth() - xPos);
					int length = (int) Math.min(16, area.getLength() - zPos);
					Location place = area.getPos().clone().add(xPos, 0, zPos);
					BasicGenUtil.generateCube(place.clone(), Material.STONE, new OreModifier(random.nextLong()), width, 32, length);
					place.add(0, 32, 0);
				});
			}
		}
		
		//Cave
		task.add(() -> {
			CaveStructure cave = new CaveStructure((int) area.getWidth(), (int) area.getLength());
			cave.generate(area.getPos().clone().add(0, 8, 0), random);
		});
		
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
