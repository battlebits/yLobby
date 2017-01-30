package br.com.battlebits.ylobby.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import br.com.battlebits.commons.api.vanish.VanishAPI;

public class PlayerHideManager {

	private Set<UUID> hideAllPlayers;

	public PlayerHideManager() {
		hideAllPlayers = new HashSet<>();
	}

	public void hideAllPlayers(Player p) {
		hideAllPlayers.add(p.getUniqueId());
		VanishAPI.getInstance().updateVanishToPlayer(p);
	}

	public boolean isHiding(Player p) {
		return hideAllPlayers.contains(p.getUniqueId());
	}

	public void showAllPlayers(Player p) {
		if (hideAllPlayers.contains(p.getUniqueId())) {
			hideAllPlayers.remove(p.getUniqueId());
		}
		VanishAPI.getInstance().updateVanishToPlayer(p);
	}

	public void stop() {
		hideAllPlayers.clear();
	}

	public void tryToRemoveFromLists(UUID id) {
		hideAllPlayers.remove(id);
	}

}
