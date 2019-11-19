package at.jojokobi.minigamesplugin.stuctures;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.mcutil.generation.FurnitureGenUtil;
import at.jojokobi.mcutil.loot.LootInventory;

public class House implements GameStructure {
	
	private Material block1 = Material.AIR;
	private Material block2 = Material.AIR;
	private LootInventory loot = new LootInventory();
	

	public House(Material block1, Material block2) {
		super();
		this.block1 = block1;
		this.block2 = block2;
		/*
		chest.getInventory().addItem(new ItemStack(Material.LOG, random.nextInt(8)));
		chest.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, random.nextInt(6)));
		chest.getInventory().addItem(new ItemStack(Material.STRING, random.nextInt(4)));
		chest.getInventory().addItem(new ItemStack(Material.STICK, random.nextInt(16)));
		chest.getInventory().addItem(new ItemStack(Material.WOOD, random.nextInt(32)));
		chest.getInventory().addItem(new ItemStack(Material.DIRT, random.nextInt(16)));
		chest.getInventory().addItem(new ItemStack(Material.ARROW, random.nextInt(16)));
		chest.getInventory().addItem(new ItemStack(Material.EGG, random.nextInt(16)));
		if (type == ArenaType.CUBE)
			chest.getInventory().addItem(new ItemStack(Material.RABBIT_FOOT, random.nextInt(3)));
		if (random.nextBoolean())
			if (random.nextBoolean())
				chest.getInventory().addItem(new ItemStack(Material.BOW, 1));
		if (random.nextBoolean())
			if (random.nextBoolean())
				chest.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
		if (random.nextBoolean())
			chest.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
		chest.getInventory().addItem(new ItemStack(Material.IRON_INGOT, random.nextInt(5)));
		chest.getInventory().addItem(new ItemStack(Material.CARROT_ITEM, random.nextInt(6)));
		chest.getInventory().addItem(new ItemStack (Material.BAKED_POTATO, random.nextInt(4)));
		if (random.nextBoolean())
			if (random.nextBoolean())
				chest.getInventory().addItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		if (random.nextBoolean())
			if (random.nextBoolean())
				chest.getInventory().addItem(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		if (random.nextBoolean())
				if (random.nextBoolean())
					chest.getInventory().addItem(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		if (random.nextBoolean())
			if (random.nextBoolean())
				chest.getInventory().addItem(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		if (random.nextBoolean())		
			if (random.nextBoolean())
				chest.getInventory().addItem(new ItemStack(Material.GOLD_HOE, 1));
		chest.getInventory().addItem(new ItemStack(Material.SKULL_ITEM, random.nextInt(4), (byte) 1));
		if (type == ArenaType.LAVA)
			chest.getInventory().addItem(new ItemStack (Material.FIREBALL, random.nextInt(4)));*/
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		BasicGenUtil.generateCube(place.clone(), block2, 6, 6, 1);
		place.setY(place.getY() + 1);
		BasicGenUtil.generateCube(place.clone(), block1, 6, 6, 3);
		place.setX(place.getX() + 1);
		place.setZ(place.getZ() + 1);
		BasicGenUtil.generateCube(place.clone(), Material.AIR, 4, 4, 3);
		place.setX(place.getX() + 2);
		place.getBlock().setType(Material.CHEST);
		loot.fillInventory(((Chest) place.getBlock().getState()).getBlockInventory(), random, null);
		place.setX(place.getX() + 1);
		place.getBlock().setType(Material.CRAFTING_TABLE);
		place.setY(place.getY() + 1);
		place.getBlock().setType(Material.TORCH);
		place.setY(place.getY() - 1);
		place.setX(place.getX() - 4);
		FurnitureGenUtil.generateDoor(place, Material.OAK_DOOR, BlockFace.NORTH, false);
		place.setZ(place.getZ() - 1);
		place.setY(place.getY() + 3);
		BasicGenUtil.generateCube(place.clone(), Material.COBBLESTONE, 6, 6, 1);
	}

}
