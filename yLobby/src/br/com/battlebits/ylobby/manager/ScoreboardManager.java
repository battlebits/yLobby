package br.com.battlebits.ylobby.manager;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.core.account.BattlePlayer;
import br.com.battlebits.commons.core.account.Tag;
import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.LobbyMain;

public class ScoreboardManager {

	public void setupMainScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();
		BattlePlayer account = BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId());
		Objective obj = board.registerNewObjective("mainScoreboard", "dummy");

		obj.setDisplayName("§6§LBATTLE§R§LBITS");

		obj.getScore("§a").setScore(11);

		obj.getScore("§9").setScore(10);
		board.registerNewTeam("rankteam").addEntry("§9");
		board.getTeam("rankteam").setPrefix("§7Rank: ");
		board.getTeam("rankteam")
				.setSuffix(Tag.valueOf(account.getServerGroup().toString()).getPrefix(account.getLanguage()));

		obj.getScore("§8").setScore(9);
		board.registerNewTeam("ligateam").addEntry("§8");
		board.getTeam("ligateam").setPrefix("§7§%league%§: ");
		board.getTeam("ligateam").setSuffix(account.getLeague().getSymbol() + " " + account.getLeague().toString());

		obj.getScore("§7").setScore(8);
		board.registerNewTeam("xpteam").addEntry("§7");
		board.getTeam("xpteam").setPrefix("§7XP: ");
		board.getTeam("xpteam").setSuffix("§b" + account.getXp());

		obj.getScore("§6").setScore(7);
		board.registerNewTeam("moedasteam").addEntry("§6");
		board.getTeam("moedasteam").setPrefix("§7§%coins%§: ");
		board.getTeam("moedasteam").setSuffix("§b" + account.getMoney());

		obj.getScore("§5").setScore(6);
		board.registerNewTeam("fichasteam").addEntry("§5");
		board.getTeam("fichasteam").setPrefix("§7§%tickets%§: ");
		board.getTeam("fichasteam").setSuffix("§b" + account.getFichas());

		obj.getScore("§4").setScore(5);

		obj.getScore("§3").setScore(4);
		board.registerNewTeam("onlineteam").addEntry("§3");
		board.getTeam("onlineteam").setPrefix("§7Online: ");
		board.getTeam("onlineteam")
				.setSuffix("§e"
						+ LobbyMain.getInstance().getServerManager().getBalancer(ServerType.NETWORK).getTotalNumber()
						+ " §%players%§");

		obj.getScore("§2").setScore(3);
		board.registerNewTeam("lobbyidteam").addEntry("§2");
		board.getTeam("lobbyidteam").setPrefix("§7Lobby: ");
		board.getTeam("lobbyidteam").setSuffix("§e" + LobbyMain.getInstance().getLobbyID());

		obj.getScore("§1").setScore(2);

		obj.getScore("§0").setScore(1);
		board.registerNewTeam("siteteam").addEntry("§0");
		board.getTeam("siteteam").setPrefix("§6www.battle");
		board.getTeam("siteteam").setSuffix("§6bits.com.br");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public void updateMainScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();

		board.getTeam("onlineteam")
				.setSuffix("§e"
						+ LobbyMain.getInstance().getServerManager().getBalancer(ServerType.NETWORK).getTotalNumber()
						+ " §%players%§");
	}

}
