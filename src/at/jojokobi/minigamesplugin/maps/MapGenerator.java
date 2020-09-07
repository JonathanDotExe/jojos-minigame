package at.jojokobi.minigamesplugin.maps;

import org.bukkit.Material;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.util.Area;

public interface MapGenerator {
	
	public MultiTickTask generate (Area area);
	
	public String getName ();
	
	public String getDisplayName ();
	
	public static void clear(Area area, MultiTickTask task) {
		for (int x = 0; x < area.getWidth() * 2; x+=32) {
			for (int z = 0; z < area.getLength() * 2; z+=32) {
				int xPos = x;
				int zPos = z;
				task.add(() -> BasicGenUtil.generateCube(area.getPos().clone().add(xPos - area.getWidth()/2, 0, zPos - area.getLength()/2), Material.AIR, (int) Math.min(32, area.getWidth() * 2 - xPos + area.getWidth()/2), (int)area.getHeight(), (int) Math.min(32, area.getLength() * 2 - zPos + area.getLength()/2)));
			}
		}
	}

}
