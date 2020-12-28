package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;


public class NetherIsland extends GameStructureBase {

	public NetherIsland() {
		super(0, 0, 0); //TODO
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		BasicGenUtil.generateCube(place.clone(), Material.NETHERRACK, 3, 2, 3);
		place.setY(place.getY() + 2);
		BasicGenUtil.generateCube(place.clone(), Material.SOUL_SAND, 3, 1, 3);
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
