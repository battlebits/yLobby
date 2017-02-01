package br.com.battlebits.ylobby.selector.match;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import br.com.battlebits.ylobby.yLobbyPlugin;
import net.md_5.bungee.api.ChatColor;

public class MatchSelectorListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (yLobbyPlugin.getyLobby().getMatchSelectorManager()
							.isMatchSelector(e.getInventory().getTitle())) {
						if (e.getClickedInventory() == e.getInventory()) {
							Player p = (Player) e.getWhoClicked();
							MatchSelector matchSelector = yLobbyPlugin.getyLobby().getMatchSelectorManager()
									.getMatchSelector(e.getInventory().getTitle());
							if (e.getSlot() == 4) {
								matchSelector.directConnect(p);
							} else if (e.getSlot() == e.getInventory().getSize() - 5) {
								yLobbyPlugin.getyLobby().getGameModeSelector().open(p);
							} else if (e.getCurrentItem().getType() == Material.INK_SACK) {
								matchSelector.tryToConnect(p, ChatColor
										.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split(" ")[1]);
							}
						}
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
