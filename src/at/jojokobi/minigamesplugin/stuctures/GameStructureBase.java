package at.jojokobi.minigamesplugin.stuctures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

import at.jojokobi.mcutil.generation.population.Structure;
import at.jojokobi.mcutil.generation.population.StructureInstance;

public abstract class GameStructureBase extends Structure implements GameStructure {
	

	public GameStructureBase(int width, int length, int height) {
		super(width, length, height, 0, 1);
	}

	@Override
	public List<StructureInstance<? extends Structure>> generate(Location loc, long seed) {
		generate(loc, new Random(generateValueBeasedSeed(loc, seed)));
		return new ArrayList<StructureInstance<? extends Structure>>();
	}

	@Override
	public String getIdentifier() {
		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public StructureInstance<? extends Structure> getStandardInstance(Location location) {
		return new StructureInstance<Structure>(this, location, getWidth(), getHeight(), getLength());
	}

}
