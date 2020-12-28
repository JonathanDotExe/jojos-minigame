package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;


public class SwampIsland extends GameStructureBase {

	public SwampIsland() {
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
		BasicGenUtil.generateCube(place.clone(), Material.GRASS_BLOCK, 7, 1, 7);
		place.setY(place.getY() + 1);
		place.setX(place.getX() + 3);
		place.setZ(place.getZ() + 3);
		place.getBlock().setType(Material.CHEST);
		place.setX(place.getX() - 1);
		place.setZ(place.getZ() - 1);
		place.getBlock().setType(Material.AIR);
		place.getWorld().generateTree(place.clone(), TreeType.SWAMP);
		place.setY(place.getY() - 1);
		place.setX(place.getX() + 1);
		place.getBlock().setType(Material.WATER);
	}

}
