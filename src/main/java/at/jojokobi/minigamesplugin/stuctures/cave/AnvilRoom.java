package at.jojokobi.minigamesplugin.stuctures.cave;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class AnvilRoom extends GameStructureBase{
		
	public AnvilRoom() {
		super(6, 6, 6);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		place.add(-1, -1, -1);
		//Walls
		BasicGenUtil.generateCube(place, Material.STONE_BRICKS, b -> b.getType() != Material.AIR, null, getWidth() + 2, getHeight() + 2, getLength() + 2);
		BasicGenUtil.generateCube(place, Material.IRON_BARS, Material.AIR, null, getWidth() + 2, getHeight() + 2, getLength() + 2);
		//Clear
		place.add(1, 1, 1);
		BasicGenUtil.generateCube(place, Material.AIR, getWidth(), getHeight(), getLength());
		//Lava
		place.add(0, -1, 0);
		place.getBlock().setType(Material.LAVA);
		place.add(getWidth() - 1, 0, 0);
		place.getBlock().setType(Material.LAVA);
		place.add(0, 0, getLength() - 1);
		place.getBlock().setType(Material.LAVA);
		place.add(- getWidth() + 1, 0, 0);
		place.getBlock().setType(Material.LAVA);
		place.add(0, 0, -getLength() + 1);
		//Anvil
		place.add(getWidth()/2, 1, getLength()/2);
		place.getBlock().setType(Material.ANVIL);
	}
	
}
