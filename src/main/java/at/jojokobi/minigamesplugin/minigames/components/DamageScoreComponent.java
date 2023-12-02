package at.jojokobi.minigamesplugin.minigames.components;

import java.util.function.BiFunction;

import org.bukkit.entity.Player;

import at.jojokobi.minigamesplugin.scoreboard.PlayerScore;

public class DamageScoreComponent extends DamageEventComponent {
	
	private BiFunction<Double, Boolean, Integer> scoreFunction;
	private PlayerScore score;

	public DamageScoreComponent(BiFunction<Double, Boolean, Integer> scoreFunction) {
		super();
		this.scoreFunction = scoreFunction;
	}

	public PlayerScore getScore() {
		return score;
	}

	public void setScore(PlayerScore score) {
		this.score = score;
	}

	@Override
	public void onDamage(Player damager, Player damaged, double damage) {
		score.set(score.get(damager) + scoreFunction.apply(damage,  damaged.getHealth() - damage <= 0.5), damager);
	}	
	
}
