package at.jojokobi.minigamesplugin.minigames;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Sign;
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
import at.jojokobi.minigamesplugin.util.StaticUtils;

public class MurdererMinigame extends BaseMinigame{

	private int gameDuration = 30 * 60 * 20;
	private int maxPlayers = 10;
	private int maxWaitTime = 20 * 20;
	private int protectionTime = 60 * 20;
	
	private GlobalScore<Integer> timerScore;
	
	private CustomTeam murdererTeam;
	private CustomTeam innocentTeam;

	
	public MurdererMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
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
		super.init(plugin);
	}
	
	@Override
	public void start() {
		spreadPlayers(getScoreboard().getOnlinePlayers(), getGameArea());
		resetPlayers(getScoreboard().getOnlinePlayers());
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
		return getTime() >= protectionTime && (getTime() >= gameDuration || getScoreboard().getPlayers().isEmpty() || getScoreboard().getOnlinePlayersInTeam(innocentTeam).stream().allMatch(p -> p.getGameMode() != GameMode.SURVIVAL) || getScoreboard().getOnlinePlayersInTeam(murdererTeam).stream().allMatch(p -> p.getGameMode() != GameMode.SURVIVAL));
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
		//Teams
		scoreboard.addTeam(murdererTeam = new CustomTeam(ChatColor.WHITE, "murderers", "Murderers", true, true));
		scoreboard.addTeam(innocentTeam = new CustomTeam(ChatColor.WHITE, "innocents", "Innocents", true, true));
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
					//Will die
					event.setDamage(0);
					player.setGameMode(GameMode.SPECTATOR);
					//Get killer
					Player killer = StaticUtils.getDamagingPlayer(event);
					if (killer != null) {
						//Death messages
						player.sendMessage(killer.getName() + " killed you!");
						killer.sendMessage("You killed " + player.getName());
						
						//Innocent/murderer
						if (getScoreboard().getTeam(player) == innocentTeam) {
							killer.sendMessage(player.getName() + " was an innocent!");
						}
						else {
							killer.sendMessage(player.getName() + " was a murderer!");
						}
						//Kill killer if both are innocent
						if (getScoreboard().getTeam(killer) == innocentTeam && getScoreboard().getTeam(player) == innocentTeam) {
							killer.damage(killer.getHealth());
							player.sendMessage(killer.getName() + " died because he was an innocent too!");
							killer.sendMessage("You died because " + player.getName() + " was an innocent too!");
						}			
						else if (getScoreboard().getTeam(player) == murdererTeam) {
							sendGameMessage(player.getName() + " died and was a murderer!");
						}
					}
					//Death sign
					player.getLocation().getBlock().setType(Material.OAK_SIGN);
					Sign sign = (Sign) player.getLocation().getBlock();
					sign.setLine(1, player.getName());
					sign.setLine(2, getScoreboard().getTeam(player).getDisplayName());
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
				event.getPlayer().kickPlayer("Sorry a round of Team Trouble is already running!");
			}
		}
	}
	
	private void sendGameTitle (String title, String subtitle, int fadeIn, int duration, int fadeOut) {
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.sendTitle(title, subtitle, fadeIn, duration, fadeOut);
		}
	}
	
	private void sendGameMessage (String message) {
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}


	@Override
	public Winner determineWinner() {
		List<Player> players = getScoreboard().getOnlinePlayers();
		System.out.println(players);
		players.sort((p1, p2) -> Integer.compare(playerScore.get(p2), playerScore.get(p1)));
		return players.isEmpty() ? null : new PlayerWinner(players.get(0), getScoreboard().getTeam(players.get(0)).getColor());
	}

	@Override
	public String getName() {
		return "Murderer";
	}

	@Override
	public void startLobby() {
		if (getScoreboard() != null) {
			spreadPlayers(getScoreboard().getOnlinePlayers(), getGameArea());
		}
	}

}
