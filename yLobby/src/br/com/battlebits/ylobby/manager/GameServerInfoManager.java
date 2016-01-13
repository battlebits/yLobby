package br.com.battlebits.ylobby.manager;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;
import br.com.battlebits.ylobby.server.GameServerInfo;

public class GameServerInfoManager {

	private HashMap<String, GameServerInfo> ipGameInfo;
	private BukkitRunnable updaterRunnable;

	public GameServerInfoManager() {
		ipGameInfo = new HashMap<>();
		startUpdater();
	}

	public void stop() {
		if (updaterRunnable != null) {
			updaterRunnable.cancel();
		}
		ipGameInfo.clear();
	}

	public void addServer(String ip) {
		ipGameInfo.put(ip, new GameServerInfo(ip));
	}

	public GameServerInfo get(String ip) {
		return ipGameInfo.get(ip);
	}

	public boolean isRegistred(String ip) {
		return ipGameInfo.containsKey(ip);
	}

	public HashMap<String, GameServerInfo> getIpGameInfo() {
		return ipGameInfo;
	}

	public void startUpdater() {
		updaterRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				for (String ip : ipGameInfo.keySet()) {
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("ServerInfo", ip));
				}
			}
		};
		updaterRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 15L);
	}

}
