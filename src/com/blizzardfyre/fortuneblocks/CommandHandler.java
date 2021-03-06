package com.blizzardfyre.fortuneblocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("fortuneblocks.command")) {
			sender.sendMessage(ChatColor.DARK_RED + "You do not have permission");
			return true;
		}

		if (args.length < 1 || args.length > 2) {
			help(sender);
			return true;
		}

		if (args[0].equalsIgnoreCase("add")) {
			Material mat;
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Non-players have to specify the item they want to add.");
					return true;
				}
				Player p = (Player) sender;
				if (p.getItemInHand().getType() == Material.AIR) {
					sender.sendMessage(ChatColor.RED + "You can't add air to the item list.");
					return true;
				}
				mat = p.getItemInHand().getType();
			} else mat = Material.getMaterial(args[1].toUpperCase());
			if (mat == null) {
				sender.sendMessage(Main.getPrefix() + ChatColor.RED + "That is not a valid material.");
				return true;
			}
			Main.getMain().addMaterial(mat);
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Material added.");
		} else if (args[0].equalsIgnoreCase("remove")) {
			Material mat;
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Non-players have to specify the item they want to remove.");
					return true;
				}
				Player p = (Player) sender;
				if (p.getItemInHand().getType() == Material.AIR) {
					sender.sendMessage(ChatColor.RED + "You can't remove nothing from the item list.");
					return true;
				}
				mat = p.getItemInHand().getType();
			} else mat = Material.getMaterial(args[1].toUpperCase());
			if (mat == null) {
				sender.sendMessage(Main.getPrefix() + ChatColor.RED + "That is not a valid material.");
				return true;
			}
			Main.getMain().removeMaterial(mat);
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Material removed.");
		} else {
			help(sender);
		}

		return true;
	}

	public void help(CommandSender sender) {
		sender.sendMessage(Main.getPrefix() + ChatColor.RED + "/fortuneblocks add [block]");
		sender.sendMessage(Main.getPrefix() + ChatColor.RED + "/fortuneblocks remove [block]");
	}

}
