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

public class HouseTower implements GameStructure {
	
	private Material block1 = Material.AIR;
	private Material block2 = Material.AIR;
	private int maxFloors;
	private LootInventory loot = new LootInventory();
	

	public HouseTower(Material block1, Material block2, int maxFloors, LootItem... items) {
		super();
		this.block1 = block1;
		this.block2 = block2;
		this.maxFloors = maxFloors;
		loot.addItem(new LootItem(1, new ItemStack(Material.DARK_OAK_LOG), 0, 8));
		loot.addItem(new LootItem(1, new ItemStack(Material.STRING), 0, 4));
		loot.addItem(new LootItem(1, new ItemStack(Material.STICK), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.OAK_PLANKS), 0, 32));
		loot.addItem(new LootItem(1, new ItemStack(Material.DIRT), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.ARROW), 0, 16));
		loot.addItem(new LootItem(1, new ItemStack(Material.EGG), 0, 16));
		//loot.addItem(new LootItem(1, new ItemStack(Material.RABBIT_FOOT), 0, 3));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.BOW), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.IRON_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.5, new ItemStack(Material.STONE_SWORD), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.IRON_INGOT), 0, 5));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.CARROT), 0, 6));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.IRON_INGOT), 0, 5));
		loot.addItem(new LootItem(0.1, new ItemStack(Material.BAKED_POTATO), 0, 4));
		
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_CHESTPLATE), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_BOOTS), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.CHAINMAIL_HELMET), 1, 1).setEnchant(true).setDamage(false));
		loot.addItem(new LootItem(0.25, new ItemStack(Material.GOLDEN_HOE), 1, 1).setEnchant(true).setDamage(false));
		
		loot.addItem(new LootItem(1, new ItemStack(Material.WITHER_SKELETON_SKULL), 0, 4));
		
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
		int floors = random.nextInt(maxFloors) + 1;
		for (int i = 0; i < floors; i++){
			//Floor
			BasicGenUtil.generateCube(place.clone(), block2, 8, 1, 8);
			place.setY(place.getY() + 1);
			//Room
			BasicGenUtil.generateCube(place.clone(), block1, 8, 6, 8);
			place.setX(place.getX() + 1);
			place.setZ(place.getZ() + 1);
			BasicGenUtil.generateCube(place.clone(), Material.AIR, 6, 6, 6);
			place.setX(place.getX() + 4);
			place.getBlock().setType(Material.CHEST);
			loot.fillInventory(((Chest) place.getBlock().getState()).getBlockInventory(), random, null);
			place.setX(place.getX() + 1);
			place.getBlock().setType(Material.CRAFTING_TABLE);
			place.setY(place.getY() + 1);
			place.getBlock().setType(Material.TORCH);
			place.setY(place.getY() - 1);
			place.setX(place.getX() - 6);
			if (i == 0){
				FurnitureGenUtil.generateDoor(place.clone(), Material.OAK_DOOR, BlockFace.NORTH, false);
			}
			else{
				BasicGenUtil.generateCube(place.clone(), Material.GLASS_PANE, 1, 4, 6);
			}
			place.setZ(place.getZ() - 1);
			place.setY(place.getY() + 5);
			BasicGenUtil.generateCube(place.clone(), block2, 8, 1, 8);
		}
	}

}
