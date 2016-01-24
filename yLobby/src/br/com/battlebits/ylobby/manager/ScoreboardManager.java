package br.com.battlebits.ylobby.manager;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class ScoreboardManager {

	public void setupMainScoreboard(Player p) {
		new BukkitRunnable() {
			@Override
			public void run() {
				Scoreboard board = p.getScoreboard();
				Objective obj = board.registerNewObjective("mainScoreboard", "dummy");

				obj.setDisplayName("§6§LBATTLE§R§LBITS");

				obj.getScore("§4").setScore(5);

				obj.getScore("§3").setScore(4);
				board.registerNewTeam("onlineteam").addEntry("§3");
				board.getTeam("onlineteam").setPrefix("§7Online: ");
				board.getTeam("onlineteam")
						.setSuffix("§e" + yLobbyPlugin.getyLobby().getPlayerCountManager().getNetworkOnlinePlayers() + " jogadores");

				obj.getScore("§2").setScore(3);
				board.registerNewTeam("lobbyidteam").addEntry("§2");
				board.getTeam("lobbyidteam").setPrefix("§7Lobby: ");
				board.getTeam("lobbyidteam").setSuffix("§e" + yLobbyPlugin.getyLobby().getBungeeManager().getLobbyID());

				obj.getScore("§1").setScore(2);

				obj.getScore("§0").setScore(1);
				board.registerNewTeam("siteteam").addEntry("§0");
				board.getTeam("siteteam").setPrefix("§ewww.battle");
				board.getTeam("siteteam").setSuffix("§ebits.com.br");

				obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			}
		}.runTaskAsynchronously(yLobbyPlugin.getyLobby());
	}

	public void updateMainScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();

		board.getTeam("onlineteam").setSuffix("§e" + yLobbyPlugin.getyLobby().getPlayerCountManager().getNetworkOnlinePlayers() + " jogadores");
	}

}
