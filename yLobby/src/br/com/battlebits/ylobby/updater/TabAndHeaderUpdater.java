package br.com.battlebits.ylobby.updater;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class TabAndHeaderUpdater extends UpdaterBase {

	public TabAndHeaderUpdater() {
		super(10L, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update() {
		if (Bukkit.getOnlinePlayers().length > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (yLobbyPlugin.getyLobby().getzUtils().getPlayerUtils().isPlayerOn18(p)) {
					yLobbyPlugin.getyLobby().getTabHeaderAndFooterManager().send(p);
				}
			}
		}
	}

}
