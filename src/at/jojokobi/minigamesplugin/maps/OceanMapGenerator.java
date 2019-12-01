package at.jojokobi.minigamesplugin.maps;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.loot.LootItem;
import at.jojokobi.minigamesplugin.stuctures.DungeonTower;
import at.jojokobi.minigamesplugin.stuctures.House;
import at.jojokobi.minigamesplugin.stuctures.HouseTower;
import at.jojokobi.minigamesplugin.util.Area;

public class OceanMapGenerator implements MapGenerator {

	@Override
	public MultiTickTask generate(Area area) {
		MultiTickTask task = new MultiTickTask();
		Random random = new Random();

		//Clear
		for (int x = 0; x < area.getWidth() * 2; x+=32) {
			for (int z = 0; z < area.getLength() * 2; z+=32) {
				int xPos = x;
				int zPos = z;
				task.add(() -> BasicGenUtil.generateCube(area.getPos().clone().add(xPos - area.getWidth()/2, 0, zPos - area.getLength()/2), Material.AIR, (int) Math.min(32, area.getWidth() * 2 - xPos + area.getWidth()/2), (int)area.getHeight(), (int) Math.min(32, area.getLength() * 2 - zPos + area.getLength()/2)));
			}
		}
		
		//Landscape
		for (int x = 0; x < area.getWidth(); x += 16) {
			for (int z = 0; z < area.getLength(); z += 16) {
				int xPos = x;
				int zPos = z;
				task.add(() -> {
					int width = (int) Math.min(16, area.getWidth() - xPos);
					int length = (int) Math.min(16, area.getLength() - zPos);
					Location place = area.getPos().clone().add(xPos, 0, zPos);
					BasicGenUtil.generateCube(place.clone(), Material.SANDSTONE, width, 32, length);
					int waterX = xPos == 0 ? 1 : 0;
					int waterZ = zPos == 0 ? 1 : 0;
					int waterWidth = (xPos + 16 > area.getWidth() ? 15 : 16) - waterX;
					int waterLength = (zPos + 16 > area.getLength() ? 15 : 16) - waterZ;
					place.add(waterX, 1, waterZ);
					BasicGenUtil.generateCube(place.clone(), Material.WATER, waterWidth, 31, waterLength);
					place.add(-waterX, 0, -waterZ);
					//Land Cube
					int height = random.nextInt(64) + 1;
					BasicGenUtil.generateCube(place.clone(), Material.SAND, b -> true, null, width, height, length, false);
				});
			}
		}
		
		//Generate Houses
		task.add(() -> {
			House house = new House(Material.DARK_OAK_LOG, Material.RED_WOOL, new LootItem(0.5, new ItemStack(Material.WOODEN_HOE), 1, 1));
			for (int i = 0; i <8; i++){
				Location loc = area.getPos().clone().add(random.nextInt((int) area.getWidth()), 0, random.nextInt((int) area.getLength()));
				int y = loc.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
				loc.setY(y);
				house.generate(loc, random);
			}
		});
		//Generate House towers
		task.add(() -> {
			HouseTower house = new HouseTower(Material.RED_CONCRETE, Material.WHITE_CONCRETE, 12, new LootItem(0.25, new ItemStack(Material.WOODEN_HOE), 1, 1));
			for (int i = 0; i <4 ; i++){
				Location loc = area.getPos().clone().add(random.nextInt((int) area.getWidth()), 0, random.nextInt((int) area.getLength()));
				int y = loc.getWorld().getHighestBlockYAt((int) loc.getX(), (int) loc.getZ());
				loc.setY(y);
				house.generate(loc, random);
			}
		});
		//Generate Dungeon Towers
		task.add(() -> {
			DungeonTower house = new DungeonTower(Material.PRISMARINE, Material.SEA_LANTERN, 12);
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
		return "beach";
	}

	@Override
	public String getDisplayName() {
		return "Cool Beach";
	}

}
