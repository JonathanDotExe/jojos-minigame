package at.jojokobi.minigamesplugin.items;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.components.GameComponent;

public class SnowballComponent implements GameComponent{
	
	private World world;
	
	@EventHandler
	public void onProjectileHit (ProjectileHitEvent event) {
		if (event.getEntity().getWorld() == world && event.getEntity() instanceof Snowball) {
			for (Entity entity : event.getEntity().getNearbyEntities(4, 4, 4)) {
				if (entity instanceof LivingEntity) {
					((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 255));
				}
			}
			BasicGenUtil.generateCube(event.getEntity().getLocation().add(-2, -2, -2), Material.ICE, b -> !b.getType().isAir(), null, 5, 5, 5);
		}
	}

	@Override
	public void init(BaseMinigame game) {
		this.world = game.getGameArea().getPos().getWorld();
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
