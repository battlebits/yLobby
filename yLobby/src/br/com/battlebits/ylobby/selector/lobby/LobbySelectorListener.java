package br.com.battlebits.ylobby.selector.lobby;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class LobbySelectorListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().getTitle().equalsIgnoreCase("          �nEscolha o Lobby")) {
						Player p = (Player) e.getWhoClicked();
						yLobbyPlugin.getyLobby().getLobbySelector().tryToConnect(p, e.getSlot());
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteractListener(PlayerInteractEvent e) {
		if (!e.isCancelled() || e.getAction().name().contains("AIR")) {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.NETHER_STAR) {
					if (e.getItem().hasItemMeta()) {
						if (e.getItem().getItemMeta().hasDisplayName()) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("�5�lLobbys �7(Clique)")) {
								yLobbyPlugin.getyLobby().getLobbySelector().open(e.getPlayer());
							}
						}
					}
				}
			}
		}
	}

}