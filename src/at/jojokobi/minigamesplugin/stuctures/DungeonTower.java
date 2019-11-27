package at.jojokobi.minigamesplugin.stuctures;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.loot.LootInventory;
import at.jojokobi.mcutil.loot.LootItem;

public class DungeonTower implements GameStructure{
	
	private Material block1;
	private Material block2;
	private int maxFloors;
	private LootInventory loot = new LootInventory();
	
	public static void generateLootInventory (LootInventory loot) {
		loot.addItem(new LootItem(1, new ItemStack(Material.TNT), 0, 6));
		//loot.addItem(new LootItem(1, new ItemStack(Material.COBBLESTONE), 0, 32));
		loot.addItem(new LootItem(1, new ItemStack(Material.ARROW), 0, 32));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.BOW), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(1, new ItemStack(Material.IRON_INGOT), 0, 10));
		loot.addItem(new LootItem(1, new ItemStack(Material.GOLD_INGOT), 0, 10));
		//loot.addItem(new LootItem(1, new ItemStack(Material.COAL), 0, 10));
		loot.addItem(new LootItem(1, new ItemStack(Material.CARROT), 0, 6));
		loot.addItem(new LootItem(1, new ItemStack(Material.BAKED_POTATO), 0, 6));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_CHESTPLATE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_LEGGINGS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_BOOTS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_HELMET), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.GOLDEN_HOE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_CHESTPLATE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.DIAMOND), 0, 2));
		loot.addItem(new LootItem(0.2, new ItemStack(Material.DIAMOND_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.2, new ItemStack(Material.DIAMOND_AXE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.2, new ItemStack(Material.ELYTRA), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(1, new ItemStack(Material.ENDER_PEARL), 0, 4));
		loot.addItem(new LootItem(1, new ItemStack(Material.FIREWORK_ROCKET), 0, 8));
		loot.addItem(new LootItem(1, new ItemStack(Material.WITHER_SKELETON_SKULL), 0, 9));
	}
 
	public DungeonTower(Material block1, Material block2, int maxFloors, LootItem... items) {
		super();
		this.block1 = block1;
		this.block2 = block2;
		this.maxFloors = maxFloors;
		
		generateLootInventory(loot);

		/*
		if (random.nextBoolean())
			chest.getInventory().addItem(new ItemStack(Material.SPECTRAL_ARROW, random.nextInt(6)));
		if (type == ArenaType.COLD)
			if (random.nextBoolean())
				chest.getInventory().addItem(new ItemStack (Material.COOKED_BEEF, random.nextInt(6)));
		if (random.nextBoolean())
			chest.getInventory().addItem(new ItemStack (Material.WATER_BUCKET, random.nextInt(2)));
		if (random.nextBoolean())
			chest.getInventory().addItem(new ItemStack (Material.LAVA_BUCKET, random.nextInt(2)));
		if (random.nextBoolean())
			chest.getInventory().addItem(new ItemStack (Material.MILK_BUCKET, random.nextInt(2)));
		chest.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, random.nextInt(9), (byte) 1));
		if (type == ArenaType.LAVA)
			chest.getInventory().addItem(new ItemStack (Material.FIREBALL, random.nextInt(6)));
		chest.getInventory().addItem(new ItemStack(Material.EMERALD, random.nextInt(2)));
		chest.getInventory().addItem(new ItemStack(Material.BLAZE_POWDER, random.nextInt(6)));
		chest.getInventory().addItem(new ItemStack(Material.FLINT, random.nextInt(6)));
		 */
		
		for (LootItem item : items) {
			loot.addItem(item);
		}
	}



	@Override
	public void generate(Location loc, Random random) {
		int floors = random.nextInt(maxFloors) + 1;
		System.out.println(floors);
		Location place = loc.clone();
		for (int i = 0; i < floors; i++){
			BasicGenUtil.generateCube(place, block1, 8, 8, 8);
			BasicGenUtil.generateCube(place, block1, s -> {
				int rand = random.nextInt(3);
				if (rand == 0) {
					s.setType(block2);
				}
			}, 8, 8, 8);
			place.setY(place.getY() + 1);
			place.setX(place.getX() + 1);
			place.setZ(place.getZ() + 1);
			BasicGenUtil.generateCube(place.clone(), Material.AIR, 6, 7, 6);
			place.getBlock().setType(Material.CHEST);
			loot.fillInventory(((Chest) place.getBlock().getState()).getBlockInventory(), random, null); 
			place.setX(place.getX() - 1);
			place.setZ(place.getZ() - 1);
			place.setY(place.getY() + 7);
		}
	}

}
