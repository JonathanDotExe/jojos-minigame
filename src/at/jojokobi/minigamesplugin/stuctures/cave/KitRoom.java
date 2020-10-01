package at.jojokobi.minigamesplugin.stuctures.cave;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;

import at.jojokobi.mcutil.generation.BasicGenUtil;
import at.jojokobi.minigamesplugin.kits.PlayerKit;
import at.jojokobi.minigamesplugin.kits.PlayerKits;
import at.jojokobi.minigamesplugin.stuctures.GameStructureBase;

public class KitRoom extends GameStructureBase{
		
	public KitRoom() {
		super(6, 6, 6);
	}

	@Override
	public void generate(Location loc, Random random) {
		Location place = loc.clone();
		place.add(-1, -1, -1);
		//Walls
		BasicGenUtil.generateCube(place, Material.STONE_BRICKS, b -> b.getType() != Material.AIR, null, getWidth() + 2, getHeight() + 2, getLength() + 2);
		//Clear
		place.add(1, 1, 1);
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
		PlayerKit kit = PlayerKits.KITS.get(random.nextInt(PlayerKits.KITS.size()));
		kit.give(((Chest) place.getBlock().getState()).getBlockInventory());
	}
	
}
