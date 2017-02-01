package br.com.battlebits.ylobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.battlebits.ylobby.LobbyMain;
import br.com.battlebits.ylobby.bungee.BungeeMessage;

public class GameModsListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteractEntityListener(final PlayerInteractEntityEvent e) {
		if (e.getRightClicked().hasMetadata("GM_TYPE")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					String type = (String) e.getRightClicked().getMetadata("GM_TYPE").get(0).value();
					if (type.equalsIgnoreCase("SIMPLE")) {
						e.getPlayer().sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", new BungeeMessage(
								((String) e.getRightClicked().getMetadata("GM_CONNECT").get(0).value()).split("#"))
										.getDataOutput().toByteArray());
					} else if (type.equalsIgnoreCase("MATCH") || type.equalsIgnoreCase("MULTI")) {
						e.getPlayer().sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", new BungeeMessage(
								((String) e.getRightClicked().getMetadata("GM_CONNECT").get(0).value()).split("#"))
										.getDataOutput().toByteArray());
					}
				}
			}.runTaskAsynchronously(LobbyMain.getInstance());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByEntityListener(final EntityDamageByEntityEvent e) {
		if (e.getEntity().hasMetadata("GM_TYPE")) {
			if (e.getDamager() instanceof Player) {
				new BukkitRunnable() {
					@Override
					public void run() {
						Player p = (Player) e.getDamager();
						String type = (String) e.getEntity().getMetadata("GM_TYPE").get(0).value();
						if (type.equalsIgnoreCase("SIMPLE")) {
							p.sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", new BungeeMessage(
									((String) e.getEntity().getMetadata("GM_CONNECT").get(0).value()).split("#"))
											.getDataOutput().toByteArray());
						} else if (type.equalsIgnoreCase("MATCH") || type.equalsIgnoreCase("MULTI")) {
							p.sendPluginMessage(LobbyMain.getInstance(), "BungeeCord", new BungeeMessage(
									((String) e.getEntity().getMetadata("GM_CONNECT").get(0).value()).split("#"))
											.getDataOutput().toByteArray());
						}
					}
				}.runTaskAsynchronously(LobbyMain.getInstance());
			}
		}
	}

}