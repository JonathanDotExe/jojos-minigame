package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.Chest;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class ClassicIsland extends GameStructureBase {

	public ClassicIsland(int width, int length, int height) {
		super(width, length, height);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		place.setY(place.getY() - 16);
		place.setX(place.getX() - 16);
		place.setZ(place.getZ() - 16);
		place.setY(place.getY() + 16);
		place.setX(place.getX() + 16);
		place.setZ(place.getZ() + 16);
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
