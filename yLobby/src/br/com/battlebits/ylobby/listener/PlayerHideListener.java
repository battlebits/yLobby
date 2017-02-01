package br.com.battlebits.ylobby.listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.battlebits.commons.bukkit.event.vanish.PlayerShowToPlayerEvent;
import br.com.battlebits.commons.core.account.BattlePlayer;
import br.com.battlebits.commons.core.permission.Group;
import br.com.battlebits.ylobby.LobbyMain;
import lombok.Getter;

public class PlayerHideListener implements Listener {

	@Getter
	private static HashMap<UUID, Long> uuidCooldown = new HashMap<>();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuitListener(PlayerQuitEvent e) {
		uuidCooldown.remove(e.getPlayer().getUniqueId());
		LobbyMain.getInstance().getPlayerHideManager().tryToRemoveFromLists(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onVanish(PlayerShowToPlayerEvent event) {
		if (LobbyMain.getInstance().getPlayerHideManager().isHiding(event.getToPlayer())) {
			if (!BattlePlayer.getPlayer(event.getPlayer().getUniqueId()).hasGroupPermission(Group.LIGHT)) {
				event.setCancelled(true);
			}
		}
	}

}
