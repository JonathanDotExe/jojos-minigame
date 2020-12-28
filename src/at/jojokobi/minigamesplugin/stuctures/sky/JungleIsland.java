package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;


public class JungleIsland extends GameStructureBase {

	public JungleIsland(int width, int length, int height) {
		super(width, length, height);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		place.getBlock().setType(Material.DIRT);
		place.setY(place.getY() + 1);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.DIRT, 3, 3, 1);
		place.setY(place.getY() + 1);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.DIRT, 5, 5, 1);
		place.setY(place.getY() + 1);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.GRASS_BLOCK, 7, 7, 1);
		place.setX(place.getX() + 3);
		place.setZ(place.getZ() + 3);
		if (random.nextBoolean())
			place.getWorld().generateTree(place.clone(), TreeType.COCOA_TREE);
		else
			place.getWorld().generateTree(place.clone(), TreeType.JUNGLE);
	}

}
