package br.com.battlebits.ylobby.server;

import br.com.battlebits.ycommon.bungee.servers.HungerGamesServer.HungerGamesState;

public class GameServerInfo extends ServerInfo implements Comparable<GameServerInfo> {

	private int time;
	private HungerGamesState state;

	public GameServerInfo(String serverip) {
		super(serverip);
		time = 9999;
		state = HungerGamesState.WAITING;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public HungerGamesState getState() {
		return state;
	}

	public void setState(HungerGamesState state) {
		this.state = state;
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
		} else if ((getTime() == 0 && isInProgress()) && (o.getTime() == 0 && isInProgress())) {
			if (getOnlinePlayers() < o.getOnlinePlayers()) {
				return 1;
			} else if (getOnlinePlayers() > o.getOnlinePlayers()) {
				return -1;
			} else {
				return 0;
			}
		} else if ((o.getTime() == 0 && isInProgress()) && (getTime() == 0 && isInProgress())) {
			if (o.getOnlinePlayers() < getOnlinePlayers()) {
				return 1;
			} else if (o.getOnlinePlayers() > getOnlinePlayers()) {
				return -1;
			} else {
				return 0;
			}
		} else if ((getTime() == 0 && isInProgress()) && (o.getTime() == 0 && !isInProgress())) {
			return -1;
		} else if ((o.getTime() == 0 && !isInProgress()) && (getTime() == 0 && isInProgress())) {
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

	public boolean isInProgress() {
		return state == HungerGamesState.GAMETIME || state == HungerGamesState.INVENCIBILITY;
	}

}
