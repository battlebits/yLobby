package br.com.battlebits.ylobby.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.core.permission.Group;

public class VipSlotsListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerPreLoginListener(PlayerLoginEvent e) {
		if (e.getResult() == Result.KICK_FULL) {
			if (BattlebitsAPI.getAccountCommon().getBattlePlayer(e.getPlayer().getUniqueId())
					.hasGroupPermission(Group.LIGHT)) {
				if ((Bukkit.getOnlinePlayers().size() + 1) <= 100) {
					e.allow();
				} else {
					e.disallow(Result.KICK_FULL,
							"§cInfelizmente, nosso servidor está sobrecarregado! D:\nTente novamente mais tarde!");
				}
			} else {
				e.disallow(Result.KICK_FULL, "§cNosso servidor está cheio, desculpe!");
			}
		}
	}

}
