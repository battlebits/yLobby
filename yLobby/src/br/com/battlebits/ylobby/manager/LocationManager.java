package br.com.battlebits.ylobby.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class LocationManager implements ManagerBase {

	private Location spawnLocation;

	public void start() {
		spawnLocation = yLobbyPlugin.getyLobby().getzUtils().getLocationUtils().getCenter(Bukkit.getWorlds().get(0).getSpawnLocation(), false);
	}

	public void stop() {
		spawnLocation = null;
	}

	public Location getSpawnLocation() {
		return spawnLocation;
	}

}
