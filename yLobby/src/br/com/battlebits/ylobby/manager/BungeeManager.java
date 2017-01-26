package br.com.battlebits.ylobby.manager;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.yLobbyPlugin;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public class BungeeManager {

	private ArrayList<String> servers;
	private String lobbyID;

	private BukkitRunnable getServersRunnable;

	public BungeeManager() {
		servers = new ArrayList<>();
		lobbyID = "#?";
		getServersRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					yLobbyPlugin.getyLobby().getBungeeMessageSender().tryToSendMessage(new BungeeMessage("GetServers"));
				}
			}
		};
		getServersRunnable.runTaskTimerAsynchronously(yLobbyPlugin.getyLobby(), 1L, 20L);
	}

	public void setServers(String str) {
		servers.clear();
		for (String s : str.split(", ")) {
			servers.add(s);
		}
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] No total " + servers.size() + " servidores foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + getHGServers().size() + " servidores de HG foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + getCustomHGServers().size() + " servidores de CustomHG foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + getFairPlayServers().size() + " servidores de FPHG foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + getLobbyservers().size() + " servidores Lobby foram carregados!");
		yLobbyPlugin.getyLobby().getLogger().info("[BungeeManager] " + getDoubleKitServers().size() + " servidores Double Kit HG foram carregados!");

		for (String ip : getHGServers()) {
			yLobbyPlugin.getyLobby().getGameServerInfoManager().addServer(ip);
		}
		for (String ip : getCustomHGServers()) {
			yLobbyPlugin.getyLobby().getGameServerInfoManager().addServer(ip);
		}
		for (String ip : getFairPlayServers()) {
			yLobbyPlugin.getyLobby().getGameServerInfoManager().addServer(ip);
		}
		for (String ip : getDoubleKitServers()) {
			yLobbyPlugin.getyLobby().getGameServerInfoManager().addServer(ip);
		}
		for (String ip : getLobbyservers()) {
			yLobbyPlugin.getyLobby().getServerInfoManager().addServer(ip);
		}
		for (String ip : getFullIronServers()) {
			yLobbyPlugin.getyLobby().getServerInfoManager().addServer(ip);
		}
		for (String ip : getSimulatorServers()) {
			yLobbyPlugin.getyLobby().getServerInfoManager().addServer(ip);
		}

		yLobbyPlugin.getyLobby().getLobbySelector().start();
		yLobbyPlugin.getyLobby().getGameModeSelector().start();
		yLobbyPlugin.getyLobby().getMultiSelectorManager().loadSelectors();
		yLobbyPlugin.getyLobby().getMatchSelectorManager().loadSelectors();
		if (getServersRunnable != null) {
			getServersRunnable.cancel();
		}
	}

	public void addServer(String s) {
		if (servers.contains(s))
			return;

		servers.add(s);

		if (s.contains("battle-hg.com")) {
			yLobbyPlugin.getyLobby().getGameServerInfoManager().addServer(s);
		} else {
			yLobbyPlugin.getyLobby().getServerInfoManager().addServer(s);
		}
		reloadServers();
	}

	public void removeServer(String str) {
		servers.remove(str);
		yLobbyPlugin.getyLobby().getServerInfoManager().removeServer(str);
		yLobbyPlugin.getyLobby().getGameServerInfoManager().removeServer(str);
		reloadServers();
	}

	public void reloadServers() {

		yLobbyPlugin.getyLobby().getLobbySelector().stop();
		yLobbyPlugin.getyLobby().getGameModeSelector().stop();
		yLobbyPlugin.getyLobby().getMultiSelectorManager().stop();
		yLobbyPlugin.getyLobby().getMatchSelectorManager().stop();

		yLobbyPlugin.getyLobby().getLobbySelector().start();
		yLobbyPlugin.getyLobby().getGameModeSelector().start();
		yLobbyPlugin.getyLobby().getMultiSelectorManager().loadSelectors();
		yLobbyPlugin.getyLobby().getMatchSelectorManager().loadSelectors();
	}

	public ArrayList<String> getServers() {
		return servers;
	}

	public ArrayList<String> getHGServers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("battle-hg.com")) {
				if (!server.contains("fp")) {
					if (!server.contains("doublekit")) {
						if (!server.contains("custom")) {
							serverList.add(server);
						}
					}
				}
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public ArrayList<String> getCustomHGServers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("custom.battle-hg.com")) {
				serverList.add(server);
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public ArrayList<String> getFullIronServers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("fulliron.pvp")) {
				serverList.add(server);
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public ArrayList<String> getSimulatorServers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("simulator.pvp")) {
				serverList.add(server);
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public ArrayList<String> getFairPlayServers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("battle-hg.com")) {
				if (server.contains("fp")) {
					serverList.add(server);
				}
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public ArrayList<String> getDoubleKitServers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("battle-hg.com")) {
				if (server.contains("doublekit")) {
					serverList.add(server);
				}
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public ArrayList<String> getLobbyservers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("lobby")) {
				serverList.add(server);
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public ArrayList<String> getSwservers() {
		ArrayList<String> serverList = new ArrayList<>();
		for (String server : servers) {
			if (server.contains("sw")) {
				serverList.add(server);
			}
		}
		Collections.sort(serverList);
		return serverList;
	}

	public void setLobbyID(String lobbyID) {
		this.lobbyID = lobbyID;
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getScoreboard().getTeam("lobbyidteam").setSuffix("§e" + getLobbyID());
		}
	}

	public String getLobbyID() {
		return lobbyID;
	}

}
