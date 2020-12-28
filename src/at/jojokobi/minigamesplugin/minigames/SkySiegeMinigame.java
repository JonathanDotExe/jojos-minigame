package at.jojokobi.minigamesplugin.minigames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
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
import at.jojokobi.minigamesplugin.maps.MapGenerator;
import at.jojokobi.minigamesplugin.minigames.components.ClimbComponent;
import at.jojokobi.minigamesplugin.minigames.components.StrengthComponent;
import at.jojokobi.minigamesplugin.scoreboard.CustomScoreboard;
import at.jojokobi.minigamesplugin.scoreboard.GlobalScore;
import at.jojokobi.minigamesplugin.util.Area;

public class SkySiegeMinigame extends BaseMinigame{
	
	public static final int ISLAND_GRID_STEP = 32;

	private int maxPlayers = 10;
	private int maxWaitTime = 20 * 20;
	
	private GlobalScore<Integer> timerScore;
	
	private List<PlayerKit> kits = new ArrayList<PlayerKit>();

	
	public SkySiegeMinigame(MapGenerator generator, MapGenerator lobbyGenerator, Area gameArea) {
		super(generator, lobbyGenerator, gameArea);
		
		kits.add(new PlayerKit(
			new ItemStack(Material.IRON_SWORD),
			new ItemStack(Material.IRON_PICKAXE),
			new ItemStack(Material.FISHING_ROD),
			new ItemStack(Material.IRON_HELMET),
			new ItemStack(Material.IRON_CHESTPLATE),
			new ItemStack(Material.IRON_LEGGINGS),
			new ItemStack(Material.IRON_BOOTS),
			new ItemStack(Material.COOKED_BEEF, 16),
			new ItemStack(Material.SHIELD),
			new ItemStack(Material.GOLDEN_APPLE, 5),
			new ItemStack(Material.WATER_BUCKET),
			new ItemStack(Material.LAVA_BUCKET),
			new ItemStack(Material.OAK_PLANKS, 64),
			new ItemStack(Material.BIRCH_PLANKS, 64),
			new ItemStack(Material.ENDER_PEARL, 3)
		));
		
		kits.add(new PlayerKit(
			new ItemStack(Material.STONE_SWORD),
			new ItemStack(Material.IRON_PICKAXE),
			new ItemStack(Material.BOW),
			new ItemStack(Material.GOLDEN_HELMET),
			new ItemStack(Material.GOLDEN_CHESTPLATE),
			new ItemStack(Material.GOLDEN_LEGGINGS),
			new ItemStack(Material.GOLDEN_BOOTS),
			new ItemStack(Material.COOKED_BEEF, 32),
			new ItemStack(Material.COOKED_MUTTON, 32),
			new ItemStack(Material.GOLDEN_APPLE, 4),
			new ItemStack(Material.SNOWBALL, 16),
			new ItemStack(Material.ARROW, 42),
			new ItemStack(Material.STONE, 64),
			new ItemStack(Material.STONE, 64),
			new ItemStack(Material.ENDER_PEARL, 3)
		));
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
			PlayerKit kit = kits.get(random.nextInt(kits.size()));
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
		return (getScoreboard().getOnlinePlayers().stream().filter(p -> p.getGameMode() == GameMode.SURVIVAL).count() <= 1);
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
	
	@Override
	protected void spreadPlayers (List<Player> players, Area gameArea) {
		Random random = new Random();
		for (Player player : players) {
			Location loc = gameArea.getPos().clone().add(random.nextInt((int) gameArea.getWidth()/ISLAND_GRID_STEP) * ISLAND_GRID_STEP, 0, random.nextInt((int) gameArea.getLength()/ISLAND_GRID_STEP) * ISLAND_GRID_STEP);
			loc.setY(loc.getWorld().getHighestBlockYAt(loc));
			player.teleport(loc);
		}
	}
	
	@EventHandler
	public void onEntityDamage (EntityDamageEvent event) {
		if (getGameArea().getPos().getWorld().getPlayers().contains(event.getEntity())) {
			//Prevent damage in lobby
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
	
	@Override
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		//let player die when they fall down
		//TODO sideeffects in the future maybe
		if (!isRunning()) {
			super.onPlayerMove(event);
		}
	}
	
	

}
