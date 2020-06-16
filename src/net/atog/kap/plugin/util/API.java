package net.atog.kap.plugin.util;

import org.bukkit.ChatColor;

public class API {
	public static String color(String txt) {
		return ChatColor.translateAlternateColorCodes('&', txt);
	}
	
	public static String[] color(String[] txt) {
		String[] result = {};
		for(int i = 0; i<txt.length; i++) {
		}
		return result;
	}
	
}
