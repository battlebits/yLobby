package br.com.battlebits.ylobby.profile;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.battlebits.ylobby.yLobbyPlugin;

public class YourProfileListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteractListener(PlayerInteractEvent e) {
		if (!e.isCancelled() || e.getAction().name().contains("AIR")) {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.SKULL_ITEM) {
					if (e.getItem().hasItemMeta()) {
						if (e.getItem().getItemMeta().hasDisplayName()) {
							if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lPerfil §7(Clique)")) {
								yLobbyPlugin.getyLobby().getYourProfileInventory().open(e.getPlayer());
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClickListener(InventoryClickEvent e) {
		if (e.getInventory() != null) {
			if (e.getInventory().getType() == InventoryType.CHEST) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().getTitle().equalsIgnoreCase("              §nSeu Perfil")) {
						e.setCancelled(true);
						if (e.getSlot() == 11) {
							yLobbyPlugin.getyLobby().getProfileRanksInventory().open((Player) e.getWhoClicked());
						} else if (e.getSlot() == 15) {
							yLobbyPlugin.getyLobby().getProfileConfigurationInventory().open((Player) e.getWhoClicked());
						}
					}
				}
			}
		}
	}

}
