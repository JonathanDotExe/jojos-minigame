package at.jojokobi.minigamesplugin.minigames;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
import at.jojokobi.minigamesplugin.minigames.components.ChatRangeComponent;
import at.jojokobi.minigamesplugin.minigames.components.ClimbComponent;
import at.jojokobi.minigamesplugin.minigames.components.MeetingButtonComponent;
import at.jojokobi.minigamesplugin.minigames.components.StrengthComponent;
import at.jojokobi.minigamesplugin.scoreboard.CustomScoreboard;
import at.jojokobi.minigamesplugin.scoreboard.CustomTeam;
import at.jojokobi.minigamesplugin.scoreboard.GlobalScore;
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
	
	private MeetingButtonComponent meetingComponent;

	
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
		ChatRangeComponent chat= new ChatRangeComponent();
		addComponent(chat);
		meetingComponent = new MeetingButtonComponent(chat);
		addComponent(meetingComponent);
		addComponent(new StrengthComponent());
		super.init(plugin);
	}
	
	@Override
	public void start() {
		Random random = new Random();
		//Teleport player to spawn
		for (Player player : getScoreboard().getOnlinePlayers()) {
			player.teleport(getGameArea().getPos().clone().add((int) (getGameArea().getWidth()/32) * 16 - 8, 8, (int) (getGameArea().getLength()/32) * 16 - 8));
		}
		resetPlayers(getScoreboard().getOnlinePlayers());

		for (Player player : getScoreboard().getOnlinePlayers()) {
			//Display role
			player.sendTitle(getScoreboard().getTeam(player).getDisplayName(), getScoreboard().getTeam(player) == murdererTeam ? "Kill all innocents" : "Kill the murderers", 20, 80, 20);
			player.sendMessage("You are a " + getScoreboard().getTeam(player).getDisplayName());
			//Give kit
			PlayerKit kit = PlayerKits.KITS.get(random.nextInt(PlayerKits.KITS.size()));
			kit.give(player.getInventory());
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
		scoreboard.addTeam(murdererTeam = new CustomTeam(ChatColor.WHITE, "murderers", "murderer", true, true));
		scoreboard.addTeam(innocentTeam = new CustomTeam(ChatColor.WHITE, "innocents", "innocent", true, true));
	}
	
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		if (getGameArea().getPos().getWorld().getPlayers().contains(event.getEntity())) {
			//Prevent damage in protection time
			if (!isRunning() || getTime() < protectionTime || meetingComponent.isMeeting()) {
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
					Sign sign = (Sign) player.getLocation().getBlock().getState();
					sign.setLine(1, player.getName());
					sign.setLine(2, getScoreboard().getTeam(player).getDisplayName());
					sign.update();
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove (PlayerMoveEvent event) {
		if (getGameArea().getPos().getWorld().getPlayers().contains(event.getPlayer()) && isRunning() && meetingComponent.isMeeting()) {
			event.setCancelled(true);
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
				event.getPlayer().kickPlayer("Sorry a round of Murderer is already running!");
			}
		}
	}

	@Override
	protected void assignTeams(List<Player> players, CustomScoreboard board, Scoreboard scoreboardView) {
		System.out.println(players);
		int murdererAmount = players.size()/6 + 1;
		System.out.println(murdererAmount);
		for (Player player : players) {
			board.setTeam(player, innocentTeam, scoreboardView);
		}
		Collections.shuffle(players);
		players = players.subList(0, Math.min(players.size(), murdererAmount));
		for (Player player : players) {
			board.setTeam(player, murdererTeam, scoreboardView);
		}
	}

	@Override
	public Winner determineWinner() {
		if (getScoreboard().getOnlinePlayersInTeam(innocentTeam).stream().anyMatch(p -> p.getGameMode() == GameMode.SURVIVAL)) {
			return new TeamWinner(innocentTeam);
		}
		return new TeamWinner(murdererTeam);
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
