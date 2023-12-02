package at.jojokobi.minigamesplugin.stuctures;

import java.util.Random;

import org.bukkit.Location;

public interface GameStructure {
	
	public void generate (Location loc, Random random);

}
