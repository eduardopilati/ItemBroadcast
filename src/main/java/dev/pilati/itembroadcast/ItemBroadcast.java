package dev.pilati.itembroadcast;

import org.bukkit.plugin.java.JavaPlugin;

public class ItemBroadcast extends JavaPlugin {
	PermissionManager permissionManager = new PermissionManager(this);
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.getCommand("itembroadcast").setExecutor(new ItemBroadcastCommand(this));
		permissionManager.setupPermissions();
	}
}
