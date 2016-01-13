package br.com.battlebits.ylobby.server;

public class ServerInfo {

	private String ip;
	private String motd;
	private int onlinePlayers;
	private int maxPlayers;

	public ServerInfo(String serverip) {
		ip = serverip;
		motd = "§0";
		onlinePlayers = 0;
		maxPlayers = 1;
	}

	public String getIp() {
		return ip;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public String getMotd() {
		return motd;
	}

	public int getOnlinePlayers() {
		return onlinePlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public void setOnlinePlayers(int onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}

}
