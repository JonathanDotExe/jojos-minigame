package at.jojokobi.minigamesplugin.stuctures.cave;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class SpawnRoom extends GameStructureBase{
	
	public SpawnRoom() {
		super(10, 10, 18);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		BasicGenUtil.generateCube(place, Material.AIR, getWidth(), getHeight(), getLength());
		//Fences and glowstone
		BasicGenUtil.generateCube(place, Material.OAK_FENCE, 1, getHeight(), 1);
		place.add(getWidth() - 1, 0, 0);
		place.getBlock().getRelative(0, -1, 0).setType(Material.GLOWSTONE);
		place.getBlock().getRelative(0, getHeight(), 0).setType(Material.GLOWSTONE);
		BasicGenUtil.generateCube(place, Material.OAK_FENCE, 1, getHeight(), 1);
		place.add(0, 0, getLength() - 1);
		place.getBlock().getRelative(0, -1, 0).setType(Material.GLOWSTONE);
		place.getBlock().getRelative(0, getHeight(), 0).setType(Material.GLOWSTONE);
		BasicGenUtil.generateCube(place, Material.OAK_FENCE, 1, getHeight(), 1);
		place.add(- getWidth() + 1, 0, 0);
		place.getBlock().getRelative(0, -1, 0).setType(Material.GLOWSTONE);
		place.getBlock().getRelative(0, getHeight(), 0).setType(Material.GLOWSTONE);
		BasicGenUtil.generateCube(place, Material.OAK_FENCE, 1, getHeight(), 1);
		place.add(0, 0, -getLength() + 1);
		place.getBlock().getRelative(0, -1, 0).setType(Material.GLOWSTONE);
		place.getBlock().getRelative(0, getHeight(), 0).setType(Material.GLOWSTONE);
		//Wood planks
		place.add(0, 9, 0);
		BasicGenUtil.generateCube(place, Material.OAK_PLANKS, getWidth(), 1, getLength());
		place.add(2, 0, 2);
		BasicGenUtil.generateCube(place, Material.AIR, getWidth() - 4, 1, getLength() - 4);
		place.add(-2, -9, -2);
		//Ladder
		place.add(1, 0, getLength() - 1);
		BasicGenUtil.generateCube(place, Material.LADDER, 1, getHeight(), 1);
	}
	
}
