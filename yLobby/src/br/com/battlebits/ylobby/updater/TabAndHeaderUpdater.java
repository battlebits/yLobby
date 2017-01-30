package br.com.battlebits.ylobby.updater;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.battlebits.commons.BattlebitsAPI;
import br.com.battlebits.commons.api.player.PingAPI;
import br.com.battlebits.commons.api.tablist.TabListAPI;
import br.com.battlebits.commons.core.account.BattlePlayer;
import br.com.battlebits.ylobby.yLobbyPlugin;

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
		StringBuilder headerBuilder = new StringBuilder();
		headerBuilder.append("§6§lBATTLE§F§LBITS §E§LLOBBY");
		headerBuilder.append("\n");
		headerBuilder.append("          ");
		headerBuilder.append("§eMoedas: §f");
		headerBuilder.append(account.getMoney());
		headerBuilder.append(" §1- ");
		headerBuilder.append("§eFichas: §f");
		headerBuilder.append(account.getFichas());
		headerBuilder.append(" §1- ");
		headerBuilder.append("§ePing: §f");
		headerBuilder.append(PingAPI.getPing(p));
		headerBuilder.append("\n");
		headerBuilder.append("§eTemos atualmente §f");
		headerBuilder.append(yLobbyPlugin.getyLobby().getPlayerCountManager().getNetworkOnlinePlayers());
		headerBuilder.append(" §ejogadores online em toda a rede!");
		StringBuilder footerBuilder = new StringBuilder();
		footerBuilder.append("§bNick: §f");
		footerBuilder.append(p.getName());
		footerBuilder.append(" §1- ");
		footerBuilder.append("§bLiga: §F");
		footerBuilder.append(account.getLeague());
		footerBuilder.append(" §1- ");
		footerBuilder.append("§bXP: §f");
		footerBuilder.append(account.getXp());
		footerBuilder.append("\n");
		footerBuilder.append("§bMais informacoes em: §f");
		footerBuilder.append("www.battlebits.com.br");
		TabListAPI.setHeaderAndFooter(p, headerBuilder.toString(), footerBuilder.toString());
	}

}
