package br.com.battlebits.ylobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;

public class VipSlotsListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerPreLoginListener(PlayerLoginEvent e) {
		if (e.getResult() == Result.KICK_FULL) {
			if (Main.getPlugin().getPermissionManager().hasGroupPermission(e.getPlayer(), Group.LIGHT)) {
				if ((Bukkit.getOnlinePlayers().length + 1) <= 120) {
					e.allow();
				} else {
					e.disallow(Result.KICK_FULL, "§cInfelizmente, nosso servidor está sobrecarregado! D:\nTente novamente mais tarde!");
				}
			} else {
				e.disallow(Result.KICK_FULL, "§cNosso servidor está cheio, desculpe!");
			}
		}
	}

}
