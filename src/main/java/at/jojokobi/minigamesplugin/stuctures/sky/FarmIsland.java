package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class FarmIsland extends GameStructureBase {

	public FarmIsland() {
		super(0, 0, 0); //TODO
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		BasicGenUtil.generateCube(place.clone(), Material.DIRT, 4, 2, 4);
		place.setY(place.getY() + 2);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.GRASS_BLOCK, 6, 1, 6);
		place.setY(place.getY() + 1);
		BasicGenUtil.generateCube(place.clone(), Material.GRASS, 6, 1, 6);
		place.setZ(place.getZ() + 3);
		place.setX(place.getX() + 4);
		place.getBlock().setType(Material.AIR);
		place.getWorld().generateTree(place.clone(), TreeType.TREE);
		place.setX(place.getX() + 1);
		place.getBlock().setType(Material.CHEST);
	}

}
