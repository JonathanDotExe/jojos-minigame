package at.jojokobi.minigamesplugin.items;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;
import at.jojokobi.minigamesplugin.minigames.components.GameComponent;

public class PlayerGlowComponent implements GameComponent{

	private World world;
	
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
		for (Player player : world.getPlayers()) {
			if (world.getHighestBlockYAt(player.getLocation()) > player.getLocation().getY()) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 1));
			}
		}
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
