package br.com.battlebits.ylobby.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import br.com.battlebits.ylobby.LobbyUtils;

public class LocationManager {

	private Location spawnLocation;

	public LocationManager() {
		spawnLocation = LobbyUtils.getLocationUtils().getCenter(Bukkit.getWorlds().get(0).getSpawnLocation(), false);
	}

	public Location getSpawnLocation() {
		return spawnLocation;
	}

}
