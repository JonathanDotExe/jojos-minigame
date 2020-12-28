package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.generation.population.BlockModifier;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class MineIsland extends GameStructureBase {

	public MineIsland(int width, int length, int height) {
		super(width, length, height);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		BlockModifier oreModifier = s -> {
			int num = random.nextInt(27);
			if (num < 7){
				//Leave
			}
			else if (num < 9){
				place.getBlock().setType(Material.DIRT);
			}
			else if (num < 11){
				place.getBlock().setType(Material.GRAVEL);
			}
			else if (num < 13){
				place.getBlock().setType(Material.COAL_ORE);
			}
			else if (num < 15){
				place.getBlock().setType(Material.IRON_ORE);
			}
			else if (num < 16){
				place.getBlock().setType(Material.GOLD_ORE);
			}
			else if (num < 17){
				place.getBlock().setType(Material.REDSTONE_ORE);
			}
			else if (num < 18){
				if (random.nextInt(10) == 1)
					place.getBlock().setType(Material.DIAMOND_ORE);
				else
					place.getBlock().setType(Material.COAL_ORE);
			}
			else if (num < 19){
				place.getBlock().setType(Material.OBSIDIAN);
			}
			else if (num < 20){
				place.getBlock().setType(Material.LAPIS_ORE);
			}
		};
		BasicGenUtil.generateCube(place.clone(), Material.STONE, oreModifier, 5, 5, 5);
		place.setY(place.getY() + 3);
		place.setX(place.getX() + 4);
		place.setZ(place.getZ() + 3);
		BasicGenUtil.generateCube(place.clone(), Material.STONE, oreModifier, 3, 3, 3);
		place.setY(place.getY() + 2);
		place.setX(place.getX() + 1);
		place.setZ(place.getZ() + 2);
		BasicGenUtil.generateCube(place.clone(), Material.STONE, oreModifier, 4, 4, 4);
		place.setY(place.getY() + 4);
		place.getBlock().setType(Material.GRASS_BLOCK);
		place.setY(place.getY() + 1);
		place.getWorld().generateTree(place.clone(), TreeType.TREE);
		place.setX(place.getX() + 1);
		place.getBlock().setType(Material.CHEST);
	}

}
