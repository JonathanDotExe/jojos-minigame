package at.jojokobi.minigamesplugin.maps;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.util.Area;

public class SimpleLobbyGenerator implements MapGenerator{

	@Override
	public MultiTickTask generate(Area area) {
		MultiTickTask task = new MultiTickTask();
		//Clear
		for (int x = 0; x < area.getWidth(); x+=32) {
			for (int z = 0; z < area.getLength(); z+=32) {
				int xPos = x;
				int zPos = z;
				task.add(() ->BasicGenUtil.generateCube(area.getPos().clone(), Material.AIR, (int) Math.min(32, area.getWidth() - xPos), (int)area.getHeight(), (int) Math.min(32, area.getLength() - zPos)));
			}
		}
		//Stone Cube
		for (int x = 0; x < area.getWidth(); x+=32) {
			for (int z = 0; z < area.getLength(); z+=32) {
				int xPos = x;
				int zPos = z;
				task.add(() ->BasicGenUtil.generateCube(area.getPos().clone(), Material.STONE, (int) Math.min(32, area.getWidth() - xPos), 64, (int) Math.min(32, area.getLength() - zPos)));
			}
		}
		task.add(() -> {
			for (Entity entity : area.getPos().getWorld().getEntities()){
				if (entity.getType() != EntityType.PLAYER)
					entity.remove();
			}
		});
		return task;
	}

}
