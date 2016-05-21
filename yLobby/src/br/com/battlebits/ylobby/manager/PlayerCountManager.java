package br.com.battlebits.ylobby.manager;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public class PlayerCountManager {

	private int networkOnlinePlayers;
	private int hgOnlinePlayers;
	private int fpOnlinePlayers;
	private int fullIronOnlinePlayers;
	private int simulatorOnlinePlayers;

	public PlayerCountManager() {
		networkOnlinePlayers = 0;
		hgOnlinePlayers = 0;
		fpOnlinePlayers = 0;
		fullIronOnlinePlayers = 0;
		simulatorOnlinePlayers = 0;
		startPlayerCountUpdater();
	}

	public int getFullIronOnlinePlayers() {
		return fullIronOnlinePlayers;
	}

	public void setFullIronOnlinePlayers(int fullIronOnlinePlayers) {
		this.fullIronOnlinePlayers = fullIronOnlinePlayers;
	}

	public int getSimulatorOnlinePlayers() {
		return simulatorOnlinePlayers;
	}

	public void setSimulatorOnlinePlayers(int simulatorOnlinePlayers) {
		this.simulatorOnlinePlayers = simulatorOnlinePlayers;
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

	public void startPlayerCountUpdater() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("NetworkCount"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("HGCount"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("FPCount"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("SimulatorCount"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("FullironCount"));
				}
			}
		}.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 20L);
	}

}
