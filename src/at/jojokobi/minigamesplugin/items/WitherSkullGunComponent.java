package at.jojokobi.minigamesplugin.items;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.GameComponent;

public class WitherSkullGunComponent implements GameComponent{
	
	private World world;
	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if (event.getPlayer().getWorld() == world && player.getCooldown(Material.GOLDEN_HOE) <= 0 && event.getItem() != null && item.getType() == Material.GOLDEN_HOE && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			HashMap<Integer, ? extends ItemStack> skulls = player.getInventory().all(Material.WITHER_SKELETON_SKULL);
			if (!skulls.isEmpty()){
				ItemStack skullItem = skulls.get(skulls.keySet().iterator().next());
				WitherSkull skull = player.launchProjectile(WitherSkull.class);
				skull.setVelocity(skull.getVelocity().multiply(10));
				skull.setYield(10.0f);
				if (!item.getEnchantments().isEmpty()) {
					skull.setCharged(true);
				}
				ItemMeta damageable = item.getItemMeta();
				skullItem.setAmount(skullItem.getAmount() - 1);
				((Damageable) damageable).setDamage(((Damageable) damageable).getDamage() - 5);
				if (((Damageable) damageable).getDamage() > 33)
					player.getInventory().remove(item);
				item.setItemMeta(damageable);
				player.setCooldown(Material.GOLDEN_HOE, 20);
			}
		}
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

	@Override
	public void init(BaseMinigame game) {
		world = game.getGameArea().getPos().getWorld();
	}

}
