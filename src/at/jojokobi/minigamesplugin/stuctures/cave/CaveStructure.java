package at.jojokobi.minigamesplugin.stuctures.cave;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.population.TunnelPathGenerator;
import at.jojokobi.mcutil.generation.population.VillageSpreader;
import at.jojokobi.minigamesplugin.stuctures.GameStructure;


public class CaveStructure implements GameStructure {
	
	private VillageSpreader spreader;
	
	public CaveStructure() {
		spreader = new VillageSpreader(new LootRoom());
		spreader.setStepMultiplier(0.25f);
		spreader.setForceHeight(true);
		spreader.setBlockFunction(b -> Material.AIR);
		spreader.setPathGenerator(new TunnelPathGenerator());
		spreader.setUnitHeight(16);
		spreader.setUnitHeight(16);
		spreader.setWidth(4);
		spreader.setHeight(4);
	}

	@Override
	public void generate(Location loc, Random random) {
		spreader.generateVillage(random, random.nextLong(), loc);
	}

}
