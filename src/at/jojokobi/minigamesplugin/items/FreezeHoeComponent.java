package at.jojokobi.minigamesplugin.items;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.GameComponent;

public class FreezeHoeComponent implements GameComponent{
	
	private World world;
	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if (event.getPlayer().getWorld() == world && event.getItem() != null && item.getType() == Material.WOODEN_HOE && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			LlamaSpit skull = player.launchProjectile(LlamaSpit.class);
			skull.setVelocity(skull.getVelocity().multiply(10));
			
			ItemMeta damageable = item.getItemMeta();
			((Damageable) damageable).setDamage(((Damageable) damageable).getDamage() - 5);
			if (((Damageable) damageable).getDamage() > 66) {
				player.getInventory().remove(item);
			}
			item.setItemMeta(damageable);
		}
	}
	
	@EventHandler
	public void onProjectileHit (ProjectileHitEvent event) {
		if (event.getEntity().getWorld() == world && event.getEntity() instanceof LlamaSpit) {
			BasicGenUtil.generateCube(event.getEntity().getLocation().add(-2, -2, -2), Material.ICE, Material.WATER, null, 5, 5, 5);
		}
	}

	@Override
	public void init(BaseMinigame game) {
		this.world = game.getGameArea().getPos().getWorld();
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
