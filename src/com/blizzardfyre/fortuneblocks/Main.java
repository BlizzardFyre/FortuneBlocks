package com.blizzardfyre.fortuneblocks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static List<String> materials = new ArrayList<String>();
	private static List<String> placed = new ArrayList<String>();
	private static String prefix = null;
	private File placedFile = null;
	private YamlConfiguration placedConfig = null;

	public void onEnable() {
		if (getConfig().contains("palced")) {
			File oldFile = new File(getDataFolder(), "config.yml");
			YamlConfiguration yml = YamlConfiguration.loadConfiguration(oldFile);
			oldFile.delete();
			System.out.println("t");
			saveDefaultConfig();
			reloadConfig();
			getConfig().set("blocks", yml.getStringList("blocks"));
			getConfig().set("prefix", yml.getString("prefix"));
			saveConfig();
		} else {
			saveDefaultConfig();
		}
		File placedFile = new File(getDataFolder(), "placed.yml");
		if (!placedFile.exists()) {
			try {
				placedFile.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		this.placedFile = placedFile;
		placedConfig = YamlConfiguration.loadConfiguration(placedFile);
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix")) + " ";
		for (String string : getConfig().getStringList("blocks"))
			materials.add(string.toUpperCase());
		for (String string : placedConfig.getStringList("placed"))
			placed.add(string);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		getCommand("fortuneblocks").setExecutor(new CommandHandler());
	}

	public void addMaterial(Material mat) {
		materials.add(mat.toString());
		getConfig().set("blocks", materials);
		saveConfig();
	}

	public static boolean containsMat(Material mat) {
		if (materials.contains(mat.toString()))
			return true;
		else
			return false;
	}

	public void removeMaterial(Material mat) {
		materials.remove(mat.toString());
		getConfig().set("blocks", materials);
		saveConfig();
	}

	public void addPlaced(Location loc) {
		placed.add(locationToString(loc));
		placedConfig.set("placed", placed);
		try {
			placedConfig.save(placedFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean wasPlaced(Location loc) {
		if (placed.contains(locationToString(loc)))
			return true;
		else
			return false;
	}

	public static String locationToString(Location loc) {
		return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
	}

	public static Main getMain() {
		return (Main) Bukkit.getPluginManager().getPlugin("FortuneBlocks");
	}

	public static String getPrefix() {
		return prefix;
	}

}
