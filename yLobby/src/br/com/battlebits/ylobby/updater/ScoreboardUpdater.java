package br.com.battlebits.ylobby.updater;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class ScoreboardUpdater extends UpdaterBase {

	public ScoreboardUpdater() {
		super(40L, false);
	}

	@Override
	public void update() {
		if (Bukkit.getOnlinePlayers().size() > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
					yLobbyPlugin.getyLobby().getScoreboardManager().updateMainScoreboard(p);
				}
			}
		}
	}

}
