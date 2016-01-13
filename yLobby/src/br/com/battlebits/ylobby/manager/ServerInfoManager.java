package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.server.GameServerInfo;
import br.com.battlebits.ylobby.server.ServerInfo;

public class ServerInfoManager {

	private HashMap<String, ServerInfo> ipInfo;
	private BukkitRunnable updaterRunnable;

	public ServerInfoManager() {
		ipInfo = new HashMap<>();
		startUpdater();
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
		ipInfo.clear();
	}

	public void addServer(String ip) {
		ipInfo.put(ip, new GameServerInfo(ip));
	}

	public ServerInfo get(String ip) {
		return ipInfo.get(ip);
	}

	public boolean isRegistred(String ip) {
		return ipInfo.containsKey(ip);
	}

	public HashMap<String, ServerInfo> getIpGameInfo() {
		return ipInfo;
	}

	public void startUpdater() {
		updaterRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				for (String ip : ipInfo.keySet()) {
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("ServerInfo", ip));
				}
			}
		};
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 15L);
	}

}
