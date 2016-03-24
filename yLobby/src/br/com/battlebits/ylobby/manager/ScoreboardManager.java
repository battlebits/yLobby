package br.com.battlebits.ylobby.manager;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.permissions.enums.Group;
import me.flame.utils.ranking.constructors.Account;

public class ScoreboardManager {

	public void setupMainScoreboard(Player p) {
		new BukkitRunnable() {
			@Override
			public void run() {
				Scoreboard board = p.getScoreboard();
				Account account = Main.getPlugin().getRankingManager().getAccount(p.getUniqueId());
				Objective obj = board.registerNewObjective("mainScoreboard", "dummy");

				obj.setDisplayName("§6§LBATTLE§R§LBITS");

				obj.getScore("§a").setScore(11);

				obj.getScore("§9").setScore(10);
				board.registerNewTeam("rankteam").addEntry("§9");
				board.getTeam("rankteam").setPrefix("§7Rank: ");
				if (Main.getPlugin().getPermissionManager().getPlayerGroup(p.getUniqueId()) != Group.NORMAL) {
					board.getTeam("rankteam").setSuffix(yLobbyPlugin.getyLobby().getzUtils().getTagUtils()
							.getDefaultTag(Main.getPlugin().getPermissionManager().getPlayerGroup(p.getUniqueId())).getPrefix());
				} else {
					board.getTeam("rankteam").setSuffix("§bNenhum");
				}

				obj.getScore("§8").setScore(9);
				board.registerNewTeam("ligateam").addEntry("§8");
				board.getTeam("ligateam").setPrefix("§7Liga: ");
				board.getTeam("ligateam").setSuffix(account.getLiga().getSymbol() + " " + account.getLiga().toString());

				obj.getScore("§7").setScore(8);
				board.registerNewTeam("xpteam").addEntry("§7");
				board.getTeam("xpteam").setPrefix("§7XP: ");
				board.getTeam("xpteam").setSuffix("§b" + account.getXp());

				obj.getScore("§6").setScore(7);
				board.registerNewTeam("moedasteam").addEntry("§6");
				board.getTeam("moedasteam").setPrefix("§7Moedas: ");
				board.getTeam("moedasteam").setSuffix("§b" + account.getMoney());

				obj.getScore("§5").setScore(6);
				board.registerNewTeam("fichasteam").addEntry("§5");
				board.getTeam("fichasteam").setPrefix("§7Fichas: ");
				board.getTeam("fichasteam").setSuffix("§b" + account.getFichas());

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
				board.getTeam("siteteam").setPrefix("§6www.battle");
				board.getTeam("siteteam").setSuffix("§6bits.com.br");

				obj.setDisplaySlot(DisplaySlot.SIDEBAR);

			}
		}.runTaskAsynchronously(yLobbyPlugin.getyLobby());
	}

	public void updateMainScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();

		board.getTeam("onlineteam").setSuffix("§e" + yLobbyPlugin.getyLobby().getPlayerCountManager().getNetworkOnlinePlayers() + " jogadores");
	}

}
