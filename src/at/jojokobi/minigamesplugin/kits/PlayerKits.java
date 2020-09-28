package at.jojokobi.minigamesplugin.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class PlayerKits {
	
	public static final PlayerKit SWORD_KIT = new PlayerKit(
			new ItemStack(Material.DIAMOND_SWORD),
			new ItemStack(Material.OAK_PLANKS, 32),
			new ItemStack(Material.STONE_PICKAXE),
			new ItemStack(Material.IRON_CHESTPLATE),
			new ItemStack(Material.IRON_LEGGINGS),
			new ItemStack(Material.STICK, 16),
			new ItemStack(Material.EGG, 8),
			new ItemStack(Material.RABBIT_FOOT, 1)
		);
	
	public static final PlayerKit BOW_KIT = new PlayerKit(
			new ItemStack(Material.BOW),
			new ItemStack(Material.COBBLESTONE, 32),
			new ItemStack(Material.ARROW, 32),
			new ItemStack(Material.SPECTRAL_ARROW, 4),
			new ItemStack(Material.STONE_PICKAXE),
			new ItemStack(Material.CHAINMAIL_CHESTPLATE),
			new ItemStack(Material.CHAINMAIL_LEGGINGS),
			new ItemStack(Material.STICK, 16),
			new ItemStack(Material.SNOWBALL, 8)
		);
	public static final PlayerKit BOMBER_KIT = new PlayerKit(
			new ItemStack(Material.GOLDEN_HOE),
			new ItemStack(Material.ICE, 32),
			new ItemStack(Material.WITHER_SKELETON_SKULL, 32),
			new ItemStack(Material.STONE_PICKAXE),
			new ItemStack(Material.DIAMOND_HELMET),
			new ItemStack(Material.EGG, 16),
			new ItemStack(Material.EGG, 16),
			new ItemStack(Material.TNT, 32),
			new ItemStack(Material.SNOWBALL, 16)
		);
	public static final PlayerKit BREWER_KIT = new PlayerKit(
			new ItemStack(Material.STONE_SWORD),
			new ItemStack(Material.POTION, 1),	//TODO potions with effects
			new ItemStack(Material.STONE_PICKAXE)
		);
	public static final PlayerKit BERSERKER_KIT = new PlayerKit(
			new ItemStack(Material.DIAMOND_AXE),
			new ItemStack(Material.OAK_LOG, 16),
			new ItemStack(Material.WITHER_SKELETON_SKULL, 16),
			new ItemStack(Material.IRON_PICKAXE),
			new ItemStack(Material.DIAMOND_LEGGINGS),
			new ItemStack(Material.EGG, 16),
			new ItemStack(Material.TNT, 4)
		);
	
	private PlayerKits () {
		
	}
	
}
