package br.com.battlebits.ylobby.updater;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class ScoreboardUpdater extends UpdaterBase {

	public ScoreboardUpdater() {
		super(30L, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update() {
		if (Bukkit.getOnlinePlayers().length > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				yLobbyPlugin.getyLobby().getScoreboardManager().updateMainScoreboard(p);
			}
		}
	}

}
