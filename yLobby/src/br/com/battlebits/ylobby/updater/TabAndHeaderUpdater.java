package br.com.battlebits.ylobby.updater;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.api.player.PingAPI;
import br.com.battlebits.commons.api.tablist.TabListAPI;
import br.com.battlebits.commons.core.account.BattlePlayer;
import br.com.battlebits.commons.core.server.ServerType;
import br.com.battlebits.ylobby.LobbyMain;

public class TabAndHeaderUpdater extends UpdaterBase {

	public TabAndHeaderUpdater() {
		super(30L, false);
	}

	@Override
	public void update() {
		if (Bukkit.getOnlinePlayers().size() > 0) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				send(p);
			}
		}
	}

	public static void send(Player p) {
		BattlePlayer account = BattlebitsAPI.getAccountCommon().getBattlePlayer(p.getUniqueId());
		if (account == null)
			return;
		StringBuilder headerBuilder = new StringBuilder();
		headerBuilder.append("§6§lBATTLE§F§LBITS §E§LLOBBY");
		headerBuilder.append("\n");
		headerBuilder.append("          ");
		headerBuilder.append("§e§%coins%§: §f");
		headerBuilder.append(account.getMoney());
		headerBuilder.append(" §1- ");
		headerBuilder.append("§e§%tickets%§: §f");
		headerBuilder.append(account.getFichas());
		headerBuilder.append(" §1- ");
		headerBuilder.append("§ePing: §f");
		headerBuilder.append(PingAPI.getPing(p));
		headerBuilder.append("\n");
		headerBuilder.append("§e§%we-have%§ §f");
		headerBuilder
				.append(LobbyMain.getInstance().getServerManager().getBalancer(ServerType.NETWORK).getTotalNumber());
		headerBuilder.append(" §e§%players-online%§!");
		StringBuilder footerBuilder = new StringBuilder();
		footerBuilder.append("§bNick: §f");
		footerBuilder.append(p.getName());
		footerBuilder.append(" §1- ");
		footerBuilder.append("§b§%league%§: §F");
		footerBuilder.append(account.getLeague());
		footerBuilder.append(" §1- ");
		footerBuilder.append("§bXP: §f");
		footerBuilder.append(account.getXp());
		footerBuilder.append("\n");
		footerBuilder.append("§b§%more-information%§: §f");
		footerBuilder.append(BattlebitsAPI.WEBSITE);
		TabListAPI.setHeaderAndFooter(p, headerBuilder.toString(), footerBuilder.toString());
	}

}
