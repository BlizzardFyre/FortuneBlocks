package com.blizzardfyre.fortuneblocks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("fortuneblocks.command")) {
			sender.sendMessage(ChatColor.DARK_RED + "You do not have permission");
			return true;
		}

		if (args.length != 2) {
			help(sender);
			return true;
		}

		if (args[0].equalsIgnoreCase("add")) {
			Material mat = Material.getMaterial(args[1].toUpperCase());
			if (mat == null) {
				sender.sendMessage(Main.getPrefix() + ChatColor.RED + "That is not a valid material.");
				return true;
			}
			Main.getMain().addMaterial(mat);
			sender.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Material added.");
		} else if (args[0].equalsIgnoreCase("remove")) {
			Material mat = Material.getMaterial(args[1].toUpperCase());
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
