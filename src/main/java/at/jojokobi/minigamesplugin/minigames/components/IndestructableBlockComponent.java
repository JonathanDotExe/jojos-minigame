package at.jojokobi.minigamesplugin.minigames.components;

import java.util.function.Predicate;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;

public class IndestructableBlockComponent implements GameComponent {
	
	private Predicate<Block> unbreakable;

	public IndestructableBlockComponent(Predicate<Block> unbreakable) {
		super();
		this.unbreakable = unbreakable;
	}
	
	@EventHandler
	public void onBlockBreak (BlockBreakEvent event) {
		if (unbreakable.test(event.getBlock())) {
			event.setCancelled(true);
		}
	}

	@Override
	public void init(BaseMinigame game) {
		
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void updateLobby() {
		
	}

	@Override
	public void end() {
		
	}

	@Override
	public void startLobby() {
		
	}
	
}
