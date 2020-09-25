package at.jojokobi.minigamesplugin.util;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class StaticUtils {

	public static Player getDamagingPlayer(EntityDamageEvent e) {
		Player player = null;
		if (e instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
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
		}
		return player;
	}
	
}
