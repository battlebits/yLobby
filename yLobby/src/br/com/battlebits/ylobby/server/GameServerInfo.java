package br.com.battlebits.ylobby.server;

public class GameServerInfo extends ServerInfo implements Comparable<GameServerInfo> {

	private int time;
	
	public GameServerInfo(String serverip) {
		super(serverip);
		time = 9999;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int compareTo(GameServerInfo o) {
		if (getTime() != 9999 && getTime() != 0 && (o.getTime() == 9999 || o.getTime() == 0)) {
			return -1;
		} else if (o.getTime() != 9999 && o.getTime() != 0 && (getTime() == 9999 || getTime() == 0)) {
			return 1;
		} else if (getTime() == 9999 && o.getTime() == 9999) {
			if (getOnlinePlayers() > o.getOnlinePlayers()) {
				return -1;
			} else if (getOnlinePlayers() < o.getOnlinePlayers()) {
				return 1;
			} else {
				return 0;
			}
		} else if (o.getTime() == 9999 && getTime() == 9999) {
			if (o.getOnlinePlayers() > getOnlinePlayers()) {
				return -1;
			} else if (o.getOnlinePlayers() < getOnlinePlayers()) {
				return 1;
			} else {
				return 0;
			}
		} else if (getTime() == 9999 && o.getTime() == 0) {
			return -1;
		} else if (o.getTime() == 9999 && getTime() == 0) {
			return 1;
		} else if ((getTime() == 0 && getMotd().toLowerCase().contains("progresso"))
				&& (o.getTime() == 0 && o.getMotd().toLowerCase().contains("progresso"))) {
			if (getOnlinePlayers() < o.getOnlinePlayers()) {
				return 1;
			} else if (getOnlinePlayers() > o.getOnlinePlayers()) {
				return -1;
			} else {
				return 0;
			}
		} else if ((o.getTime() == 0 && o.getMotd().toLowerCase().contains("progresso"))
				&& (getTime() == 0 && getMotd().toLowerCase().contains("progresso"))) {
			if (o.getOnlinePlayers() < getOnlinePlayers()) {
				return 1;
			} else if (o.getOnlinePlayers() > getOnlinePlayers()) {
				return -1;
			} else {
				return 0;
			}
		} else if ((getTime() == 0 && getMotd().toLowerCase().contains("progresso"))
				&& (o.getTime() == 0 && !o.getMotd().toLowerCase().contains("progresso"))) {
			return -1;
		} else if ((o.getTime() == 0 && !o.getMotd().toLowerCase().contains("progresso"))
				&& (getTime() == 0 && getMotd().toLowerCase().contains("progresso"))) {
			return 1;
		} else if ((getTime() != 9999 && getTime() != 0) && (o.getTime() != 9999 && o.getTime() != 0)) {
			if (getTime() < o.getTime()) {
				return -1;
			} else if (getTime() > o.getTime()) {
				return 1;
			} else {
				return 0;
			}
		} else if ((o.getTime() != 9999 && o.getTime() != 0) && (getTime() != 9999 && getTime() != 0)) {
			if (o.getTime() < getTime()) {
				return -1;
			} else if (o.getTime() > getTime()) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

}
