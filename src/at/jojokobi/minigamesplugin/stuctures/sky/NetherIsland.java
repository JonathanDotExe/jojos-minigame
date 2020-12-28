package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;


public class NetherIsland extends GameStructureBase {

	public NetherIsland(int width, int length, int height) {
		super(width, length, height);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		BasicGenUtil.generateCube(place.clone(), Material.NETHERRACK, 3, 3, 2);
		place.setY(place.getY() + 2);
		BasicGenUtil.generateCube(place.clone(), Material.SOUL_SAND, 3, 3, 1);
		place.setY(place.getY() + 1);
		place.setX(place.getX() + 1);
		place.setZ(place.getZ() + 1);
		place.getBlock().setType(Material.CHEST);
		place.setY(place.getX() + 1);
		if (random.nextInt(6) == 3) {
			place.getBlock().setType(Material.NETHER_WART_BLOCK);
		}
	}

}
