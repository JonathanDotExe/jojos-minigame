package at.jojokobi.minigamesplugin.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public final class PlayerKits {
	
	private static ItemStack createPotion(Material type, PotionData data) {
		ItemStack item = new ItemStack(type);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(data);
		item.setItemMeta(meta);
		
		return item;
	}
	
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
			new ItemStack(Material.IRON_SWORD),
			new ItemStack(Material.OAK_LEAVES, 64),
			new ItemStack(Material.EGG, 8),
			new ItemStack(Material.ENDER_PEARL, 2),
			createPotion(Material.POTION, new PotionData(PotionType.REGEN, false, false)),
			createPotion(Material.POTION, new PotionData(PotionType.NIGHT_VISION, true, false)),
			createPotion(Material.SPLASH_POTION, new PotionData(PotionType.INSTANT_DAMAGE, false, true)),
			createPotion(Material.LINGERING_POTION, new PotionData(PotionType.POISON, false, false)),
			createPotion(Material.POTION, new PotionData(PotionType.INVISIBILITY, false, false)),
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
	
	public static final List<PlayerKit> KITS = Arrays.asList(SWORD_KIT, BOW_KIT, BOMBER_KIT, BREWER_KIT, BERSERKER_KIT);
	
	private PlayerKits () {
		
	}
	
}
