package dev.pilati.itembroadcast;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemBroadcastCommand implements CommandExecutor{
	ItemBroadcast plugin;

	public ItemBroadcastCommand(ItemBroadcast plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!plugin.permissionManager.hasPermission(sender, "ib.send")) {
			sender.sendMessage(ChatColor.RED + "[ItemBroadcast] No permission [ib.send] to run this command");
			return false;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "[ItemBroadcast] Only players can run this command");
		}
		
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInMainHand();
		
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.getInventory().addItem(item);
		}
		
		sender.sendMessage(ChatColor.GREEN + String.format("[ItemBroadcast] Items sent to %d players", Bukkit.getOnlinePlayers().size()));
		return true;
	}
}