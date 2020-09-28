package at.jojokobi.minigamesplugin.kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerKit {
	
	private List<ItemStack> items = new ArrayList<ItemStack>();

	public PlayerKit(ItemStack... items) {
		super();
		this.items.addAll(Arrays.asList(items));
	}
	
	public void give (Inventory inv) {
		for (ItemStack itemStack : items) {
			inv.addItem(itemStack.clone());
		}
	}

}
