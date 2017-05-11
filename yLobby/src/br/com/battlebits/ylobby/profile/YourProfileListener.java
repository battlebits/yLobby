package br.com.battlebits.ylobby.profile;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import br.com.battlebits.ylobby.LobbyMain;

public class YourProfileListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().getTitle().equalsIgnoreCase("              §nSeu Perfil")) {
						if (e.getClickedInventory() == e.getInventory()) {
							if (e.getSlot() == 11) {
								LobbyMain.getInstance().getProfileRanksInventory().open((Player) e.getWhoClicked());
							} else if (e.getSlot() == 15) {
								LobbyMain.getInstance().getProfileConfigurationInventory()
										.open((Player) e.getWhoClicked());
							}
						}
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
