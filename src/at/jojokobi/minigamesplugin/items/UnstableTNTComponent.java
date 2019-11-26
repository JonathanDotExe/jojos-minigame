package at.jojokobi.minigamesplugin.items;

import org.bukkit.World;
import org.bukkit.block.data.type.TNT;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.GameComponent;

public class UnstableTNTComponent implements GameComponent{

	private World world;
	
	@EventHandler
	public void onPlackPlace (BlockPlaceEvent event) {
		if (event.getBlockPlaced().getBlockData() instanceof TNT && event.getBlockPlaced().getWorld() == world) {
			TNT tnt = (TNT) event.getBlockPlaced().getBlockData();
			tnt.setUnstable(true);
			event.getBlockPlaced().setBlockData(tnt);
		}
	}
	
	@Override
	public void init(BaseMinigame game) {
		world = game.getGameArea().getPos().getWorld();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
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

}
