package at.jojokobi.minigamesplugin.stuctures;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.generation.FurnitureGenUtil;
import at.jojokobi.mcutil.loot.LootInventory;
import at.jojokobi.mcutil.loot.LootItem;

public class House extends GameStructureBase {
	
	private Material block1 = Material.AIR;
	private Material block2 = Material.AIR;
	private LootInventory loot = new LootInventory();
	
	public static void generateLootInventory (LootInventory loot) {
		loot.addItem(new LootItem(1, new ItemStack(Material.OAK_LOG), 0, 8));
		loot.addItem(new LootItem(1, new ItemStack(Material.STRING), 0, 4));
		loot.addItem(new LootItem(1, new ItemStack(Material.STICK), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.OAK_PLANKS), 0, 32));
		loot.addItem(new LootItem(1, new ItemStack(Material.DIRT), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.ARROW), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.EGG), 0, 16));
		//loot.addItem(new LootItem(1, new ItemStack(Material.RABBIT_FOOT), 0, 3));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.BOW), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_AXE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.SHIELD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.STONE_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.IRON_INGOT), 0, 5));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.CARROT), 0, 6));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.IRON_INGOT), 0, 5));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.BAKED_POTATO), 0, 6));
		
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_CHESTPLATE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_BOOTS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_HELMET), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.GOLDEN_HOE), 1, 1).setEnchant(true).setDamage(false));
		
		loot.addItem(new LootItem(1, new ItemStack(Material.COOKED_BEEF), 0, 4));
		
		loot.addItem(new LootItem(1, new ItemStack(Material.WITHER_SKELETON_SKULL), 0, 4));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.SPECTRAL_ARROW), 0, 6));
	}

	public House(Material block1, Material block2, LootItem... items) {
		super(6, 6, 5);
		this.block1 = block1;
		this.block2 = block2;
		
		generateLootInventory(loot);
		
		for (LootItem item : items) {
			loot.addItem(item);
		}
		//loot.addItem(new LootItem(1, new ItemStack(Material.FIRE_CHARGE), 0, 4));

		
		/*
		chest.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, random.nextInt(4), (byte) 1));
		if (type == ArenaType.LAVA)
			chest.getInventory().addItem(new ItemStack (Material.FIREBALL, random.nextInt(4)));*/
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		//Floor
		BasicGenUtil.generateCube(place.clone(), block2, 6, 1, 6);
		place.setY(place.getY() + 1);
		//Room
		BasicGenUtil.generateCube(place.clone(), block1, 6, 3, 6);
		place.setX(place.getX() + 1);
		place.setZ(place.getZ() + 1);
		BasicGenUtil.generateCube(place.clone(), Material.AIR, 4, 3, 4);
		place.setX(place.getX() + 2);
		//Funiture
		place.getBlock().setType(Material.CHEST);
		loot.fillInventory(((Chest) place.getBlock().getState()).getBlockInventory(), random, null);
		place.setX(place.getX() + 1);
		place.getBlock().setType(Material.CRAFTING_TABLE);
		place.setY(place.getY() + 1);
		place.getBlock().setType(Material.TORCH);
		place.setY(place.getY() - 1);
		place.setX(place.getX() - 4);
		FurnitureGenUtil.generateDoor(place.clone(), Material.OAK_DOOR, BlockFace.NORTH, false);
		place.setZ(place.getZ() - 1);
		place.setY(place.getY() + 3);
		BasicGenUtil.generateCube(place.clone(), block2, 6, 1, 6);
	}

}
