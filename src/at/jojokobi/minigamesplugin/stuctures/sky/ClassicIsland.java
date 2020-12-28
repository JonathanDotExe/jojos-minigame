package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class ClassicIsland extends GameStructureBase {

	public ClassicIsland() {
		super(0, 0, 0); //TODO
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		BasicGenUtil.generateCube(place.clone() , Material.GRASS_BLOCK, 3, 3, 3);
		BasicGenUtil.generateCube(place.clone() , Material.DIRT, 3, 3, 2);
		place.setX(place.getX() + 3);
		BasicGenUtil.generateCube(place.clone() , Material.GRASS_BLOCK, 3, 3, 3);
		BasicGenUtil.generateCube(place.clone() , Material.DIRT, 3, 3, 2);
		place.setZ(place.getZ() + 3);
		BasicGenUtil.generateCube(place.clone() , Material.GRASS_BLOCK, 3, 3, 3);
		BasicGenUtil.generateCube(place.clone() , Material.DIRT, 3, 3, 2);
		place.setY(place.getY() + 3);
		place.getBlock().setType(Material.CHEST);
		//TODO loot
		place.setZ(place.getZ() - 3);
		place.setX(place.getX() - 3);
		place.getWorld().generateTree(place.clone(), TreeType.TREE);
	}

}
