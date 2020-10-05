package at.jojokobi.minigamesplugin.stuctures.cave;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.generation.population.TunnelPathGenerator;
import at.jojokobi.mcutil.generation.population.VillageSpreader;
import at.jojokobi.minigamesplugin.stuctures.GameStructure;


public class CaveStructure implements GameStructure {
	
	private VillageSpreader upperSpreader;
	private VillageSpreader lowerSpreader;
	
	public CaveStructure(int width, int height) {
		{
			lowerSpreader = new VillageSpreader(new LootRoom(), new LootRoom(), new LootRoom(), new KitRoom(), new KitRoom(), new AnvilRoom(), new MeetingRoom(), new MeetingRoom());
			lowerSpreader.setStepMultiplier(0.5f);
			lowerSpreader.setForceHeight(true);
			lowerSpreader.setBlockFunction(b -> Material.AIR);
			lowerSpreader.setPathGenerator(new TunnelPathGenerator());
			lowerSpreader.setUnitHeight(16);
			lowerSpreader.setUnitHeight(16);
			lowerSpreader.setWidth(width/16);
			lowerSpreader.setHeight(height/16);
		}
		{
			upperSpreader = new VillageSpreader(new LootRoom(), new LootRoom(), new KitRoom(), new KitRoom(), new KitRoom(), new MeetingRoom(), new MeetingRoom());
			upperSpreader.setStepMultiplier(0.5f);
			upperSpreader.setForceHeight(true);
			upperSpreader.setBlockFunction(b -> Material.AIR);
			upperSpreader.setPathGenerator(new TunnelPathGenerator());
			upperSpreader.setUnitHeight(16);
			upperSpreader.setUnitHeight(16);
			upperSpreader.setWidth(width/16);
			upperSpreader.setHeight(height/16);
		}
	}

	@Override
	public void generate(Location loc, Random random) {
		Location centerPlace = loc.clone().add(lowerSpreader.getWidth()/2 * lowerSpreader.getUnitWidth() - lowerSpreader.getUnitWidth()/2, 0, lowerSpreader.getHeight()/2 * lowerSpreader.getUnitHeight() - lowerSpreader.getUnitHeight()/2);
		BasicGenUtil.generateCube(centerPlace.add(-2, 0, -2), Material.AIR, 4, 10, 4);
		lowerSpreader.generateVillage(random, random.nextLong(), loc);
		upperSpreader.generateVillage(random, random.nextLong(), loc.clone().add(0, 10, 0));
	}

}
