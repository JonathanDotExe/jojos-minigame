package at.jojokobi.minigamesplugin.stuctures.cave;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.loot.LootInventory;
import at.jojokobi.mcutil.loot.LootItem;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class LootRoom extends GameStructureBase{
	
	private LootInventory loot = new LootInventory();
	
	public static void generateLootInventory (LootInventory loot) {
		loot.addItem(new LootItem(1, new ItemStack(Material.OAK_LOG), 0, 8));
		loot.addItem(new LootItem(1, new ItemStack(Material.STRING), 0, 4));
		loot.addItem(new LootItem(1, new ItemStack(Material.STICK), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.OAK_PLANKS), 0, 32));
		loot.addItem(new LootItem(1, new ItemStack(Material.ARROW), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.EGG), 0, 16));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.BOW), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_AXE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.SHIELD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.STONE_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.CARROT), 0, 6));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.IRON_INGOT), 2, 16));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.BAKED_POTATO), 0, 6));
		
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_CHESTPLATE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_BOOTS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_HELMET), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.GOLDEN_HOE), 1, 1).setEnchant(true).setDamage(false));
		
		loot.addItem(new LootItem(0.5, new ItemStack(Material.ENCHANTED_BOOK), 1, 1).setEnchant(true));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.ENCHANTED_BOOK), 1, 1).setEnchant(true));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.ENCHANTED_BOOK), 1, 1).setEnchant(true));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.EXPERIENCE_BOTTLE), 1, 16));
		
		loot.addItem(new LootItem(1, new ItemStack(Material.COOKED_BEEF), 0, 4));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.TORCH), 1, 16));
		
		loot.addItem(new LootItem(1, new ItemStack(Material.WITHER_SKELETON_SKULL), 0, 4));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.SPECTRAL_ARROW), 0, 6));
	}

	
	public LootRoom() {
		super(6, 6, 6);
		
		generateLootInventory(loot);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		//Clear
		BasicGenUtil.generateCube(place, Material.AIR, getWidth(), getHeight(), getLength());
		//Crafting table
		place.getBlock().setType(Material.CRAFTING_TABLE);
		//Furnace
		place.add(1, 0, 0);
		place.getBlock().setType(Material.FURNACE);
		//Torch
		place.add(0, 1, 0);
		place.getBlock().setType(Material.TORCH);
		place.add(0, -1, 0);
		//Chest
		place.add(1, 0, 0);
		place.getBlock().setType(Material.CHEST);
		loot.fillInventory(((Chest) place.getBlock().getState()).getBlockInventory(), random, null);
	}
	
}
