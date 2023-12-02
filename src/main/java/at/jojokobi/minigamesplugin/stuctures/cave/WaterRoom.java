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

public class WaterRoom extends GameStructureBase{
	
	private LootInventory loot = new LootInventory();
	
	public static void generateLootInventory (LootInventory loot) {
		loot.addItem(new LootItem(1, new ItemStack(Material.TNT), 0, 6));
		loot.addItem(new LootItem(1, new ItemStack(Material.COBBLESTONE), 0, 32));
		loot.addItem(new LootItem(1, new ItemStack(Material.ARROW), 0, 32));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.BOW), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(1, new ItemStack(Material.IRON_INGOT), 0, 10));
		loot.addItem(new LootItem(1, new ItemStack(Material.GOLD_INGOT), 0, 10));
		loot.addItem(new LootItem(1, new ItemStack(Material.SEA_LANTERN), 0, 10));
		loot.addItem(new LootItem(1, new ItemStack(Material.CARROT), 0, 6));
		loot.addItem(new LootItem(1, new ItemStack(Material.BAKED_POTATO), 0, 6));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_CHESTPLATE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_LEGGINGS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_BOOTS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_HELMET), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.GOLDEN_HOE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(1, new ItemStack(Material.ENDER_PEARL), 0, 4));
		loot.addItem(new LootItem(1, new ItemStack(Material.FIREWORK_ROCKET), 0, 8));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.SNOWBALL), 1, 16));
	}

	public WaterRoom() {
		super(8, 8, 8);
		generateLootInventory(loot);
	}

	@Override
	public void generate(Location loc, Random random) {
		loc.add(0, -1, 0);
		BasicGenUtil.generateCube(loc, Material.AIR, getWidth(), getHeight(), getLength());
		loc.add(0, getHeight() - 1, 0);
		BasicGenUtil.generateCube(loc, Material.WATER, getWidth(), getHeight(), getLength());
		loc.add(0, -getHeight() + 1, 0);
		//Chest
		loc.getBlock().setType(Material.CHEST);
		loot.fillInventory(((Chest) loc.getBlock().getState()).getBlockInventory(), random, null);
	}

}
