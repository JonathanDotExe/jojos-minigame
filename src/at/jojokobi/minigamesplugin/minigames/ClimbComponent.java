package at.jojokobi.minigamesplugin.minigames;

import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ClimbComponent implements GameComponent {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Location to = event.getTo();
		if (player.isSneaking()){
			Location to1 = to.clone();
			to1.setX(to1.getX() + 0.5);
			Location to2 = to.clone();
			to2.setX(to2.getX() - 0.5);
			Location to3 = to.clone();
			to3.setZ(to3.getZ() + 0.5);
			Location to4 = to.clone();
			to4.setZ(to4.getZ() - 0.5);
			if ((to.getBlock().getType().isSolid()) || (to1.getBlock().getType().isSolid()) || (to2.getBlock().getType().isSolid()) || (to3.getBlock().getType().isSolid()) || (to4.getBlock().getType().isSolid())){
				Vector motion = player.getVelocity();
				double playerY = player.getLocation().getY();
				double blockY = player.getTargetBlock((Set<Material>) null, 256).getLocation().getY();
				double distance = blockY  - playerY;
				if (distance > 2)
					motion.setY(0.3);
				else if (distance < -2)
					motion.setY(-0.3);
				else
					motion.setY(0);
				player.setVelocity(motion);
				player.setFallDistance(0);
				player.setExhaustion(player.getExhaustion() + 0.05f);
//				}
			}
		}
		if (to.getY() < 0){
			Random random = new Random();
			Location place = new Location(to.getWorld(), random.nextInt(60) - 30, 100, random.nextInt(60) - 30);
			place.setY(place.getWorld().getHighestBlockYAt(place) + 1);
			event.setTo(place);
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

}
