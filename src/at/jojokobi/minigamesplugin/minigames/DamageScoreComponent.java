package at.jojokobi.minigamesplugin.minigames;

import java.util.function.BiFunction;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import at.jojokobi.minigamesplugin.scoreboard.PlayerScore;

public class DamageScoreComponent implements GameComponent {
	
	private BiFunction<Double, Boolean, Integer> scoreFunction;
	private PlayerScore score;
	private World world;
	

	public DamageScoreComponent(BiFunction<Double, Boolean, Integer> scoreFunction) {
		super();
		this.scoreFunction = scoreFunction;
	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void onEntityDamage (EntityDamageByEntityEvent event) {
		//Check world
		if (event.getDamager().getWorld() == world && event.getEntity() instanceof Player) {
			Bukkit.broadcastMessage(event.getDamager() + "");
			Player player = null;
			//Hit
			if (event.getDamager() instanceof Player) {
				player = (Player) event.getDamager();
			}
			//TNT
			else if (event.getDamager() instanceof TNTPrimed) {
				Bukkit.broadcastMessage("Sauce: " + ((TNTPrimed) event.getDamager()).getSource() + "");
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
			
			if (player != null) {
				score.set(score.get(player) + scoreFunction.apply(event.getFinalDamage(),  ((Player) event.getEntity()).getHealth() - event.getFinalDamage() <= 0.5), player);
			}
		}
	}

	public PlayerScore getScore() {
		return score;
	}

	public void setScore(PlayerScore score) {
		this.score = score;
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

	@Override
	public void init(BaseMinigame game) {
		world = game.getGameArea().getPos().getWorld();
	}

}
