package at.jojokobi.minigamesplugin.stuctures.cave;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class MeetingRoom extends GameStructureBase{
		
	public MeetingRoom() {
		super(14, 14, 8);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		place.add(-1, -1, -1);
		//Walls
		BasicGenUtil.generateCube(place, Material.BRICKS, b -> b.getType() != Material.AIR, null, getWidth() + 2, getHeight() + 2, getLength() + 2);		//Clear
		place.add(1, 1, 1);
		BasicGenUtil.generateCube(place, Material.AIR, getWidth(), getHeight(), getLength());
		//Emergency button
		place.add(getWidth()/2 - 1, 0, getLength()/2 - 1);
		BasicGenUtil.generateCube(place, Material.RED_TERRACOTTA, 3, 1, 3);
		place.add(1, 1, 1);
		place.getBlock().setType(Material.REDSTONE_LAMP);
		place.add(0, 0, -1);
		place.getBlock().setType(Material.STONE_BUTTON);
	}
	
}
