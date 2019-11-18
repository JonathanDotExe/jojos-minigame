package at.jojokobi.minigamesplugin.maps;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.generation.TerrainGenUtil;
import at.jojokobi.mcutil.generation.population.OreModifier;
import at.jojokobi.minigamesplugin.util.Area;

public class ForestMapGenerator implements MapGenerator {

	//private LootInventory loot = new LootInventory();

	public ForestMapGenerator() {

	}

	@Override
	public MultiTickTask generate(Area area) {
		MultiTickTask task = new MultiTickTask();
		Random random = new Random();

		// Clear
		for (int x = 0; x < area.getWidth(); x += 32) {
			for (int z = 0; z < area.getLength(); z += 32) {
				int xPos = x;
				int zPos = z;
				task.add(() -> BasicGenUtil.generateCube(area.getPos().clone(), Material.AIR,
						(int) Math.min(32, area.getWidth() - xPos), (int) area.getHeight(),
						(int) Math.min(32, area.getLength() - zPos)));
			}
		}
		
		// Landscape
		for (int x = 0; x < area.getWidth(); x += 16) {
			for (int z = 0; z < area.getLength(); z += 16) {
				int xPos = x;
				int zPos = z;
				task.add(() -> {
					int width = (int) Math.min(16, area.getWidth() - xPos);
					int length = (int) Math.min(16, area.getLength() - zPos);
					Location place = area.getPos().clone();
					BasicGenUtil.updateBlocks(BasicGenUtil.generateCubeStates(place.clone(), Material.STONE, new OreModifier(random.nextLong()), width, 32, length));
					place.add(0, 32, 0);
					//Land Cube
					int height = 4 + random.nextInt(64);
					BasicGenUtil.generateCube(place.clone(), Material.DIRT, width, height - 1, length);
					place.add(1, 0, 1);
					BasicGenUtil.generateCube(place.clone(), Material.STONE, new OreModifier(random.nextLong()), width - 2, height - 4, length - 2);
					place.add(-1, height - 1, -1);
					BasicGenUtil.generateCube(place.clone(), Material.GRASS_BLOCK, width, height - 1, length);
					place.add(0, 1, 0);
					//Decoration
					//Trees
					for (int i = 0; i < 32; i++){
						int treeX = random.nextInt(width);
						int treeZ = random.nextInt(length);
						place.setX(place.getX() + treeX);
						place.setZ(place.getZ() + treeZ);
						place.getBlock().setType(Material.AIR);
						place.getWorld().generateTree(place, TreeType.TREE);
						place.setX(place.getX() - treeX);
						place.setZ(place.getZ() - treeZ);
					}
					//Grass
					TerrainGenUtil.bonemealSpots(place.clone(), width, length, 5, 2, random.nextLong());
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

/*
		place.setX(startX);
		place.setZ(startZ);
		for (int i = 0; i < 4; i++) {
			Location loc = new Location(place.getWorld(), random.nextInt(54) - 22, 0, random.nextInt(54) - 22);
			int y = place.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
			loc.setY(y);
			StructureGeneration.GenerateDungeonTower(loc.clone(), ArenaType.CUBE);
		}
		for (int i = 0; i < 4; i++) {
			Location loc = new Location(place.getWorld(), random.nextInt(54) - 22, 0, random.nextInt(54) - 22);
			int y = place.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
			loc.setY(y);
			StructureGeneration.GenerateHouseTower(loc.clone(), ArenaType.CUBE);
		}
		for (int i = 0; i < 8; i++) {
			Location loc = new Location(place.getWorld(), random.nextInt(54) - 22, 0, random.nextInt(54) - 22);
			int y = place.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
			loc.setY(y);
			StructureGeneration.GenerateHouse(loc.clone(), ArenaType.CUBE);
		}
		for (Entity entity : place.getWorld().getEntities()) {
			if (entity.getType() != EntityType.PLAYER)
				entity.remove();
		}*/

		return task;
	}

}
