package at.jojokobi.minigamesplugin.minigames;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;

import at.jojokobi.minigamesplugin.items.CocoaComponent;
import at.jojokobi.minigamesplugin.items.FreezeHoeComponent;
import at.jojokobi.minigamesplugin.items.PlayerGlowComponent;
import at.jojokobi.minigamesplugin.items.RabbitFootComponent;
import at.jojokobi.minigamesplugin.items.SnowballComponent;
import at.jojokobi.minigamesplugin.items.SpectralArrowComponent;
import at.jojokobi.minigamesplugin.items.TNTEggComponent;
import at.jojokobi.minigamesplugin.items.UnstableTNTComponent;
import at.jojokobi.minigamesplugin.items.WitherSkullGunComponent;
import at.jojokobi.minigamesplugin.maps.ForestMapGenerator;
import at.jojokobi.minigamesplugin.maps.JungleMapGenerator;
import at.jojokobi.minigamesplugin.maps.MapGenerator;
import at.jojokobi.minigamesplugin.maps.OceanMapGenerator;
import at.jojokobi.minigamesplugin.maps.SnowMapGenerator;
import at.jojokobi.minigamesplugin.scoreboard.CustomScoreboard;
import at.jojokobi.minigamesplugin.scoreboard.CustomTeam;
import at.jojokobi.minigamesplugin.scoreboard.GlobalScore;
import at.jojokobi.minigamesplugin.scoreboard.PlayerScore;
import at.jojokobi.minigamesplugin.util.Area;

public class TeamTroubleMinigame extends BaseMinigame{

	private int gameDuration = 30 * 60 * 20;
	private int maxPlayers = 8;
	private int maxWaitTime = 20 * 20;
	private int protectionTime = 3 * 60 * 20;
	
	private GlobalScore<Integer> timerScore;
	private PlayerScore playerScore;
	
	private CustomTeam redTeam;
	private CustomTeam blueTeam;
	
	private DamageScoreComponent damageScoreComponent;

	
	public TeamTroubleMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
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
		addComponent(new PlayerGlowComponent());
		addComponent(new MapSwitchComponent(Arrays.asList(new ForestMapGenerator(), new SnowMapGenerator(), new OceanMapGenerator(), new JungleMapGenerator())));
		addComponent(damageScoreComponent = new DamageScoreComponent((d, b) -> (int) (d * 5 + (b ? 100 : 0))));
		super.init(plugin);
	}
	
	@Override
	public void start() {
		spreadPlayers(getScoreboard().getOnlinePlayers(), getGameArea());
		resetPlayers(getScoreboard().getOnlinePlayers());
		for (OfflinePlayer player : getScoreboard().getPlayers()) {
			playerScore.set(100, player);
		}
	}

	@Override
	public void update() {
		if (getTime() == protectionTime && isRunning()) {
			sendGameTitle(ChatColor.YELLOW + "The protection time ends now!", "", 10, 80, 10);
		}
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
		return getTime() >= protectionTime && (getTime() >= gameDuration || getScoreboard().getPlayers().isEmpty() || getScoreboard().getPlayers().stream().allMatch(p -> getScoreboard().getTeam(p) == getScoreboard().getTeam(getScoreboard().getPlayers().get(0))));
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
		playerScore = new PlayerScore("score", "Score", DisplaySlot.PLAYER_LIST);
		scoreboard.addScore(timerScore);
		scoreboard.addScore(playerScore);
		damageScoreComponent.setScore(playerScore);
		//Teams
		scoreboard.addTeam(redTeam = new CustomTeam(ChatColor.RED, "team_red", "Team Red", false, true));
		scoreboard.addTeam(blueTeam = new CustomTeam(ChatColor.BLUE, "team_blue", "Team Blue", false, true));
	}
	
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		if (getGameArea().getPos().getWorld().getPlayers().contains(event.getEntity())) {
			//Prevent damage in protection time
			if (!isRunning() || getTime() < protectionTime) {
				event.setDamage(0);
			}
			else if (event.getEntity() instanceof Player && getScoreboard() != null && getScoreboard().getOnlinePlayers().contains(event.getEntity())){
				Player player = (Player) event.getEntity();
				if (player.getHealth() - event.getFinalDamage() <= 0.5) {
					CustomTeam newTeam;
					//Switch team
					if (getScoreboard().getTeam(player) ==  blueTeam) {
						newTeam = redTeam;
					}
					else {
						newTeam = blueTeam;
					}
					//Lose Points
					int newScore = Math.max(0, playerScore.get(player) - Math.max(playerScore.get(player)/2, 50));
					int teamMax = Math.max(getMaxScoreOfTeam(newTeam) - 50, 0);
					if (newScore > teamMax) {
						newScore = teamMax;
					}
					
					setTeam(player, newTeam);
					playerScore.set(newScore, player);
					sendGameTitle(newTeam.getColor() + player.getName() + " is now in " + newTeam.getDisplayName() + "!", "", 10, 80, 10);
					event.setDamage(0);
					player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}
			}
		}
	}
	
	private int getMaxScoreOfTeam (CustomTeam team) {
		int max = 0;
		for (OfflinePlayer player : getScoreboard().getPlayersInTeam(team)) {
			if (playerScore.get(player) > max) {
				max = playerScore.get(player);
			}
		}
		return max;
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		if (event.getPlayer().getWorld() == getGameArea().getPos().getWorld()) {
			if (!isRunning()) {
				spreadPlayers(Arrays.asList(event.getPlayer()), getGameArea());
				resetPlayer(event.getPlayer());
			}
			else {
				event.getPlayer().kickPlayer("Sorry a round of Team Trouble is already running!");
			}
		}
	}
	
	private void sendGameTitle (String title, String subtitle, int fadeIn, int duration, int fadeOut) {
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.sendTitle(title, subtitle, fadeIn, duration, fadeOut);
		}
	}

	@Override
	public Winner determineWinner() {
		List<Player> players = getScoreboard().getOnlinePlayers();
		players.sort((p1, p2) -> Integer.compare(playerScore.get(p2), playerScore.get(p1)));
		return players.isEmpty() ? null : new PlayerWinner(players.get(0), getScoreboard().getTeam(players.get(0)).getColor());
	}

	@Override
	public String getName() {
		return "Team Trouble";
	}

	@Override
	public void startLobby() {
		if (getScoreboard() != null) {
			spreadPlayers(getScoreboard().getOnlinePlayers(), getGameArea());
		}
	}

}
