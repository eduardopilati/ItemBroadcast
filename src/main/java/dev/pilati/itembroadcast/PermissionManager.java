package dev.pilati.itembroadcast;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.permission.Permission;

public class PermissionManager {
	ItemBroadcast plugin;
	private Permission permission;

	public PermissionManager(ItemBroadcast plugin) {
		this.plugin = plugin;
	}

	public boolean hasPermission(CommandSender sender, String permissionKey) {
		if(permission != null) {
			return permission.has(sender, permissionKey);
		}
		
		return sender.hasPermission(permissionKey);
	}
	
	public void setupPermissions() {
		if(plugin.getServer().getPluginManager().isPluginEnabled("Vault")) {
			RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
			permission = rsp.getProvider();
		}
	}
}
