package at.jojokobi.minigamesplugin.items;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.GameComponent;

public class RabbitFootComponent implements GameComponent{
	
	private World world;
	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if (item != null && item.getType() == Material.RABBIT_FOOT && event.getPlayer().getWorld() == world) {
			if (player.getFoodLevel() > 10){
				player.addPotionEffect(new PotionEffect (PotionEffectType.JUMP, 120, 50));
				player.addPotionEffect(new PotionEffect (PotionEffectType.DAMAGE_RESISTANCE, 240, 100));
				player.setFoodLevel(10);
				item.setAmount(item.getAmount() - 1);
			}
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
