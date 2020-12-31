package at.jojokobi.minigamesplugin.stuctures.sky;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class SandIsland extends GameStructureBase {

	public SandIsland() {
		super(0, 0, 0); //TODO
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		place.setY(place.getY() - 1);
		BasicGenUtil.generateCube(place.clone(), Material.SANDSTONE, 3, 1, 3);
		place.setY(place.getY() + 1);
		BasicGenUtil.generateCube(place.clone(), Material.SAND, 3, 3, 3);
		place.setY(place.getY() + 3);
		place.getBlock().setType(Material.CACTUS);
		place.setX(place.getX() + 2);
		place.getBlock().setType(Material.CHEST);
	}

}
