package br.com.battlebits.ylobby.manager;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public class BungeeManager {

	private ArrayList<String> servers;
	private ArrayList<String> hgservers;
	private ArrayList<String> fpservers;
	private ArrayList<String> swservers;
	private ArrayList<String> lobbyservers;
	private String serverName;

	private BukkitRunnable getServersRunnable;

	public BungeeManager() {
		servers = new ArrayList<>();
		hgservers = new ArrayList<>();
		fpservers = new ArrayList<>();
		swservers = new ArrayList<>();
		lobbyservers = new ArrayList<>();
		getServersRunnable = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().length > 0) {
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("GetServer"));
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("GetServers"));
				}
			}
		};
		getServersRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 20L);
	}

	public void setServers(String str) {
		servers.clear();
		hgservers.clear();
		fpservers.clear();
		for (String s : str.split(", ")) {
			servers.add(s);
			if (s.contains("battle-hg.com")) {
				if (s.contains("fp")) {
					fpservers.add(s);
				} else {
					hgservers.add(s);
				}
			} else if (s.contains("lobby")) {
				lobbyservers.add(s);
			}
		}
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] No total " + servers.size() + " servidores foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + hgservers.size() + " servidores de HG foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + fpservers.size() + " servidores de FPHG foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + lobbyservers.size() + " servidores Lobby foram carregados!");
		Collections.sort(fpservers);
		Collections.sort(hgservers);
		Collections.sort(lobbyservers);
		for (String ip : hgservers) {
			yLobbyPlugin.getyLobby().getGameServerInfoManager().addServer(ip);
		}
		for (String ip : fpservers) {
			yLobbyPlugin.getyLobby().getGameServerInfoManager().addServer(ip);
		}
		for (String ip : lobbyservers) {
			yLobbyPlugin.getyLobby().getServerInfoManager().addServer(ip);
		}
		yLobbyPlugin.getyLobby().getLobbySelector().start();
		yLobbyPlugin.getyLobby().getGameModeSelector().start();
		yLobbyPlugin.getyLobby().getMatchSelectorManager().loadSelectors();
		if (getServersRunnable != null) {
			getServersRunnable.cancel();
		}
	}

	public ArrayList<String> getServers() {
		return servers;
	}

	public ArrayList<String> getHGServers() {
		return hgservers;
	}

	public ArrayList<String> getFairPlayServers() {
		return fpservers;
	}

	public ArrayList<String> getLobbyservers() {
		return lobbyservers;
	}

	public ArrayList<String> getSwservers() {
		return swservers;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String str) {
		serverName = str;
	}

}
