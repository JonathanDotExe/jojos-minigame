package at.jojokobi.minigamesplugin.minigames.components;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.jojokobi.minigamesplugin.minigames.BaseMinigame;

public class StrengthComponent implements GameComponent {

	private BaseMinigame game;
	
	@Override
	public void init(BaseMinigame game) {
		this.game = game;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		if (game.getScoreboard() != null) {
			for (Player player : game.getScoreboard().getOnlinePlayers()) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 2));
			}
		}
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
