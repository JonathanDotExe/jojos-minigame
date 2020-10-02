package at.jojokobi.minigamesplugin.items;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.type.TNT;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import at.jojokobi.mcutil.entity.NMSEntityUtil;
import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.components.GameComponent;

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
	
	//Fix tnt source bug
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event) {
		if (event.getPlayer().getWorld() == world && event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getBlockData() instanceof TNT && ((TNT) event.getClickedBlock().getBlockData()).isUnstable()) {
			event.setCancelled(true);
			event.getClickedBlock().setType(Material.AIR);
			TNTPrimed tnt = world.spawn(event.getClickedBlock().getLocation(), TNTPrimed.class);
			NMSEntityUtil.setTNTSource(tnt, event.getPlayer());
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
