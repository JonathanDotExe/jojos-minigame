package at.jojokobi.minigamesplugin.minigames;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class DamageEventComponent implements GameComponent {
	
	private World world;

	public DamageEventComponent() {
		super();
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onEntityDamage (EntityDamageByEntityEvent event) {
		//Check world
		if (event.getDamager().getWorld() == world && event.getEntity() instanceof Player) {
			Player player = null;
			//Hit
			if (event.getDamager() instanceof Player) {
				player = (Player) event.getDamager();
			}
			//TNT
			else if (event.getDamager() instanceof TNTPrimed) {
				if (((TNTPrimed) event.getDamager()).getSource() instanceof Player) {
					player = (Player) ((TNTPrimed) event.getDamager()).getSource();
				}
				else if (((TNTPrimed) event.getDamager()).getSource() instanceof Projectile && ((Projectile)((TNTPrimed) event.getDamager()).getSource()) instanceof Player) {
					player = (Player) ((Projectile)((TNTPrimed) event.getDamager()).getSource()).getShooter();
				}
			}
			//Projectile
			else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
				player = (Player) ((Projectile) event.getDamager()).getShooter();
			}
			
			if (player != null && player != event.getEntity()) {
				onDamage(player, (Player) event, event.getDamage());
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
