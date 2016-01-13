package br.com.battlebits.ylobby.manager;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public class PlayerCountManager {

	private int networkOnlinePlayers;
	private int hgOnlinePlayers;
	private int fpOnlinePlayers;
	private int swOnlinePlayers;

	public PlayerCountManager() {
		networkOnlinePlayers = 0;
		hgOnlinePlayers = 0;
		fpOnlinePlayers = 0;
		swOnlinePlayers = 0;
		startPlayerCountUpdater();
	}

	public int getNetworkOnlinePlayers() {
		return networkOnlinePlayers;
	}

	public void setNetworkOnlinePlayers(int i) {
		networkOnlinePlayers = i;
	}

	public int getHgOnlinePlayers() {
		return hgOnlinePlayers;
	}

	public void setHgOnlinePlayers(int i) {
		hgOnlinePlayers = i;
	}

	public int getFpOnlinePlayers() {
		return fpOnlinePlayers;
	}

	public void setFpOnlinePlayers(int i) {
		fpOnlinePlayers = i;
	}

	public int getSwOnlinePlayers() {
		return swOnlinePlayers;
	}

	public void setSwOnlinePlayers(int i) {
		swOnlinePlayers = i;
	}

	@SuppressWarnings("deprecation")
	public void startPlayerCountUpdater() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().length > 0) {
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("NetworkCount"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("HGCount"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("FPCount"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("SWCount"));
				}
			}
		}.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 15L);
	}

}
