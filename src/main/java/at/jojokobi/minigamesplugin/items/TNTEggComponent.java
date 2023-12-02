package at.jojokobi.minigamesplugin.items;

import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;

import at.jojokobi.mcutil.entity.NMSEntityUtil;
import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.components.GameComponent;

public class TNTEggComponent implements GameComponent {

	private World world;
	
	@EventHandler
	public void onPlayerEggThrow (PlayerEggThrowEvent event) {
		if (event.getEgg().getWorld() == world) {
			event.setHatchingType(EntityType.PRIMED_TNT);
			event.setHatching(false);
			event.setNumHatches((byte) 0);
		}
	}
	
	@EventHandler
	public void onProjectileHit (ProjectileHitEvent event) {
		if (event.getEntity().getWorld() == world && event.getEntity() instanceof Egg && Math.random() < 0.125) {
			TNTPrimed tnt = world.spawn(event.getEntity().getLocation(), TNTPrimed.class);
			if (event.getEntity().getShooter() instanceof Player) {
				NMSEntityUtil.setTNTSource(tnt, (Player) event.getEntity().getShooter());
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
