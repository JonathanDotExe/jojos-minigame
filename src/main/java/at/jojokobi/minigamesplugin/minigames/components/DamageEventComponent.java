package at.jojokobi.minigamesplugin.minigames.components;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.util.StaticUtils;

public abstract class DamageEventComponent implements GameComponent {
	
	private World world;

	public DamageEventComponent() {
		super();
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onEntityDamage (EntityDamageByEntityEvent event) {
		//Check world
		if (event.getDamager().getWorld() == world && event.getEntity() instanceof Player) {
			Player player = StaticUtils.getDamagingPlayer(event);
			if (player != null && player != event.getEntity()) {
				onDamage(player, (Player) event.getEntity(), event.getDamage());
			}
		}
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
	
	public abstract void onDamage(Player damager, Player damaged, double damage);

	@Override
	public void init(BaseMinigame game) {
		world = game.getGameArea().getPos().getWorld();
	}

}
