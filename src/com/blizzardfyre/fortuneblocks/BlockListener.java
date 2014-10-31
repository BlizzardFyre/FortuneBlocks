package com.blizzardfyre.fortuneblocks;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled()) return;

		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

		if (Main.getMain().wasPlaced(e.getBlock().getLocation())) return;

		Material mat = e.getBlock().getType();
		if (!Main.containsMat(mat)) return;

		e.setCancelled(true);
		e.getBlock().setType(Material.AIR);
		e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(mat, getDropCount(e.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS))));
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Main.getMain().addPlaced(e.getBlock().getLocation());
	}

	public int getDropCount(int i) {
		Random random = new Random();
		int j = random.nextInt(i + 2) - 1;
		if (j < 0) j = 0;
		return (j + 1);
	}

}
