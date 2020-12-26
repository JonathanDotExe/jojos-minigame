package at.jojokobi.minigamesplugin.minigames;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;

import at.jojokobi.minigamesplugin.items.CocoaComponent;
import at.jojokobi.minigamesplugin.items.FreezeHoeComponent;
import at.jojokobi.minigamesplugin.items.RabbitFootComponent;
import at.jojokobi.minigamesplugin.items.SnowballComponent;
import at.jojokobi.minigamesplugin.items.SpectralArrowComponent;
import at.jojokobi.minigamesplugin.items.TNTEggComponent;
import at.jojokobi.minigamesplugin.items.UnstableTNTComponent;
import at.jojokobi.minigamesplugin.items.WitherSkullGunComponent;
import at.jojokobi.minigamesplugin.kits.PlayerKit;
import at.jojokobi.minigamesplugin.kits.PlayerKits;
import at.jojokobi.minigamesplugin.maps.MapGenerator;
import at.jojokobi.minigamesplugin.minigames.components.ClimbComponent;
import at.jojokobi.minigamesplugin.minigames.components.StrengthComponent;
import at.jojokobi.minigamesplugin.scoreboard.CustomScoreboard;
import at.jojokobi.minigamesplugin.scoreboard.GlobalScore;
import at.jojokobi.minigamesplugin.util.Area;

public class SkySiegePlugin extends BaseMinigame{

	private int gameDuration = 30 * 60 * 20;
	private int maxPlayers = 10;
	private int maxWaitTime = 20 * 20;
	
	private GlobalScore<Integer> timerScore;

	
	public SkySiegePlugin(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
		super(generator, lobbyGenerator, gameArea);
	}
	
	@Override
	public void init(Plugin plugin) {
		addComponent(new SpectralArrowComponent());
		addComponent(new WitherSkullGunComponent());
		addComponent(new UnstableTNTComponent());
		addComponent(new RabbitFootComponent());
		addComponent(new TNTEggComponent());
		addComponent(new SnowballComponent());
		addComponent(new FreezeHoeComponent());
		addComponent(new CocoaComponent());
		
		addComponent(new ClimbComponent());
		addComponent(new StrengthComponent());
		super.init(plugin);
	}
	
	@Override
	public void start() {
		Random random = new Random();
		//Teleport player to spawn
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.teleport(getGameArea().getPos().clone().add((int) (getGameArea().getWidth()/32) * 16 + 8, 8, (int) (getGameArea().getLength()/32) * 16 + 8));
		}
		resetPlayers(getScoreboard().getOnlinePlayers());

		for (Player player : getScoreboard().getOnlinePlayers()) {
			//Display role
			player.sendTitle("The Sky Siege starts now!", "Kill everyone and survive!", 20, 80, 20);
			//Give kit
			PlayerKit kit = PlayerKits.KITS.get(random.nextInt(PlayerKits.KITS.size()));
			kit.give(player.getInventory());
		}
	}

	@Override
	public void update() {
		timerScore.set(getTime()/20);
	}

	@Override
	public void updateLobby() {
		
	}

	@Override
	public void end() {
		
	}

	@Override
	public boolean canGameFinish() {
		return (getTime() >= gameDuration && getScoreboard().getOnlinePlayers().stream().allMatch(p -> p.getGameMode() != GameMode.SURVIVAL));
	}

	@Override
	public boolean canGameStart() {
		int players = determinePlayers().size();
		return players >= maxPlayers || (getTime() > maxWaitTime && players >= 1);
	}

	@Override
	public void initScoreboard(CustomScoreboard scoreboard) {
		//Score
		timerScore = new GlobalScore<>(0, "Time: ");
		scoreboard.addScore(timerScore);
	}
	
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		if (getGameArea().getPos().getWorld().getPlayers().contains(event.getEntity())) {
			//Prevent damage in protection time
			if (!isRunning()) {
				event.setDamage(0);
			}
			else if (event.getEntity() instanceof Player && getScoreboard() != null && getScoreboard().getOnlinePlayers().contains(event.getEntity())){
				Player player = (Player) event.getEntity();
				if (player.getHealth() - event.getFinalDamage() <= 0.5) {
					//Will die
					event.setDamage(0);
					player.setGameMode(GameMode.SPECTATOR);
					sendGameMessage(player.getName() + " was killed!");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		if (event.getPlayer().getWorld() == getGameArea().getPos().getWorld()) {
			if (!isRunning()) {
				spreadPlayers(Arrays.asList(event.getPlayer()), getGameArea());
				resetPlayer(event.getPlayer());
			}
			else {
				event.getPlayer().kickPlayer("Sorry a round of Sky Siege is already running!");
			}
		}
	}

	@Override
	protected void assignTeams(List<Player> players, CustomScoreboard board, Scoreboard scoreboardView) {
		
	}

	@Override
	public Winner determineWinner() {
		return new PlayerWinner(getScoreboard().getOnlinePlayers().stream().filter(p -> p.getGameMode() == GameMode.SURVIVAL).findFirst().orElseGet(() -> null), ChatColor.WHITE);
	}

	@Override
	public String getName() {
		return "Sky Siege";
	}

	@Override
	public void startLobby() {
		if (getScoreboard() != null) {
			spreadPlayers(getScoreboard().getOnlinePlayers(), getGameArea());
		}
	}

}
