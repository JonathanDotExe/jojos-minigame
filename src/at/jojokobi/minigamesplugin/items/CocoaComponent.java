package at.jojokobi.minigamesplugin.items;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.GameComponent;

public class CocoaComponent implements GameComponent{
	
	private World world;
	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if (item != null && item.getType() == Material.COCOA_BEANS && event.getPlayer().getWorld() == world) {
			//Leaves tower
			BasicGenUtil.generateCube(player.getLocation(), Material.JUNGLE_LEAVES, 1, 30, 1);
			player.setFallDistance(0);
			player.teleport(player.getLocation().add(0, 30, 0));
			item.setAmount(item.getAmount() - 1);
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
