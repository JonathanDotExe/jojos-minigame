package at.jojokobi.minigamesplugin.items;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.GameComponent;

public class SpectralArrow implements GameComponent {
	
	private World world;

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		for (Entity entity : world.getEntitiesByClass(org.bukkit.entity.SpectralArrow.class)) {
			int startX = entity.getLocation().getBlockX() - 1;
			int startY = entity.getLocation().getBlockY() - 1;
			int startZ = entity.getLocation().getBlockZ() - 1;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					for (int z = 0; z < 3; z++) {
						new Location(world, startX + x, startY + y, startZ + z).getBlock().breakNaturally();
					}
				}
			}
		}
	}

	@Override
	public void updateLobby() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startLobby() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(BaseMinigame game) {
		world = game.getGameArea().getPos().getWorld();
	}

}
