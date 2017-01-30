package br.com.battlebits.ylobby.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.battlebits.ylobby.yLobbyPlugin;
import lombok.Getter;

public class PlayerHideListener implements Listener {

	private ArrayList<String> hideMessage;
	private ArrayList<String> showMessage;
	@Getter
	private static HashMap<UUID, Long> uuidCooldown = new HashMap<>();

	public PlayerHideListener() {
		hideMessage = new ArrayList<>();
		hideMessage.add("§0");
		hideMessage.add("§%players-hided%§");
		hideMessage.add("§0");
		showMessage = new ArrayList<>();
		showMessage.add("§0");
		showMessage.add("§%players-showed%§");
		showMessage.add("§0");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuitListener(PlayerQuitEvent e) {
		uuidCooldown.remove(e.getPlayer().getUniqueId());
		yLobbyPlugin.getyLobby().getPlayerHideManager().tryToRemoveFromLists(e.getPlayer().getUniqueId());
	}

		// TODO User PlayerHideToPlayerEvent

}
