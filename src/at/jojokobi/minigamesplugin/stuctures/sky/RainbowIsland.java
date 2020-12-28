package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;


public class RainbowIsland extends GameStructureBase {

	public static final Material[] WOOLS = {Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL};
	
	
	public RainbowIsland() {
		super(0, 0, 0); //TODO
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		place.getBlock().setType(Material.DIRT);
		place.setY(place.getY() + 1);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.DIRT, 3, 1, 3);
		place.setY(place.getY() + 1);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.DIRT, 5, 1, 5);
		place.setY(place.getY() + 1);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.WHITE_WOOL, 7, 1, 7);

		int startX = (int) place.getX();
		int startZ = (int) place.getZ();
		for (int x = 0; x < 7; x++){
			place.setX(startX + x);
			for (int z = 0; z < 7; z++){
				place.setZ(startZ + z);
				place.getBlock().setType(WOOLS[random.nextInt(WOOLS.length)]);;
			}
		}
		place.setX(place.getX() - 4);
		place.setZ(place.getZ() - 4);
		BasicGenUtil.generateCube(place.clone(), Material.GRASS_BLOCK, 3, 1, 3);
		place.setY(place.getY() + 1);
		place.setX(place.getX() + 1);
		place.setZ(place.getZ() + 1);
		place.getWorld().generateTree(place.clone(), TreeType.TREE);
		place.setX(place.getX() + 1);
		place.getBlock().setType(Material.CHEST);
	}

}
