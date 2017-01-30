package br.com.battlebits.ylobby.selector.gamemode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class GameModeSelectorListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().getTitle().equalsIgnoreCase("     §nEscolha o Modo de Jogo")) {
						if (e.getClickedInventory() == e.getInventory()) {
							Player p = (Player) e.getWhoClicked();
							yLobbyPlugin.getyLobby().getGameModeSelector().tryToConnect(p, e.getSlot(),
									(e.getAction() == InventoryAction.PICKUP_HALF));
						}
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
