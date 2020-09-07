package at.jojokobi.minigamesplugin.maps;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.generation.population.OreModifier;
import at.jojokobi.mcutil.loot.LootItem;
import at.jojokobi.minigamesplugin.stuctures.DungeonTower;
import at.jojokobi.minigamesplugin.stuctures.House;
import at.jojokobi.minigamesplugin.stuctures.HouseTower;
import at.jojokobi.minigamesplugin.util.Area;

public class SnowMapGenerator implements MapGenerator {

	@Override
	public MultiTickTask generate(Area area) {
		MultiTickTask task = new MultiTickTask();
		Random random = new Random();

		//Clear
		MapGenerator.clear(area, task);
		
		//Landscape
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
					//Land Cube
					int height = 4 + random.nextInt(32);
					BasicGenUtil.generateCube(place.clone(), Material.DIRT, width, height - 1, length);
					place.add(1, 0, 1);
					BasicGenUtil.generateCube(place.clone(), Material.STONE, new OreModifier(random.nextLong()), width - 2, height - 4, length - 2);
					place.add(-1, height - 1, -1);
					BasicGenUtil.generateCube(place.clone(), Material.GRASS_BLOCK, width, 1, length);
					//Snow
					place.add(0, 1, 0);
					BasicGenUtil.generateCube(place.clone(), Material.SNOW, width, 1, length);
					//Ice
					if (random.nextInt(5) == 0) {
						place.add(1, -5, 1);
						BasicGenUtil.generateCube(place.clone(), Material.ICE, width - 2, 5, length - 2);
						place.add(-1, 5, -1);
					}
					//Decoration
					//Trees
					for (int i = 0; i < 4; i++){
						int treeX = random.nextInt(width);
						int treeZ = random.nextInt(length);
						place.setX(place.getX() + treeX);
						place.setZ(place.getZ() + treeZ);
						place.getBlock().setType(Material.AIR);
						place.getWorld().generateTree(place, TreeType.REDWOOD);
						place.setX(place.getX() - treeX);
						place.setZ(place.getZ() - treeZ);
					}
				});
			}
		}
		
		//Generate Houses
		task.add(() -> {
			House house = new House(Material.SNOW_BLOCK, Material.SNOW_BLOCK, new LootItem(1, new ItemStack(Material.SNOWBALL), 0, 16));
			for (int i = 0; i <8; i++){
				Location loc = area.getPos().clone().add(random.nextInt((int) area.getWidth()), 0, random.nextInt((int) area.getLength()));
				int y = loc.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
				loc.setY(y);
				house.generate(loc, random);
			}
		});
		//Generate House towers
		task.add(() -> {
			HouseTower house = new HouseTower(Material.SPRUCE_PLANKS, Material.SPRUCE_LOG, 12, new LootItem(1, new ItemStack(Material.SNOWBALL), 0, 16));
			for (int i = 0; i <4 ; i++){
				Location loc = area.getPos().clone().add(random.nextInt((int) area.getWidth()), 0, random.nextInt((int) area.getLength()));
				int y = loc.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
				loc.setY(y);
				house.generate(loc, random);
			}
		});
		//Generate Dungeon Towers
		task.add(() -> {
			DungeonTower house = new DungeonTower(Material.SNOW_BLOCK, Material.PACKED_ICE, 12);
			for (int i = 0; i <4 ; i++){
				Location loc = area.getPos().clone().add(random.nextInt((int) area.getWidth()), 0, random.nextInt((int) area.getLength()));
				int y = loc.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
				loc.setY(y);
				house.generate(loc, random);
			}
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
		return "snow";
	}

	@Override
	public String getDisplayName() {
		return "Winter Wonderland";
	}

}
