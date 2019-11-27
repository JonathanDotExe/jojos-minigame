package at.jojokobi.minigamesplugin.stuctures;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;

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
		
		House.generateLootInventory(loot);
		
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
		System.out.println(floors);
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
