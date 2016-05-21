package br.com.battlebits.ylobby.server;

public class ServerInfo {

	private String ip;
	private int onlinePlayers;
	private int maxPlayers;

	public ServerInfo(String serverip) {
		ip = serverip;
		onlinePlayers = 0;
		maxPlayers = 1;
	}

	public String getIp() {
		return ip;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getOnlinePlayers() {
		return onlinePlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setOnlinePlayers(int onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}

}
