package dev.pilati.itembroadcast;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ItemBroadcastCommand implements CommandExecutor{
	ItemBroadcast plugin;

	public ItemBroadcastCommand(ItemBroadcast plugin) {
		this.plugin = plugin;
	}

	// /itembroadcast [quantidade] [-p perm]
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		ItemStack item;
		String permission = null;
		
		if(!plugin.permissionManager.hasPermission(sender, "itembroadcast.send")) {
			sender.sendMessage(this.getConfig("messages.noPermission").replace("{permission}", "[itembroadcast.send]"));
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(this.getConfig("messages.onlyPlayers"));
			return true;
		}
		
		if(args.length == 0 || "help".equalsIgnoreCase(args[0])) {
			this.showHelp(sender);
			return true;
		}
		
		if(Integer.valueOf(args[0]) <= 0 || args.length == 2 || args.length > 3) {
			sender.sendMessage(this.getConfig("messages.incorrectUsage"));
			return true;
		}
		
		if(args.length == 3) {
			permission = String.valueOf(args[2]).trim();
			if(!"-p".equalsIgnoreCase(args[1]) || !(permission.length() > 0)) {
				sender.sendMessage(this.getConfig("messages.incorrectUsage"));
				return true;
			}
			
			permission = "itembroadcast." + permission;
		}
		
		player = (Player) sender;
		item = player.getInventory().getItemInMainHand().clone();
		item.setAmount(Integer.valueOf(args[0]));
		
		if(item.getType().isAir()) {
			sender.sendMessage(this.getConfig("messages.noItem"));
			return true;
		}
		
		int players = 0;
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if(permission != null) {
				if(!plugin.permissionManager.hasPermission(sender, permission)) {
					continue;
				}
			}
			onlinePlayer.getInventory().addItem(item);
			players++;
		}
		
		sender.sendMessage(String.format(this.getConfig("messages.sent"), players));
		return true;
	}

	private void showHelp(CommandSender sender) {
		List<String> lines = plugin.getConfig().getStringList("messages.help");
		for(String line : lines) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
		}
	}
	
	private String getConfig(String configPath) {
		String message = plugin.getConfig().getString(configPath);
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}