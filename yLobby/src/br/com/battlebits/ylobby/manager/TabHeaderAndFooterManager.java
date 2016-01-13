package br.com.battlebits.ylobby.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import me.flame.utils.Main;
import me.flame.utils.ranking.constructors.Account;

public class TabHeaderAndFooterManager {

	private BukkitRunnable updaterRunnable;

	public TabHeaderAndFooterManager() {
		start();
	}

	public void start() {
		updaterRunnable = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().length > 0) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (yLobbyPlugin.getyLobby().getzUtils().getPlayerUtils().isPlayerOn18(p)) {
							Account account = Main.getPlugin().getRankingManager().getAccount(p.getUniqueId());
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
							headerBuilder.append(yLobbyPlugin.getyLobby().getzUtils().getPlayerUtils().getPlayerPing(p));
							headerBuilder.append("\n");
							headerBuilder.append("§eTemos atualmente §f");
							headerBuilder.append(yLobbyPlugin.getyLobby().getPlayerCountManager().getNetworkOnlinePlayers());
							headerBuilder.append(" §ejogadores online em toda a nossa rede!");
							StringBuilder footerBuilder = new StringBuilder();
							footerBuilder.append("§bNick: §f");
							footerBuilder.append(p.getName());
							footerBuilder.append(" §1- ");
							footerBuilder.append("§bLiga: §F");
							footerBuilder.append(account.getLiga());
							footerBuilder.append(" §1- ");
							footerBuilder.append("§bXP: §f");
							footerBuilder.append(account.getXp());
							footerBuilder.append("\n");
							footerBuilder.append("§bMais informacoes em: §f");
							footerBuilder.append("www.battlebits.com.br");
							yLobbyPlugin.getyLobby().getzUtils().getBountifullUtils().createAndSendTabHeaderAndFooterPacket(p,
									headerBuilder.toString(), footerBuilder.toString());
						}
					}
				}
			}
		};
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 20L);
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
	}

}
